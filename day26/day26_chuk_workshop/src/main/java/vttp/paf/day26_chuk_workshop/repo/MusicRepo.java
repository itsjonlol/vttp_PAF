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

      
          // Criteria criteria = Criteria
          //   .where(F_RELEASED_YEAR).gte(1980)
          //   .andOperator(
          //       Criteria.where(F_RELEASED_YEAR).lte(2000));
          // Query query = Query.query(criteria);

          Criteria criteria2 = new Criteria();
          criteria2.andOperator(
              Criteria.where(F_RELEASED_YEAR).gte(1980),
              Criteria.where(F_RELEASED_YEAR).lte(2000)
          );
          Query query = Query.query(criteria2);
          return template.findDistinct(query,
            F_RELEASED_YEAR, C_MUSIC, Integer.class);

      //   Query query = new Query();
      // return template.findDistinct(
      //    new Query(), F_RELEASED_YEAR, C_MUSIC, Integer.class
      
    }
     // db.spotify_songs.distinct('released_year')
    // public List<Integer> getYears() {
    //     Criteria criteria = Criteria
    //         .where(F_RELEASED_YEAR).gte(1980)
    //         .andOperator(
    //             Criteria.where(F_RELEASED_YEAR).lte(1989));

    //     Query query = Query.query(criteria);

    //     //return template.findDistinct(query,
    //     return template.findDistinct(new Query(),
    //         F_RELEASED_YEAR, C_SPOTIFY_SONGS, Integer.class);
    // }



    /*
     * db.music
     *   .find({released_year: year})
     *   .projection({_id:0,track_name:1,"artist(s)_name":1})
     *   .sort(track_name:1,"artist(s)_name":1})
     */
    public List<Document> findMusicByYear(Integer year) {

        Criteria criteria = Criteria.where(F_RELEASED_YEAR).is(year);

        Query query = Query.query(criteria);
        

        query.fields()
            .include("track_name","artist(s)_name")
            .exclude("_id");
        List<Document> musicResults = template.find(query, Document.class, C_MUSIC);
        
        // System.out.println(musicResults.getFirst().toJson());

        return musicResults;
    }

    // db.spotify_songs
  //    .find({ released_year: year })
  //    .projection({ _id: 0, track_name: 1, 'artist(s)_name': 1 })
  //    .sort({ track_name: 1, 'artist(s)_name': 1 })
//   public List<ArtistSong> findTrackByYear(int year) {

//     Criteria criteria = Criteria.where(F_RELEASED_YEAR).is(year);

//     Query query = Query.query(criteria)
//         .with(Sort.by(Sort.Direction.ASC, 
//               F_TRACK_NAME, F_ARTISTS_NAME));
//     query.fields()
//         .include(F_TRACK_NAME, F_ARTISTS_NAME)
//         .exclude("_id");

//     return template.find(query, Document.class, C_SPOTIFY_SONGS)
//         .stream()
//         .map(doc -> {
//           ArtistSong as = new ArtistSong();
//           try {
//             as.setTrackName(doc.getString(F_TRACK_NAME));
//           } catch (Exception ex) {
//             as.setTrackName(doc.get(F_TRACK_NAME).toString());
//           }
//           as.setArtistName(doc.getString(F_ARTISTS_NAME));
//           return as;
//         })
//         .toList();
      
//   }
}
