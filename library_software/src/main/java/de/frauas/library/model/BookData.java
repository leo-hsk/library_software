package de.frauas.library.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Contains core book information.
 * Represents the description of a book.
 * POJO defined as an Entity so that data can be persisted to the database.
 * @author Leonard
 *
 */
@Entity
@Table(name = "book_data")
public class BookData {
	
	@NotNull
	private String title;
	
	@NotNull
	private String authors;
	
	@NotNull
	private String publisher;
	
	@NotNull
	private Date publicationDate;
	
	@NotNull
	@Id
	private long isbn13;
	
	@OneToMany(mappedBy = "bookData")
	private List<Book> books;

	public BookData() {
	}
	
	public BookData( String title, String authors, String publisher, Date publicationDate, long isbn13) {
		super();
		this.title = title;
		this.authors = authors;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.isbn13 = isbn13;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public long getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(long isbn13) {
		this.isbn13 = isbn13;
	}
}