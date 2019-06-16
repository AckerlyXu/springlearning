package hibernate.document.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class PKNext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SubSystem subsystem;

	private String username;

	public PKNext(SubSystem subsystem, String username) {
		this.subsystem = subsystem;
		this.username = username;
	}

	public PKNext() {
	}

	public SubSystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(SubSystem subsystem) {
		this.subsystem = subsystem;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PKNext pk = (PKNext) o;
		return Objects.equals(subsystem, pk.subsystem) && Objects.equals(username, pk.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(subsystem, username);
	}
}