package hibernate.document.model.naturlid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity
public class NatualPerson {
	@Id
	@GeneratedValue
	private Integer id;
	@NaturalId
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

}
