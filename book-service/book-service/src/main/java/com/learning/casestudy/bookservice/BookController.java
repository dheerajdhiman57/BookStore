package com.learning.casestudy.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping("/books")
	public Book[] retrieveBooks() {
		return bookService.retrieveBooks();
	}

    @KafkaListener(topics = {"spring-kafka-book-subscription-group"})
    public void consumeMessage(String bookIdToBeUpdated) {
        bookService.decrAvailableCount(bookIdToBeUpdated);
    }
}
