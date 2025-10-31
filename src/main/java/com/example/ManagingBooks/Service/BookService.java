package com.example.ManagingBooks.Service;

import com.example.ManagingBooks.Dto.BookRequest;
import com.example.ManagingBooks.Entity.BookEntity;
import com.example.ManagingBooks.Repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> findByAuthor(String author) {
        log.info("Find books by author: {}", author);
        return bookRepository.findByAuthor(author);
    }


    public BookEntity createBook(BookRequest request) {
        log.info("Create book: {}", request.getTitle());

        try {
            if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("Title cannot be empty");
            }
            if (request.getAuthor() == null || request.getAuthor().trim().isEmpty()) {
                throw new IllegalArgumentException("Author cannot be empty");
            }

            if (request.getPublishedDate() == null || request.getPublishedDate().trim().isEmpty()) {
                throw new IllegalArgumentException("Published date cannot be empty.");
            }

            LocalDate publishedDate = parseBuddhistDate(request.getPublishedDate());
            int year = publishedDate.getYear();
            int currentYear = Year.now().getValue();

            if (year <= 1000 || year > currentYear) {
                throw new IllegalArgumentException("Invalid published year: " + year);
            }

            BookEntity book = new BookEntity();
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setPublishedDate(publishedDate);

            long id = bookRepository.createBook(book);
            book.setId(id);
            log.info("Book saved successfully with ID {}", id);
            return book;

        } catch (Exception e) {
            log.error("Error create book: {}", e.getMessage(), e);
            throw e;
        }
    }


    private LocalDate parseBuddhistDate(String buddhistDate) {
        try {

            if (buddhistDate == null || buddhistDate.trim().isEmpty()) {
                throw new IllegalArgumentException("Date string is null or empty. Expected yyyy-MM-dd");
            }

            LocalDate date = LocalDate.parse(buddhistDate);
            if (date.getYear() > 2500) {
                date = date.minusYears(543);
            }
            return date;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd", e);
        }
    }
}
