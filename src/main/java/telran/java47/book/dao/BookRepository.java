package telran.java47.book.dao;


import java.util.stream.Stream;
import org.springframework.data.repository.PagingAndSortingRepository;

import telran.java47.book.model.Book;
public interface BookRepository extends PagingAndSortingRepository<Book, String> {
	
	Stream<Book> findByAuthorsName(String name);
	
	Stream<Book> findByPublisherPublisherNameIgnoreCase(String publisher);
	
	

}
