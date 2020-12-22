package com.learning.casestudy.bookservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByAuthor(String author);
    Book findByBookId(String bookId);
}
