package vttp.paf.day27_chukws.repo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class CommentRepo {
    
    @Autowired
    MongoTemplate template;

    public void deleteCollection(String collectionName) {
        template.dropCollection(collectionName);
    }

    public void addCollection(String collectionName,String path) throws FileNotFoundException {
        template.createCollection(collectionName);

        FileReader fileReader = new FileReader(path);

		JsonReader r = Json.createReader(fileReader);
		JsonArray jsonArray = r.readArray();
        List<Document> documents = new ArrayList<>();
       
        for (int i = 0;i<jsonArray.size();i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            Document document = Document.parse(jsonObject.toString());
            String newId = jsonObject.getString("c_id");
            document.put("_id",newId);
            document.remove("c_id");
            //if inserting 1 by 1
            // template.insert(document,collectionName);
            //if inserting a list of documents
            documents.add(document);

        }
        //if inserting a list of documents
        template.insert(documents,collectionName);

        //third method for insertion
        // for (int i = 0; i<jsonArray.size(); i++) {
        //     JsonObject jsonObject = jsonArray.getJsonObject(i);

        //     JsonObjectBuilder builder = Json.createObjectBuilder();
        //     builder.add("_id", jsonObject.get("c_id"));

       
        //     for (String key : jsonObject.keySet()) {
        //         if (!key.equals("c_id")) { // Exclude 'c_id'
        //             builder.add(key, jsonObject.get(key));
        //         }
        //     }

        
        //     JsonObject updatedObject = builder.build();

        //     String jsonObjectString = updatedObject.toString();
        //     Document document = Document.parse(jsonObjectString);


        //     template.insert(document, collectionName);
        // }

        
    }
    public void searchComments(String collectionName,String... terms) {
      TextCriteria criterial = TextCriteria.forDefaultLanguage()
            .matchingAny(terms)
            .caseSensitive(true);

      TextQuery query = (TextQuery)TextQuery.queryText(criterial)
            .includeScore("similarity")
            .sortByScore()
            .limit(5);
      query.fields()
         .include("c_text", "similarity");


      template.find(query, Document.class, collectionName)
         .stream()  
         .forEach(d -> {
            System.out.printf(">>>>> %s\n\n", d.toJson());
         });
            
   }

   public void createTextIndex(String collectionName) {
    //my method
    // template.getCollection(collectionName).createIndex(
    //     new Document("c_text", "text") 
    // );
    
    
//notes
//     template.indexOps(collectionName).ensureIndex(
//     new Index().on("c_text", Sort.Direction.ASC).text()
// );
    //chuks method
    MongoCollection<Document> col =  template.getCollection(collectionName);
    col.createIndex(Indexes.text("c_text"));
    col.createIndex(Indexes.ascending("user"));
   
}
}
