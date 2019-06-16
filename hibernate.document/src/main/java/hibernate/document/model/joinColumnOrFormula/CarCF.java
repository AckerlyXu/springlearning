package hibernate.document.model.joinColumnOrFormula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinFormula;

@Entity
public class CarCF {
	@Id
	@GeneratedValue
	private Integer id;
	private String language;
	private String name;
	@ManyToOne
	@JoinColumnOrFormula(column = @JoinColumn(referencedColumnName = "language", name = "language", insertable = false, updatable = false)

	)
	@JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "is_default", value = "true"))

	private CarCountryCF cf;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CarCountryCF getCf() {
		return cf;
	}

	public void setCf(CarCountryCF cf) {
		this.cf = cf;
	}

	public Integer getId() {
		return id;
	}

}
