package telran.java52.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java52.book.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {

	List<Book> findByAuthorsName(String authorName);

	List<Book> findByPublisherPublisherName(String publisherName);

}
