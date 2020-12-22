DROP TABLE BOOK;

CREATE TABLE BOOK (
    book_id varchar(100) primary key,
    book_name varchar(100),
    author varchar(100),
    available_copies numeric(5),
   total_copies numeric(5)
);


insert into book (book_id, book_name, author, available_copies, total_copies)
values ('B1001', 'RDBMS', 'John', 200, 200);

insert into book (book_id, book_name, author, available_copies, total_copies)
values ('B1002', 'Java IO', 'Steve', 20, 20);

insert into book (book_id, book_name, author, available_copies, total_copies)
values ('B1003', 'ABC of Programmering', 'Donald', 10, 10);
 