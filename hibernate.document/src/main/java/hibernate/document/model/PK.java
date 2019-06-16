package hibernate.document.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length = 40)
	private String username;
	@Column(length = 40)
	private String password;

	public PK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PK(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password);

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PK other = (PK) obj;
		return Objects.equals(username, other.getUsername()) && Objects.equals(password, other.getPassword());
	}

}
