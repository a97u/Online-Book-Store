package org.anshu.bookstore.controllers;

import java.util.List;
import org.anshu.bookstore.entities.Book;
import org.anshu.bookstore.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	// Auto-inject BookService dependency using Spring's dependency injection
	@Autowired
	private BookService bookService;

	// to create a new book resource
	@PostMapping("/book")
	public Book addNewBook(@RequestBody Book book) {
		logger.info("Adding a new book: {}", book.getTitle());
		return bookService.addBook(book);
	}

	// to retrieve a single book by its ID & return ResponseEntity with Book if found, 404 Not Found otherwise
	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
		logger.debug("Retrieving book with ID:{}", id);
		return bookService.getBookById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// to retrieve all books in the system & return List of all Book objects
	@GetMapping("/books")
	public List<Book> getBooks() {
		logger.info("Fetching All Books");
		return bookService.getBooks();
	}

	// to update an existing book resource by its ID & return Updated Book object wrapped in ResponseEntity
	@PutMapping("/book/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
		logger.info("Updating book with ID: {}", id);
		return ResponseEntity.ok(bookService.updateBook(id, book));
	}

	// to delete a book by its ID & return Confirmation message wrapped in ResponseEntity
	@DeleteMapping("/book/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
		logger.info("Deleting book with ID: {}", id);
		bookService.deleteBook(id);
		return ResponseEntity.ok("Book with id:" + id + " deleted successfully.");
	}
}