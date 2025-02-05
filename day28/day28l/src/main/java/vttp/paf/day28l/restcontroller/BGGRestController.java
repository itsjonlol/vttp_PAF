package vttp.paf.day28l.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.paf.day28l.models.UserDto;
import vttp.paf.day28l.repo.BGGRepo;


@RestController
@RequestMapping("/api")
public class BGGRestController {
    
    @Autowired
    BGGRepo bggRepo;

    @GetMapping("/comments/{user}")
    public ResponseEntity<?> getUserComments(@PathVariable("user") String name) {
        List<UserDto> results = bggRepo.getCommentsFromUser(name);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(results);
    }
    


}
