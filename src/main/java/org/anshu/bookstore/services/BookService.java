package org.anshu.bookstore.services;

import java.util.List;
import java.util.Optional;
import org.anshu.bookstore.entities.Book;
import org.anshu.bookstore.exception.ResourceNotFoundException;
import org.anshu.bookstore.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	// Inject BookRepository to interact with the database
	@Autowired
	private BookRepository bookRepository;

	// Add a new book to the database
	public Book addBook(Book book) {
		logger.info("Adding book: {}", book.getTitle());
		return bookRepository.save(book);
	}

	// Retrieve all books from the database
	public List<Book> getBooks() {
		logger.info("Fetching all books");
		return bookRepository.findAll();
	}

	// Retrieve a book by its ID, throw exception if not found
	public Optional<Book> getBookById(Long id) {
		logger.debug("Fetching book by ID: {}", id);
		if (!bookRepository.existsById(id)) {
			logger.error("Book with ID {} not found", id);
			throw new ResourceNotFoundException("Book with id: " + id + " not found.");
		}
		return bookRepository.findById(id);
	}

	// Update an existing book by its ID, throw exception if not found
	public Book updateBook(Long id, Book book) {
		logger.info("Updating book with ID: {}", id);
		Book availaBook = bookRepository.findById(id).orElseThrow(() -> {
			logger.error("Book with ID {} not found for update", id);
			return new ResourceNotFoundException("Book with id: " + id + " not found.");
		});
		availaBook.setTitle(book.getTitle());
		availaBook.setAuthor(book.getAuthor());
		availaBook.setPrice(book.getPrice());
		availaBook.setPublishedDate(book.getPublishedDate());
		return bookRepository.save(availaBook);
	}

	// Delete a book by its ID, throw exception if not found
	public void deleteBook(Long id) {
		logger.info("Deleting book with ID: {}", id);
		if (!bookRepository.existsById(id)) {
			logger.error("Book with ID {} not found for deletion", id);
			throw new ResourceNotFoundException("Book with id: " + id + " not found.");
		}
		bookRepository.deleteById(id);
	}
}