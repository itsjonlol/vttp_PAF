package vttp.paf.day27_chukws.repo;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
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

        for (int i = 0; i<jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);

            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("_id", jsonObject.get("c_id"));

       
            for (String key : jsonObject.keySet()) {
                if (!key.equals("c_id")) { // Exclude 'c_id'
                    builder.add(key, jsonObject.get(key));
                }
            }

        
            JsonObject updatedObject = builder.build();

            String jsonObjectString = updatedObject.toString();
            Document document = Document.parse(jsonObjectString);


            template.insert(document, collectionName);
        }

        
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
    template.getCollection(collectionName).createIndex(
        new Document("c_text", "text") 
    );
   
}
}
