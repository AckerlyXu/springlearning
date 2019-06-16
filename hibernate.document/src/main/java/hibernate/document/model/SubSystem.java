package hibernate.document.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Subsystem")
public class SubSystem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4922425569842355817L;

	@Id
	private String id;

	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SubSystem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubSystem(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	// Getters and setters are omitted for brevity
}