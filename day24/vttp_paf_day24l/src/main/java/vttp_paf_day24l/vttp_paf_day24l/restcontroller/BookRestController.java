package vttp_paf_day24l.vttp_paf_day24l.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_paf_day24l.vttp_paf_day24l.model.Book;
import vttp_paf_day24l.vttp_paf_day24l.repo.BookRepo;


@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    BookRepo bookRepo;

    @GetMapping("")
    public ResponseEntity<?> getBooks() {
        return ResponseEntity.ok().header("Content-Type", "application/json").body(bookRepo.getAllBooks());
    }

    @GetMapping("/{book-id}")
    public ResponseEntity<?> getBookById(@PathVariable("book-id") Integer bookId) {
        return ResponseEntity.ok().header("Content-Type", "application/json").body(bookRepo.getBookById(bookId));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createBook(@RequestBody Book book) {
        Boolean bCreated = bookRepo.insertBook(book);
        
        return ResponseEntity.ok().header("Content-Type", "application/json").body(bCreated);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateBook(@PathVariable("id") Integer id, @RequestBody Book book) {
        Boolean bUpdated = bookRepo.updateBook(book,id);
        
        return ResponseEntity.ok().header("Content-Type", "application/json").body(bUpdated);
    }
    
    @PutMapping("/status/{id}")
    public ResponseEntity<Boolean> updateBookStatus(@PathVariable("id") Integer id, @RequestBody Book book) {
        Boolean bUpdated = bookRepo.updateBookStatus
        (book);
        
        return ResponseEntity.ok().header("Content-Type", "application/json").body(bUpdated);
    }
    
    
    


    
}
