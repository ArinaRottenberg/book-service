package telran.java52.book.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;
import telran.java52.book.model.Publisher;
import telran.java52.book.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    final BookService bookService;

    @PostMapping("/book")
    public Boolean addBook(@RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    @GetMapping("/book/{isbn}")
    public BookDto findBookByIsbn(@PathVariable String isbn) {
        return bookService.findBookByIsbn(isbn);
    }

    @DeleteMapping("/book/{isbn}")
    public BookDto removeBookByIsbn(@PathVariable String isbn) {
        return bookService.removeBookByIsbn(isbn);
    }

    @PutMapping("/book/{isbn}/title/{newTitle}")
    public BookDto updateBookTitleByIsbn(@PathVariable String isbn, @PathVariable String newTitle) {
        return bookService.updateBookTitleByIsbn(isbn, newTitle);
    }

    @GetMapping("/books/author/{authorName}")
    public List<BookDto> findBooksByAuthor(@PathVariable String authorName) {
        return bookService.findBooksByAuthor(authorName);
    }

    @GetMapping("/books/publisher/{publisherName}")
    public List<BookDto> findBooksByPublisher(@PathVariable String publisherName) {
        return bookService.findBooksByPublisher(publisherName);
    }

    @GetMapping("/authors/book/{isbn}")
    public List<AuthorDto> findBookAuthorsByIsbn(@PathVariable String isbn) {
        return bookService.findBookAuthorsByIsbn(isbn);
    }

    @GetMapping("/publishers/author/{authorName}")
    public List<Publisher> findBookPublishersByAuthor(@PathVariable String authorName) {
        return bookService.findBookPublishersByAuthor(authorName);
    }

    @DeleteMapping("/author/{authorName}")
    public Boolean removeAuthor(@PathVariable String authorName) {
        return bookService.removeAuthor(authorName);
    }
}

