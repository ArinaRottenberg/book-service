package telran.java52.book.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import telran.java52.book.dao.AuthorRepository;
import telran.java52.book.dao.BookRepository;
import telran.java52.book.dao.PublisherRepository;
import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;
import telran.java52.book.exception.EntityNotFoundException;
import telran.java52.book.model.Author;
import telran.java52.book.model.Book;
import telran.java52.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final ModelMapper modelMapper;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final BookRepository bookRepository;

	@Transactional
	@Override
	public Boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}

		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));

		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElseGet(() -> authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());

		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto removeBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto updateBookTitleByIsbn(String isbn, String newTitle) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(newTitle);
		bookRepository.save(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public List<BookDto> findBooksByAuthor(String authorName) {
		List<Book> books = bookRepository.findByAuthorsName(authorName);
		return books.stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<BookDto> findBooksByPublisher(String publisherName) {
		List<Book> books = bookRepository.findByPublisherPublisherName(publisherName);
		return books.stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<AuthorDto> findBookAuthorsByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors().stream().map(author -> modelMapper.map(author, AuthorDto.class))
				.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<Publisher> findBookPublishersByAuthor(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Boolean removeAuthor(String authorName) {
	    List<Book> books = bookRepository.findByAuthorsName(authorName);
	    if (books.isEmpty()) {
	        return false;
	    }
	    for (Book book : books) {
	        Set<Author> authors = book.getAuthors();
	        authors.removeIf(author -> author.getName().equals(authorName));
	        bookRepository.save(book);
	    }
	    return true;
	}

}
