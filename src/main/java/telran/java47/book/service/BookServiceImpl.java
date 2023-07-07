package telran.java47.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java47.book.dao.AuthorRepository;
import telran.java47.book.dao.BookRepository;
import telran.java47.book.dao.PublisherRepository;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.dto.PublisherDto;
import telran.java47.book.dto.exceptions.EntityNotFoundException;
import telran.java47.book.model.Author;
import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		// Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		// Author
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
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

	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto updateBookTitle(String isbn, String newTitle) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(newTitle);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public List<BookDto> findBooksByAuthor(String name) {
		return bookRepository.findByAuthorsName(name).map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<BookDto> findBooksByPublisher(String publisher) {
		return bookRepository.findByPublisherPublisherNameIgnoreCase(publisher)
				.map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());

	}

	@Override
	@Transactional
	public List<PublisherDto> findPublishersByAuthor(String author) {
		List<Book> booksByAuthor = bookRepository.findByAuthorsName(author).collect(Collectors.toList());
		List<PublisherDto> publishers = booksByAuthor.stream().map(p -> p.getPublisher()).map(p -> modelMapper.map(p, PublisherDto.class))
				.collect(Collectors.toList());
		return publishers;
	}

	@Override
	public AuthorDto removeAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

}
