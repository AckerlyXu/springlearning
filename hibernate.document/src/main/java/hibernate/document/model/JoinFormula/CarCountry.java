package hibernate.document.model.JoinFormula;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CarCountry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6814672810906179782L;

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

}
