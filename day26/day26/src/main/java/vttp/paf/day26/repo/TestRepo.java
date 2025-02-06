package vttp.paf.day26.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepo {
    
    @Autowired
    MongoTemplate template;

    public List<Document> testQueries() {
        Criteria criteria = Criteria.where("name").regex("true", "i");
        
        Query query = Query.query(criteria);
        List<Document> result = template.find(query, Document.class, "series");
        System.out.println("size of listdocs is " + result.size());



        return result;

        
    }
    public List<Document> testQueries2() {
        Criteria criteria = Criteria.where("name").regex("true", "i").
        and("rating.average").is(8.3);
        Criteria criteria2 = new Criteria();
        criteria2.andOperator(
            Criteria.where("name").regex("true", "i"),
            Criteria.where("rating.average").is(8.3)
        );
        
        Query query = Query.query(criteria2);
        List<Document> result = template.find(query, Document.class, "series");
        System.out.println("size of listdocs is " + result.size());

        return result;

        
    }
    public List<Document> testQueries3() {
        
        Criteria criteria = new Criteria();
        criteria.orOperator(
            Criteria.where("rating.average").gte(8.2),
            Criteria.where("weight").ne(99)
        );
        
        Query query = Query.query(criteria);
        query.limit(2);
        List<Document> result = template.find(query, Document.class, "series");
        System.out.println("size of listdocs is " + result.size());

        return result;

        
    }
    public List<Document> testQueries4() {
        
       Criteria criteria = Criteria.where("genres").size(2);
        
        Query query = Query.query(criteria);
        query.limit(2);
        List<Document> result = template.find(query, Document.class, "series");
        System.out.println("size of listdocs is " + result.size());

        return result;

        
    }
}
