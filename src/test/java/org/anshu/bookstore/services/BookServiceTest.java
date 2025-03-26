package org.anshu.bookstore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.anshu.bookstore.entities.Book;
import org.anshu.bookstore.exception.ResourceNotFoundException;
import org.anshu.bookstore.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

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
	void testAddBook() {
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		Book result = bookService.addBook(book);

		// Assert that the result is not null and matches the expected values
		assertNotNull(result);
		assertEquals("Things Fall Apart", result.getTitle());
		assertEquals("Chinua Achebe", result.getAuthor());

		// Verify that the save method was called once
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	void testGetBooks() {
		List<Book> bookList = Arrays.asList(book);
		when(bookRepository.findAll()).thenReturn(bookList);
		List<Book> result = bookService.getBooks();

		// Assert that the result is not null, contains one book, and matches the expected title
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Things Fall Apart", result.get(0).getTitle());

		// Verify that the findAll method was called once
		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testGetBookById_Found() {
		when(bookRepository.existsById(7L)).thenReturn(true);
		when(bookRepository.findById(7L)).thenReturn(Optional.of(book));
		Optional<Book> result = bookService.getBookById(7L);

		// Assert that the result is present and matches the expected title
		assertTrue(result.isPresent());
		assertEquals("Things Fall Apart", result.get().getTitle());

		// Verify that the existsById and findById methods were called once
		verify(bookRepository, times(1)).existsById(7L);
		verify(bookRepository, times(1)).findById(7L);
	}

	@Test
	void testGetBookById_NotFound() {
		when(bookRepository.existsById(9L)).thenReturn(false);

		// Assert that a ResourceNotFoundException is thrown when trying to get a non-existent book
		assertThrows(ResourceNotFoundException.class, () -> {
			bookService.getBookById(9L);
		});

		// Verify that the existsById method was called once and findById was never called
		verify(bookRepository, times(1)).existsById(9L);
		verify(bookRepository, never()).findById(9L);
	}

	@Test
	void testUpdateBook_Found() {
		Book updatedBook = new Book();
		updatedBook.setTitle("You Between Us");
		updatedBook.setAuthor("Chinua Achebe");
		updatedBook.setPrice(BigDecimal.valueOf(105.99));
		updatedBook.setPublishedDate(LocalDate.of(1969, 7, 11));
		
		when(bookRepository.findById(7L)).thenReturn(Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

		Book result = bookService.updateBook(7L, updatedBook);

		// Assert that the result is not null and matches the updated values
		assertNotNull(result);
		assertEquals("You Between Us", result.getTitle());
		assertEquals("Chinua Achebe", result.getAuthor());
		assertEquals(BigDecimal.valueOf(105.99), result.getPrice());
		assertEquals(LocalDate.of(1969, 7, 11), result.getPublishedDate());

		// Verify that the findById and save methods were called once
		verify(bookRepository, times(1)).findById(7L);
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	void testUpdateBook_NotFound() {
		when(bookRepository.findById(9L)).thenReturn(Optional.empty());

		// Assert that a ResourceNotFoundException is thrown when trying to update a non-existent book
		assertThrows(ResourceNotFoundException.class, () -> {
			bookService.updateBook(9L, new Book());
		});

		// Verify that the findById method was called once and save was never called
		verify(bookRepository, times(1)).findById(9L);
		verify(bookRepository, never()).save(any(Book.class));
	}

	@Test
	void testDeleteBook_Found() {
		when(bookRepository.existsById(7L)).thenReturn(true);
		doNothing().when(bookRepository).deleteById(7L);
		bookService.deleteBook(7L);

		// Verify that the existsById and deleteById methods were called once
		verify(bookRepository, times(1)).existsById(7L);
		verify(bookRepository, times(1)).deleteById(7L);
	}

	@Test
	void testDeleteBook_NotFound() {
		when(bookRepository.existsById(9L)).thenReturn(false);

		// Assert that a ResourceNotFoundException is thrown when trying to delete a non-existent book
		assertThrows(ResourceNotFoundException.class, () -> {
			bookService.deleteBook(9L);
		});

		// Verify that the existsById method was called once and deleteById was never called
		verify(bookRepository, times(1)).existsById(9L);
		verify(bookRepository, never()).deleteById(9L);
	}
}