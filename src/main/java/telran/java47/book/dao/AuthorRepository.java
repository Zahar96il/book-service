package telran.java47.book.dao;

import java.util.Optional;

import telran.java47.book.model.Author;

public interface AuthorRepository  {
	
	Optional<Author> findById(String authorName);

	void delete(Author author);
	
	Author save(Author author);

	
	
}
