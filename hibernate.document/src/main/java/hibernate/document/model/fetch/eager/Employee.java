package hibernate.document.model.fetch.eager;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.FetchProfile.FetchOverride;

@FetchProfile(name = "employee.department", fetchOverrides = @FetchOverride(entity = Employee.class, association = "department", mode = FetchMode.JOIN))
@Entity
public class Employee {
	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

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
