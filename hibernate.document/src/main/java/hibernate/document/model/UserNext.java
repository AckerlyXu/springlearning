package hibernate.document.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
//@IdClass(PKNext.class)
public class UserNext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8705293140926301858L;
	@Id

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private SubSystem subsystem;
	@Id
	private String username;

	private String hobby;

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public PKNext getId() {
		return new PKNext(subsystem, username);
	}

	public void setId(PKNext id) {
		this.subsystem = id.getSubsystem();
		this.username = id.getUsername();
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
}
