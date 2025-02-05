package vttp.paf.day28l.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;

import vttp.paf.day28l.models.Review;
import vttp.paf.day28l.models.UserDto;

@Repository
public class BGGRepo {
    
    @Autowired
    MongoTemplate template;
    
    /*
     * db.games.aggregate([
     *  {$match:{name:{$regex:name,$options:"i"}}}},
     *  {$project: {name:1,ranking:1,image:1,_id:-1},
     *  {$sort:{ranking:-1}}},
     *  {$limit:3}  
     * }])
     */
    public List<Document> findGamesByName(String name) {
        // create the aggregation stages
        Criteria criteria = Criteria.where("name").regex(name,"i");
        MatchOperation matchStage = Aggregation.match(criteria);
        //project attributes
        ProjectionOperation projectStage= Aggregation
            .project("name","ranking","image")
            .andExclude("_id");
        //sort by ranking
        SortOperation sortStage = Aggregation.sort(Sort.Direction.ASC,"ranking");

        LimitOperation limitStage = Aggregation.limit(3);

        Aggregation pipeline = Aggregation.newAggregation(matchStage,projectStage,sortStage,limitStage);
        AggregationResults<Document> results = template.aggregate(pipeline,"games",Document.class);
       
        return results.getMappedResults();
        
    }
    /*
     * db.comments.aggregate([
    {
        $group:{
            _id: "$user",
            comments: {
                $push:{
                    gid:"$gid",
                    text:"$c_text"
                }
                
            }
        }
    }
])
     */
    public List<Document> groupCommentsByUser() {
        GroupOperation groupStage = Aggregation.group("user")
            .push(
                new BasicDBObject()
                    .append("gid","$gid")
                    .append("text","$c_text")
            ).as("comments");

        LimitOperation limitStage = Aggregation.limit(3);
       
        Aggregation pipeline = Aggregation.newAggregation(groupStage,limitStage);
        AggregationResults<Document> results = template.aggregate(pipeline,"comments",Document.class);

        
        


        return results.getMappedResults();
    }
    /*
     * db.comments.aggregate([
    {
        $match:{
            user: {
                $regex: "johnny",
                $options: "i"
            }
        }
    },
    {
        $lookup:{
            from:"games",
            foreignField:"gid",
            localField:"gid",
            as: "games"
        }
    },
    {
        $unwind: "$games"
    },
    {
        $group: {
            _id:"$user",
            
            reviewsbyuser: {
                $push: {gamename:"$games.name",
                        comment:"$c_text",
                        rating:"$rating"}
            }
          
        }
    }
    
    
])
     */
    public List<UserDto> getCommentsFromUser(String name) {
        SortOperation sortRating = Aggregation.sort(Sort.Direction.DESC,"rating");
        
        
        Criteria criteria = Criteria.where("user").regex(name,"i");
        MatchOperation matchStage = Aggregation.match(criteria);
        LookupOperation lookupStage = LookupOperation.newLookup()
            .from("games")
            .localField("gid")
            .foreignField("gid")
            .as("games");
        // LookupOperation joinComments = Aggregation.lookup("games","gid","gid","games");
        UnwindOperation unwindStage = Aggregation.unwind("$games");
        GroupOperation groupStage = Aggregation.group("$user")
            .push(
                new BasicDBObject()
                .append("gameName","$games.name")
                .append("comment","$c_text")
                .append("rating","$rating")
            ).as("reviewsbyuser");
        Aggregation pipeline = Aggregation.newAggregation(matchStage,lookupStage,unwindStage,sortRating,groupStage);
        AggregationResults<Document> results = template.aggregate(pipeline,"comment",Document.class);

        List<Document> documents = results.getMappedResults();

        //alternatively can just return as a List<Document>
        return documents.stream()
        .map(d -> {
            UserDto userDto = new UserDto();
            List<Document> reviewDocs = d.getList("reviewsbyuser", Document.class);
            userDto.set_id(d.getString("_id"));
            userDto.setUserReviews(
                reviewDocs.stream()
                    .map(doc-> {
                        Review review = new Review();
                        review.setGameName(doc.getString("gameName"));
                        review.setComment(doc.getString("comment"));
                        review.setRating(doc.getInteger("rating"));
                        return review;
                        
                    })
                    .toList()

            );
            
            return userDto;
        })
        .toList();
    }
    
}
