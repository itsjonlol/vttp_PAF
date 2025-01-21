package vttp.paf.day26_chuk_workshop.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day26_chuk_workshop.model.Music;
import vttp.paf.day26_chuk_workshop.repo.MusicRepo;

@Service
public class MusicService {
    
    @Autowired
    MusicRepo musicRepo;

    public List<Integer> findYears() {
        return musicRepo.findYears();
    }

    public List<Music> findAllMusicByYear(Integer year) {
        List<Document> documentMusicResults = musicRepo.findMusicByYear(year);
        List<Music> musicList = new ArrayList<>();
        for (Document document : documentMusicResults) {
            Music music = new Music();
           
            //bro why the fk cant i use getString
            music.setTrackName(document.get("track_name").toString());
            music.setArtists(document.get("artist(s)_name").toString());

            
            System.out.println(music.toString());
            musicList.add(music);

        }
        return musicList;
    }


}
