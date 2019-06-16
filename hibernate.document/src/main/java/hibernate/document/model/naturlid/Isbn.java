package hibernate.document.model.naturlid;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class Isbn implements Serializable {

	private String isbn10;

	private String isbn13;

	// Getters and setters are omitted for brevity

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Isbn isbn = (Isbn) o;
		return Objects.equals(isbn10, isbn.isbn10) && Objects.equals(isbn13, isbn.isbn13);
	}

	public String getIsbn10() {
		return isbn10;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn10, isbn13);
	}
}