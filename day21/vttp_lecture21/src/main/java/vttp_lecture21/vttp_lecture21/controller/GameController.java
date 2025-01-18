package vttp_lecture21.vttp_lecture21.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vttp_lecture21.vttp_lecture21.model.Game;
import vttp_lecture21.vttp_lecture21.service.GameService;


@Controller
@RequestMapping
public class GameController {
    

    @Autowired
    GameService gameService;

    
    @GetMapping("/games")
    public ModelAndView getGames(@RequestParam(defaultValue="10") int limit) {


        ModelAndView mav = new ModelAndView();
        
        List<Game> games = gameService.getGames(limit);
        mav.setViewName("games");
        mav.addObject("games",games);
        mav.setStatus(HttpStatusCode.valueOf(200));
        return mav;
    }
    
}
