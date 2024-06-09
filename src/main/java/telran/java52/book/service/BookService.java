package telran.java52.book.service;


import java.util.List;

import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;
import telran.java52.book.model.Publisher;

public interface BookService {
	
	Boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);
	
	BookDto removeBookByIsbn(String isbn);

    BookDto updateBookTitleByIsbn(String isbn, String newTitle);
    
    List<BookDto> findBooksByAuthor(String authorName);
    
    List<BookDto> findBooksByPublisher(String publisherName);
    
    List<AuthorDto> findBookAuthorsByIsbn(String isbn);

    List<Publisher> findBookPublishersByAuthor(String isbn);

    Boolean removeAuthor(String authorName);

}
