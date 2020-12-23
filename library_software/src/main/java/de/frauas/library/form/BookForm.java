package de.frauas.library.form;

import java.sql.Date;


public class BookForm {
	
	private String title;
	
	private String authors;
	
	private String publisher;
	
	private Date publicationDate;
	
	private long isbn13;
	
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
