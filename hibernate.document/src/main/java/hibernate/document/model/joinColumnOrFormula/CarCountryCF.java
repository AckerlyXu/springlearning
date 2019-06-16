package hibernate.document.model.joinColumnOrFormula;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CarCountryCF implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6814672810906179782L;

	@Id
	@GeneratedValue
	private Integer id;
	private Boolean is_default;
	private String language;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getIs_default() {
		return is_default;
	}

	public void setIs_default(Boolean is_default) {
		this.is_default = is_default;
	}

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
