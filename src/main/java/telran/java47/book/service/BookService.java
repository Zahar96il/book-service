package telran.java47.book.service;

import java.util.List;

import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.dto.PublisherDto;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);
	
	BookDto removeBook(String isbn);
	
	BookDto updateBookTitle(String isbn, String newTitle);
	
	List<BookDto> findBooksByAuthor(String name);
	
	List<BookDto> findBooksByPublisher(String publisher);
	
	List<AuthorDto> findBookAuthors(String isbn);
	
	List<PublisherDto> findPublishersByAuthor(String author);
	
	AuthorDto removeAuthor(String author);
	
	

}
