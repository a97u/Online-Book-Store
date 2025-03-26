package org.anshu.bookstore.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "Title cannot be blank")
	private String title;
	@NotBlank(message = "Author cannot be empty")
	private String author;
	@NotNull(message = "Price cannot be null")
	private BigDecimal price;
	private LocalDate publishedDate;

	//Constructors
	public Book() {
		super();
	}

	public Book(@NotBlank(message = "Title cannot be blank") String title,
			@NotBlank(message = "Author cannot be empty") String author,
			@NotNull(message = "Price cannot be null") BigDecimal price, LocalDate publishedDate) {
		super();
		this.title = title;
		this.author = author;
		this.price = price;
		this.publishedDate = publishedDate;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", price=" + price + ", publishedDate="
				+ publishedDate + "]";
	}
}
