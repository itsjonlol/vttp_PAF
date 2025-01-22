package vttp.paf.day27l.repo;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
@Repository
public class TaskRepo {
    
    @Autowired
    MongoTemplate template;
    public static final String C_TASKS = "tasks";
    public static final String C_COMMENTS = "comments";
    /*
     * db.tasks.insert({
     *  name:"Jogging",
     *  priority:1,
     *  to_bring: ["water","phone","insect repellant"]
     *  })
     */
    public void insertTask() {
        Document toInsert = new Document();
        toInsert.put("name","Jogging");
        toInsert.put("priority",1);
        
        List<String> toBring = new ArrayList<>();
        toBring.add("water");
        toBring.add("phone");
        toBring.add("insect repellant");
        toInsert.put("to_bring",toBring);

        System.out.printf(">>>to insert: %s\n",toInsert.toJson());
        
        Document insertedDoc = template.insert(toInsert,C_TASKS);
        ObjectId id = insertedDoc.getObjectId("_id");

        System.out.printf(">>>after insert: %s\n",insertedDoc.toJson());
        System.out.printf(">>>id after insert: %s\n",id.toHexString());


        
    }

    public void insertTask2() {
        JsonArray friends = Json.createArrayBuilder()
            .add("fred")
            .add("barney")
            .build();
        JsonObject toInsert = Json.createObjectBuilder()
            .add("name","CNY shopping")
            .add("friends",friends)
            .add("venue","chinatown")
            .add("priority",1)
            .build();
        
            String jsonString = toInsert.toString();
            //JsonObject to Document

            Document docToInsert = Document.parse(jsonString);
            // Document result = template.insert(docToInsert,C_TASKS);
            //type inference
            var result = template.insert(docToInsert,C_TASKS);
            // System.out.printf(">>>> result: %s\n",result);
            // System.out.printf(">>. _id: %s\n",result.getString("_id"));

            //Document -> JsonObject
            jsonString = result.toString();
            JsonReader r = Json.createReader(new StringReader(jsonString));
            var jsonResult = r.readObject();
            System.out.printf(">>> in JSON-P: %s\n",jsonResult.toString());
            

    }

    public void update() {
        String id = "2b881173";
        Criteria criteria = Criteria.where("_id").is(id);

        Query query = Query.query(criteria);
        Update updateOps = new Update()
            .set("time","2pm")
            .set("venue","Bugis")
            .push("friends","betty");
        
        UpdateResult result = template.updateFirst(query, updateOps, javax.swing.text.Document.class,C_TASKS);

        System.out.printf(">>>Matched: %d\n",result.getMatchedCount());
        System.out.printf(">>>Modify: %d\n",result.getModifiedCount());
        System.out.printf(">>>Upserted: %d\n",result.getUpsertedId());
        
    }
    
    public void searchComments(String... terms) {
        TextCriteria criterial = TextCriteria.forDefaultLanguage()
            .matchingAny(terms)
            .caseSensitive(true);
        TextQuery query = (TextQuery) TextQuery.queryText(criterial)
            .includeScore("similarity")
            .sortByScore()
            .limit(5);
        query.fields()
        .include("c_text","similarity");
        template.find(query,Document.class,C_COMMENTS)
            .stream()
            .forEach(d -> {
                System.out.printf(">>>>%s\n\n",d.toJson());
            });
    }
}
