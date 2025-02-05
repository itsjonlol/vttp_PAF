package vttp.paf.day28l.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.stereotype.Repository;

@Repository
public class SeriesRepo {
    
    @Autowired
    MongoTemplate template;
    /*
     * 
db.series.aggregate([
    {
        $unwind: "$genres"
    },
    {
        $group:{
            _id:"$genres",
            count:{
                $sum:1}
        }
    },
    {
        $sort: {
            _id:1
        }
    }
])
     */
    public List<Document> listSeriesByGenres() {

        UnwindOperation unwindStage = Aggregation.unwind("genres");
        GroupOperation groupStage = Aggregation.group("genres")
            .count().as("count");
        SortOperation sortStage = Aggregation.sort(Sort.Direction.ASC,"_id");
        Aggregation pipeline = Aggregation.newAggregation(unwindStage,groupStage,sortStage);
        AggregationResults<Document> results = template.aggregate(pipeline,"series",Document.class);

        

        return results.getMappedResults();
      
    }
}
