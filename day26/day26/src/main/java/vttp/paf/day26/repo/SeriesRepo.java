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
    MongoTemplate mongoTemplate;

    /*
     * db.series.find({
     *  name: {
     *      $regex: "a name",
     *      $options: "i"
     *  }
     * })
     * .projections({...})
     */
    public List<Document> findSeriesByName(String name) {
        // Criteria , .is -> means = to name
        //create the predicate
        Criteria criteria = Criteria.where(F_NAME)
        .regex(name,"i");
        //create the query using the predicate

        Query query = Query.query(criteria);
        query.fields()
        .include("name","id","summary","image.original")
        .exclude("_id");
        //perform the query
        List<Document> results = mongoTemplate.find(query,Document.class,"series");
        return results;
        
    }
    /*
     *  db.series.find({
     *      "rating.average": {"gte:8"}       
     *  })
     *  .sort({"rating.average": -1})
     *  .limit(10)
     */
    public List<Document> findSeriesByRating(float rating) {
        Criteria criteria = Criteria.where(F_RATING_AVERAGE)
                                    .gte(rating);
        Query query = Query.query(criteria)
                            .with(Sort.by(Sort.Direction.DESC,F_RATING_AVERAGE))
                            .limit(5);
        query.fields()
        .include("name","id","summary","image.original")
        .exclude("_id");
        return mongoTemplate.find(query,Document.class,C_SERIES);
    }


    /*
     * db.series.distinct("type")
     */

    public List<String> findTypeOfSeries() {
        return mongoTemplate.findDistinct(
            new Query(), "type",F_TYPE,String.class
        );
       
    }
}
