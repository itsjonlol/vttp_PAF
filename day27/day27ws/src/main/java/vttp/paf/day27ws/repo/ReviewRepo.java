package vttp.paf.day27ws.repo;



import java.time.LocalDateTime;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import vttp.paf.day27ws.exception.exceptions.InvalidValueException;
import vttp.paf.day27ws.exception.exceptions.RecordNotFoundException;
import vttp.paf.day27ws.model.Review;
import vttp.paf.day27ws.model.UpdateReview;
import static vttp.paf.day27ws.util.ConstantVar.C_GAMES;
import static vttp.paf.day27ws.util.ConstantVar.C_REVIEWS;

@Repository
public class ReviewRepo {
    
    @Autowired
    MongoTemplate template;

    public Boolean checkIfGameExist(Integer id) {
        
        Criteria criteria = Criteria.where("gid").is(id);
        Query query = Query.query(criteria);
        Document gameDocument = template.findOne(query,Document.class,C_GAMES);
        
        return gameDocument != null;
    }

    public String getGameName(Integer id) {
        
        Criteria criteria = Criteria.where("gid").is(id);
        Query query = Query.query(criteria);
        Document gameDocument = template.findOne(query,Document.class,C_GAMES);
        return gameDocument.getString("name");
    }
    public Boolean insertReview(Review review) {

        if (review.getRating() < 0 || review.getRating() >10) {
            throw new InvalidValueException("Rating can only be between 0 and 10");
        }

        Document toInsert = new Document();
        toInsert.put("user",review.getUser());
        toInsert.put("rating",review.getRating());
        toInsert.put("comment",review.getComment());
        toInsert.put("ID",review.getId());
        toInsert.put("posted",review.getDate());
        toInsert.put("name",review.getName());
        Document newDoc = template.insert(toInsert, C_REVIEWS);
        return newDoc !=null;
    }

    public Boolean checkIfReviewExist(String id) {
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        Query query = Query.query(criteria);
        Document reviewDocument = template.findOne(query,Document.class,C_REVIEWS);
        return reviewDocument != null;
    }

    /*
     * db.reviews.updateOne(
    { _id: ObjectId("6793419a8297ed65810fa0c8") }, // Filter by _id
    {   $set: {comment: <new comment> ,rating:<new rating>},
        $push: {
            edited: {
                comment: "Updated comment",
                rating: 4,
                posted: new Date()
            }
        }
    }
)
     */
    public Boolean updateReview(UpdateReview updateReview,String id) {
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        Query query = Query.query(criteria);
        Document toInsert = new Document();
        
        if (updateReview.getRating() < 0 || updateReview.getRating() >10) {
            throw new InvalidValueException("Rating can only be between 0 and 10");
        }
        toInsert.put("comment",updateReview.getComment());
        toInsert.put("rating", updateReview.getRating());
        toInsert.put("posted",updateReview.getPosted());
        Update updateOps = new Update();
        if (updateReview.getComment()==null) {
            updateOps.set("rating",updateReview.getRating())
            .push("edited",toInsert);
        } else {
            updateOps.set("comment",updateReview.getComment())
            .set("rating",updateReview.getRating())
            .push("edited",toInsert);

        }
       
        //can remove the Document.class here
        UpdateResult result = template.updateFirst(query, updateOps, Document.class, C_REVIEWS);
        return result.wasAcknowledged() && result.getModifiedCount() > 0;
        
    }
    /*
     * db.reviews.find({
    "_id": ObjectId("6793419a8297ed65810fa0c8")
})
     */
    public Document getReviewHistory(String id) {
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        Query query = Query.query(criteria);
        Document document = template.findOne(query, Document.class,C_REVIEWS);
        if (document ==null ) {
            throw new RecordNotFoundException("Record does not exist");
        }
        
        document.remove("_id");
        document.put("timestamp", LocalDateTime.now());
        
        return document;
        

    }
    public Document getLatestReview(String id) {
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        Query query = Query.query(criteria);
        Document document = template.findOne(query, Document.class,C_REVIEWS);
        if (document ==null ) {
            throw new RecordNotFoundException("Record does not exist");
        }
        document.remove("_id");
        Boolean isEdited = false;
        if (document.get("edited")!=null) {
            isEdited = true;
        } 
        document.put("edited",isEdited);
        document.put("timestamp", LocalDateTime.now());
        return document;
        

    }
}
