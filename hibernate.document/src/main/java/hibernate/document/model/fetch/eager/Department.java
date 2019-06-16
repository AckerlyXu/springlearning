package hibernate.document.model.fetch.eager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "department", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	// @Fetch(FetchMode.SELECT)
	// subselect不会造成n+1但是只要访问一个关联实体就会加载所有
	@Fetch(FetchMode.SUBSELECT)
	// JOIN相当于eager
	// @Fetch(FetchMode.JOIN)
	private List<Employee> employees = new ArrayList<Employee>();

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addEmployee(Employee employee) {
		getEmployees().add(employee);
		employee.setDepartment(this);
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public Integer getId() {
		return id;
	}

}
