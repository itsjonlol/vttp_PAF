package vttp.paf.day28ws.repo;

import java.util.Date;
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
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp.paf.day28ws.exception.exceptions.RecordNotFoundException;
import vttp.paf.day28ws.model.GameDto;
import vttp.paf.day28ws.model.GameReviewDto;

@Repository
public class ReviewRepo {

    @Autowired
    MongoTemplate template;


    /*
     * db.games.aggregate([
  { 
    $match: { gid: 6228 } // Match the game with gid = 1
  },
  { 
    $lookup: { 
      from: "comments", // Join with the comments collection
      localField: "gid", 
      foreignField: "gid",
      as: "reviews" // Store the joined reviews in the "reviews" array
    }
  },
  {
    $unwind: "$reviews" // Deconstruct the reviews array
  },
  {
    $group: {
      _id: "$gid", // Group by game ID
      name: { $first: "$name" }, // Include the game name
      year: { $first: "$year" }, // Include the game year
      rank: { $first: "$ranking" }, // Include the game ranking
      average: { $avg: "$reviews.rating" }, // Calculate the average rating
      users_rated: { $first: "$users_rated" }, // Include the number of users who rated the game
      thumbnail: { $first: "$image" }, // Include the game thumbnail
      reviews: { 
        $push: { $concat: ["/review/", "$reviews._id"] } // Create review URLs
      }
    }
  },
  {
    $project: {
      _id: 0, // Exclude the default _id field
      game_id: "$_id", // Include the game ID
      name: 1, // Include the game name
      year: 1, // Include the game year
      rank: 1, // Include the game ranking
      average: 1, // Include the average rating
      users_rated: 1, // Include the number of users who rated the game
      thumbnail: 1, // Include the game thumbnail
      reviews: 1, // Include the reviews array
      timestamp: new Date() // Add a timestamp
    }
  }
]);
     */
    public GameDto getGameReviews(Integer gid) {
      
        MatchOperation matchStage = Aggregation.match(Criteria.where("gid").is(gid));
        
        // Lookup reviews from the comments collection
        LookupOperation lookupStage = LookupOperation.newLookup()
            .from("comments")          // Collection to join
            .localField("gid")         // Field from the games collection
            .foreignField("gid")       // Field from the comments collection
            .as("reviews");            // Output array field

        
        // Unwind stage: Deconstruct the reviews array
        UnwindOperation unwindStage = Aggregation.unwind("reviews");

        // Group stage: Group by game ID and accumulate review URLs
        GroupOperation groupStage = Aggregation.group("gid")
                .first("name").as("name")
                .first("year").as("year")
                .first("ranking").as("rank")
                .avg("reviews.rating").as("average")
                .first("users_rated").as("users_rated")
                .first("image").as("thumbnail")
                .push(StringOperators.Concat.stringValue("/reviews/").concatValueOf("reviews._id")
                        
                ).as("reviews");
               
                

        // Project stage: Reshape the output
        ProjectionOperation projectStage = Aggregation.project()
                .andExclude("_id")
                .and("_id").as("game_id")
                .and("name").as("name")
                .and("year").as("year")
                .and("rank").as("rank")
                .and("average").as("average")
                .and("users_rated").as("users_rated")
                .and("thumbnail").as("thumbnail")
                .and("reviews").as("reviews");
                // .and(new Date()).as("timestamp");

        // Build the aggregation pipeline
        Aggregation pipeline = Aggregation.newAggregation(
                matchStage,
                lookupStage,
                unwindStage,
                groupStage,
                projectStage
        );


        
        // Execute the aggregation
        AggregationResults<Document> results = template.aggregate(pipeline, "games", Document.class);

        // Get the result
        Document result = results.getUniqueMappedResult();
        if (result == null) {
            throw new RecordNotFoundException("Record not found");
        }
        // result.put("timestamp", new Date());
        return mapDocumentToGameDTO(result);
       

    }
    //alternatively can put in utils
    private GameDto mapDocumentToGameDTO(Document document) {
        GameDto gameDTO = new GameDto();
        gameDTO.setName(document.getString("name"));
        gameDTO.setYear(document.getInteger("year"));
        gameDTO.setRank(document.getInteger("rank"));
        gameDTO.setAverage(document.getDouble("average"));
        gameDTO.setUsers_rated(document.getInteger("users_rated"));
        gameDTO.setThumbnail(document.getString("thumbnail"));
        gameDTO.setReviews(document.getList("reviews", String.class));
        gameDTO.setGame_id(document.getInteger("game_id"));
        gameDTO.setTimestamp(new Date()); // Set the current timestamp
        return gameDTO;
    }

    /*
     * db.comments.aggregate([
  {
    $match: { rating: { $exists: true }} // Filter out documents without ratings
  },
  {
    $sort: { rating: -1 } // Sort by rating in descending order
  },
  {
    $group: {
      _id: "$gid",
      maxRating: { $max: "$rating" },
      user: { $first: "$user" },
      comment: { $first: "$c_text" },
      review_id: { $first: "$_id" }
    }
  },
  {
    $lookup: {
      from: "games",
      localField: "_id",
      foreignField: "gid",
      as: "gameDetails"
    }
  },
  {
    $unwind: "$gameDetails"
  },
  {
    $project: {
      _id: "$gameDetails._id",
      name: "$gameDetails.name",
      rating: "$maxRating",
     
      user: "$user",
      comment: "$comment",
      review_id: "$review_id"
    }
  },
  {  
      $limit: 100
  },
  {
      $sort: {rating:-1}
  }
]);
     */
    public List<GameReviewDto> getHighestRatedGames() {
        // Match stage: Filter out documents without ratings
        MatchOperation matchStage = Aggregation.match(Criteria.where("rating").exists(true));

        // Sort stage: Sort by rating in descending order
        SortOperation sortStage = Aggregation.sort(Sort.Direction.DESC, "rating");

        // Group stage: Group by game ID and find the maximum rating
        GroupOperation groupStage = Aggregation.group("gid")
                .max("rating").as("maxRating") // Find the maximum rating
                .first("user").as("user") // Store the user who gave the rating
                .first("c_text").as("comment") // Store the associated comment
                .first("_id").as("review_id"); // Store the review ID

        // Lookup stage: Join with the games collection
        LookupOperation lookupStage = LookupOperation.newLookup()
                .from("games") // Join collection
                .localField("_id") // Field from the comments collection
                .foreignField("gid") // Field from the games collection
                .as("gameDetails"); // Output array field

        // Unwind stage: Deconstruct the gameDetails array
        UnwindOperation unwindStage = Aggregation.unwind("gameDetails");

        // Project stage: Reshape the output
        ProjectionOperation projectStage = Aggregation.project()
                .and("gameDetails._id").as("_id") // Include the game ID
                .and("gameDetails.name").as("name") // Include the game name
                .and("maxRating").as("rating") // Include the maximum rating
                .and("user").as("user") // Include the user
                .and("comment").as("comment") // Include the comment
                .and("review_id").as("review_id"); // Include the review ID

        // Limit stage: Limit the results to 100
        LimitOperation limitStage = Aggregation.limit(100);

        // Build the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                sortStage,
                groupStage,
                lookupStage,
                unwindStage,
                projectStage,
                limitStage
          
        );

        // Execute the aggregation
        AggregationResults<Document> results = template.aggregate(
                aggregation, "comments", Document.class
        );

        List<Document> resultsList = results.getMappedResults();
        List<GameReviewDto> games = resultsList.stream()
            .map(document -> {
                GameReviewDto gameReviewDto = new GameReviewDto();
                gameReviewDto.set_id(document.getObjectId("_id").toHexString());
                gameReviewDto.setName(document.getString("name"));
                gameReviewDto.setRating(document.getInteger("rating"));
                gameReviewDto.setUser(document.getString("user"));
                gameReviewDto.setComment(document.getString("comment"));
                gameReviewDto.setReview_id(document.getString("review_id"));
                return gameReviewDto;
            })
            .toList();
        // Return the results as a list
        return games;
    }

    
    
}
