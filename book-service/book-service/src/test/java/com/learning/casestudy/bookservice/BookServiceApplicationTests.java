package com.learning.casestudy.bookservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookServiceApplicationTests {

	@Autowired
	BookController bookController;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void testRetrieveBooks() {
		
		Book[] books = bookController.retrieveBooks();
		assertThat(books.length).isEqualTo(3);
		
		for (Book book: books) {
			
			assertThat(book.getBookId()).isNotBlank();
			assertThat(book.getAuthor()).isNotBlank();
			assertThat(book.getAvailableCopies()).isNotEqualTo(0);
			assertThat(book.getTotalCopies()).isNotEqualTo(0);
		}
	}
	
	@Test
	public void testConsumeMessage() {
		
		bookController.consumeMessage("B1001");
		Book[] books = bookController.retrieveBooks();
		
		for (Book book: books) {
		
			if (book.getBookId().equals("B1001")) {
				assertThat(book.getAvailableCopies()).isEqualTo(199);
			}
		}
	}
}
