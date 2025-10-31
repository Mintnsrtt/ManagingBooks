package com.example.ManagingBooks;

import com.example.ManagingBooks.Dto.BookRequest;
import com.example.ManagingBooks.Entity.BookEntity;
import com.example.ManagingBooks.Repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateAndGetBook() {
        // 1. POST /books
        BookRequest request = new BookRequest("Test Book", "Alice", "2567-05-01");

        ResponseEntity<BookEntity> postResponse =
                restTemplate.postForEntity("/books", request, BookEntity.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        assertNotNull(postResponse.getBody().getId());
        assertEquals("Test Book", postResponse.getBody().getTitle());

        System.out.println("Status: " + postResponse.getStatusCode());
        System.out.println("Response: " + postResponse.getBody());

        // 2. GET /books?author=Alice
        ResponseEntity<BookEntity[]> getResponse =
                restTemplate.getForEntity("/books?author=Alice", BookEntity[].class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertTrue(getResponse.getBody().length > 0);
        assertEquals("Alice", getResponse.getBody()[0].getAuthor());
    }
}