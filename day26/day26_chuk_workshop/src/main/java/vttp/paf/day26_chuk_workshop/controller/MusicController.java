package vttp.paf.day26_chuk_workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vttp.paf.day26_chuk_workshop.model.Music;
import vttp.paf.day26_chuk_workshop.repo.MusicRepo;
import vttp.paf.day26_chuk_workshop.service.MusicService;



@Controller
public class MusicController {
    
    @Autowired
    MusicService musicService;

    @Autowired
    MusicRepo musicRepo;

    // @GetMapping("/")
    // @ResponseBody
    // public List<Integer> getYears() {
    //     return musicService.findYears();
    // }

    @GetMapping("/")
    public ModelAndView getYears() {
        List<Integer> yearList = musicService.findYears();
        ModelAndView mav = new ModelAndView("homepage");
        mav.addObject("yearlist",yearList);
       
        return mav;
    }

    @PostMapping("/search")
    public ModelAndView postSearch(@RequestParam("year") Integer year) {
        System.out.println(year);

        ModelAndView mav = new ModelAndView("searchpage");
        List<Music> musics = musicService.findAllMusicByYear(year);
        mav.addObject("year",year);
        mav.addObject("musics",musics);
        
        return mav;
    }
    
    @GetMapping("/{year}")
    @ResponseBody
    public List<?> getMethodName(@PathVariable("year") Integer year) {
        // return musicRepo.findMusicByYear(year);
        // List<Music> musicListt = new ArrayList<>();
        List<Music> musicList = musicService.findAllMusicByYear(year);
        return musicList;
    }
    

    
}
