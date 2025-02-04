package vttp_lecture22.vttp_lecture22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vttp_lecture22.vttp_lecture22.model.Summary;
import vttp_lecture22.vttp_lecture22.repo.KindleRepo;


@RestController
@RequestMapping("/api")
public class SummaryRestController {

    @Autowired
    KindleRepo kindleRepo;

    @GetMapping("") //http://localhost:4000/api?limit=5&bookCount=200
    public ResponseEntity<?> getMethodName(@RequestParam(defaultValue="50") Integer bookCount,
    @RequestParam(defaultValue="10") Integer limit) {
        List<Summary> summaries = kindleRepo.getSummaries(bookCount, limit);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(summaries);
    }
    
    
}
