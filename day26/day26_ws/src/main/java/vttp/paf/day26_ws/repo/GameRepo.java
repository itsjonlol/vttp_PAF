package vttp.paf.day26_ws.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.paf.day26_ws.model.Game;
import vttp.paf.day26_ws.model.GameDTO;
import vttp.paf.day26_ws.model.GameResponse;
import static vttp.paf.day26_ws.util.ConstantVar.C_GAMES;
import static vttp.paf.day26_ws.util.ConstantVar.F_GID;
import static vttp.paf.day26_ws.util.ConstantVar.F_RANKING;
import static vttp.paf.day26_ws.util.toGame.toGameFromDocument;

@Repository
public class GameRepo {
    
    @Autowired
    MongoTemplate template;


    
    /*
     * db.games.find().limit(<limit>).skip(<offset>)
     * 
     */
    /*
     * db.games.find().limit(25).skip(5).sort({ranking:-1})
     */
    public GameResponse getAllGamesResponse(Integer limit, Integer offset,Boolean sortByRank) {

        // Criteria criteria = new Criteria();
        Query query = new Query();
        if (!sortByRank) {
            query.limit(limit).skip(offset);
        } 
        query.limit(limit)
            .skip(offset)
            .with(Sort.by(Sort.Direction.ASC, 
        F_RANKING));
        

        List<Document> documents = template.find(query,Document.class,C_GAMES);
        GameResponse gameResponse = new GameResponse();
        List<GameDTO> gamesDTO = documents.stream()
                                    .map(document -> {
                                        GameDTO gameDTO = new GameDTO();
                                        gameDTO.setGid(document.getInteger("gid"));
                                        gameDTO.setName(document.getString("name"));
                                        return gameDTO;
                                    })
                                    .toList();
        
        long count = template.count(new Query(), C_GAMES);
        gameResponse.setGames(gamesDTO);
        gameResponse.setTotal(count);
        gameResponse.setLimit(limit);
        gameResponse.setOffset(offset);
                                    
                                                                
        return gameResponse;
        
    }
    // db.games.findOne({gid:<id>})
    public Optional<Game> getGameById(Integer id) {
        Criteria criteria = Criteria
        .where(F_GID).is(id);

        Query query = Query.query(criteria);
        Document document = template.findOne(query, Document.class,C_GAMES);
        if (document==null) {
            return Optional.empty();
        }
        Game game = toGameFromDocument(document);
        

        

        return Optional.of(game);
    }

    public Document getGameDocuments(Integer limit,Integer offset,Boolean sortByRank) {
        Query query = new Query();
        if (!sortByRank) {
            query.limit(limit).skip(offset);
        } 
        query.fields()
        .include("gid","name")
        .exclude("_id");
        
        query.limit(limit)
            .skip(offset)
            .with(Sort.by(Sort.Direction.ASC, 
        F_RANKING));
        

        List<Document> documents = template.find(query,Document.class,C_GAMES);
        Document bigDocument = new Document();
        bigDocument.put("games",documents);
        bigDocument.put("limit",limit);
        bigDocument.put("offset",offset);
        bigDocument.put("date", new Date());
        return bigDocument;
    }
}
