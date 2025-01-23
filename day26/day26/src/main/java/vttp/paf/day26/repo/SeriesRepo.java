package vttp.paf.day26.repo;




import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static vttp.paf.day26.repo.Constants.C_SERIES;
import static vttp.paf.day26.repo.Constants.F_NAME;
import static vttp.paf.day26.repo.Constants.F_RATING_AVERAGE;
import static vttp.paf.day26.repo.Constants.F_TYPE;

@Repository
public class SeriesRepo {
    @Autowired
    MongoTemplate template;


    /*
      db.series.find({
         name: {
            $regex: 'a name', 
            $options: 'i'
         }
      })
      .projections({ ... })
    */
   public List<Document> findSeriesByName(String name) {
      // Create the predicate
      Criteria criteria = Criteria.where(F_NAME)
            .regex(name, "i");
      
      // Create the query using the predicate
      Query query = Query.query(criteria);
      query.fields()
            .include("name", "id", "summary", "image.original")
            .exclude("_id");

      // Perform the query
      List<Document> results = template.find(query, Document.class, C_SERIES);

      return (results);
   }

   /*
      db.series.find({
         "rating.average": { $gte: 8 }
      }) 
      .sort({ 'rating.average': -1 })
      .limit(10)
    */
   public List<Document> findSeriesByRating(float rating) {

      Criteria criteria = Criteria.where(F_RATING_AVERAGE)
            .gte(rating);

      Query query = Query.query(criteria)
            .with(Sort.by(Sort.Direction.DESC, F_RATING_AVERAGE))
            .limit(5);

      query.fields()
         .include("name", "id", "summary", "image.original", F_RATING_AVERAGE)
         .exclude("_id");

      return template.find(query, Document.class, C_SERIES);
   }

   /*
    * db.series.distinct('type')
    */
    public List<String> findTypeOfSeries() {
      return template.findDistinct(
         new Query(), F_TYPE, C_SERIES, String.class
      );
    }
    
}
