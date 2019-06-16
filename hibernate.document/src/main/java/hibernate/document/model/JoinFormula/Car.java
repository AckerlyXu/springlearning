package hibernate.document.model.JoinFormula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.JoinFormula;

@Entity
public class Car {
	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	@ManyToOne
	// 相当于joinFormula,不过可以对外键进行一定的处理以后再匹配
	@JoinFormula(referencedColumnName = "name", value = "substr(name,4)")
	private CarCountry carCountry;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CarCountry getCarCountry() {
		return carCountry;
	}

	public void setCarCountry(CarCountry carCountry) {
		this.carCountry = carCountry;
	}

	public Integer getId() {
		return id;
	}
}
