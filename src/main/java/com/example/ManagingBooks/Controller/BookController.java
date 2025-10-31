package com.example.ManagingBooks.Controller;

import com.example.ManagingBooks.Dto.BookRequest;
import com.example.ManagingBooks.Entity.BookEntity;
import com.example.ManagingBooks.Service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookEntity>> getBooksByAuthor(@RequestParam("author") String author) {
        log.info("Get Books By Author : {}", author);

        List<BookEntity> books = bookService.findByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookEntity> createBook(@Valid @RequestBody BookRequest request) {
        log.info("Create new book: {}", request.getTitle());

        BookEntity createdBook = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
}
