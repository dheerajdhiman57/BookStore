package com.learning.casestudy.bookservice;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Book[] retrieveBooks() {
		List<Book> bookList = bookRepository.findAll();
		Book[] books = new Book[bookList.size()];
		return bookList.toArray(books);
	}
	
    public void decrAvailableCount(String bookIdToBeUpdated) {
		Book bookToBeUpdated = bookRepository.findByBookId(bookIdToBeUpdated);
		if (bookToBeUpdated == null || bookToBeUpdated.getAvailableCopies() == 0) {
			throw new RuntimeException("Book with id: " + bookIdToBeUpdated + " not available!");
		} else {
			bookToBeUpdated.setAvailableCopies(bookToBeUpdated.getAvailableCopies() - 1);
			updateBook(bookToBeUpdated);
		}
    }
	
	@Transactional
	private void updateBook(Book bookToBeUpdated) {
		bookRepository.save(bookToBeUpdated);
	}
}
