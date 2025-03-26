package org.anshu.bookstore.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.anshu.bookstore.entities.Book;
import org.anshu.bookstore.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class BookControllerTest {

	@Mock
	private BookService bookService;

	@InjectMocks
	private BookController bookController;

	private Book book;

	@BeforeEach
	void addBookForTest() {
		MockitoAnnotations.openMocks(this); // Initialize mocks

		// Initialize a book object for testing
		book = new Book();
		book.setId(7L);
		book.setTitle("Things Fall Apart");
		book.setAuthor("Chinua Achebe");
		book.setPrice(BigDecimal.valueOf(109.45));
		book.setPublishedDate(LocalDate.of(1958, 03, 14));
	}

	@Test
	void testAddNewBook() {
		when(bookService.addBook(any(Book.class))).thenReturn(book);
		Book actualResult = bookController.addNewBook(book);

		// Assert that the result is not null and matches the expected values
		assertNotNull(actualResult);
		assertEquals("Things Fall Apart", actualResult.getTitle());
		assertEquals("Chinua Achebe", actualResult.getAuthor());
		assertEquals(BigDecimal.valueOf(109.45), actualResult.getPrice());

		// Verify that the service method was called once
		verify(bookService, times(1)).addBook(any(Book.class));
	}

	@Test
	void testGetBook_Found() {
		when(bookService.getBookById(7L)).thenReturn(Optional.of(book));
		ResponseEntity<Book> response = bookController.getBookById(7L);

		// Assert that the response status is 200 and the body matches the expected title
		assertEquals(200, response.getStatusCode().value());
		assertEquals("Things Fall Apart", response.getBody().getTitle());

		// Verify that the service method was called once with the correct ID
		verify(bookService, times(1)).getBookById(7L);
	}

	@Test
	void testGetBook_NotFound() {
		when(bookService.getBookById(9L)).thenReturn(Optional.empty());
		ResponseEntity<Book> response = bookController.getBookById(9L);

		// Assert that the response status is 404 and the body is null
		assertEquals(404, response.getStatusCode().value());
		assertNull(response.getBody());

		// Verify that the service method was called once with the correct ID
		verify(bookService, times(1)).getBookById(9L);
	}

	@Test
	void testGetBooks() {
		List<Book> bookList = Arrays.asList(book);
		when(bookService.getBooks()).thenReturn(bookList);
		List<Book> actualResult = bookController.getBooks();

		// Assert that the result is not null, contains one book, and matches the expected title
		assertNotNull(actualResult);
		assertEquals(1, actualResult.size());
		assertEquals("Things Fall Apart", actualResult.get(0).getTitle());

		// Verify that the service method was called once
		verify(bookService, times(1)).getBooks();
	}

	@Test
	void testUpdateBook() {
		when(bookService.updateBook(eq(7L), any(Book.class))).thenReturn(book);
		ResponseEntity<Book> response = bookController.updateBook(7L, book);

		// Assert that the response status is 200 and the body matches the expected title
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals("Things Fall Apart", response.getBody().getTitle());

		// Verify that the service method was called once with the correct ID and book
		verify(bookService, times(1)).updateBook(eq(7L), any(Book.class));
	}

	@Test
	void testDeleteBook() {
		doNothing().when(bookService).deleteBook(7L);
		ResponseEntity<String> response = bookController.deleteBook(7L);

		// Assert that the response status is 200 and the body contains the success message
		assertEquals(200, response.getStatusCode().value());
		assertEquals("Book with id:7 deleted successfully.", response.getBody());

		// Verify that the service method was called once with the correct ID
		verify(bookService, times(1)).deleteBook(7L);
	}
}