package vttp_paf_day24l.vttp_paf_day24l.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_paf_day24l.vttp_paf_day24l.repo.BookRepo;

@RestController
@RequestMapping("/api")
public class BookRestController {

    @Autowired
    BookRepo bookRepo;


    
}
