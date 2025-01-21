package vttp.paf.day26_chuk_workshop.repo;


import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static vttp.paf.day26_chuk_workshop.util.ConstantVar.C_MUSIC;
import static vttp.paf.day26_chuk_workshop.util.ConstantVar.F_RELEASED_YEAR;

@Repository
public class MusicRepo {
    
    @Autowired
    MongoTemplate template;

    public List<Integer> findYears() {
      return template.findDistinct(
         new Query(), F_RELEASED_YEAR, C_MUSIC, Integer.class
      );
    }

    public List<Document> findMusicByYear(Integer year) {

        Criteria criteria = Criteria.where(F_RELEASED_YEAR).is(year);

        Query query = Query.query(criteria);
        // db.music.find({released_year: 2023},{released_year:1,track_name:1,"artist(s)_name":1})

        query.fields()
            .include("track_name","artist(s)_name")
            .exclude("_id");
        List<Document> musicResults = template.find(query, Document.class, C_MUSIC);
        for (Document d : musicResults) {
            System.out.println(d.get("track_name"));
            
        }
        // System.out.println(musicResults.getFirst().toJson());

        return musicResults;
    }
}
