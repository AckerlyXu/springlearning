package hibernate.document.model.naturlid;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity(name = "Book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	@NaturalId
	private String author;
	// natualId默认是不可变的
	@NaturalId(mutable = true)
	@Embedded
	private Isbn isbn;

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

	public Isbn getIsbn() {
		return isbn;
	}

	public void setIsbn(Isbn isbn) {
		this.isbn = isbn;
	}

	public Long getId() {
		return id;
	}

	// Getters and setters are omitted for brevity
}
