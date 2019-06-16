package hibernate.document.model.collection.valueCollection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;

@Entity
public class ValueCollectionPerson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ElementCollection
	@OrderColumn(name = "phone_order")
	private List<String> phones = new ArrayList<>();

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

	public List<String> getPhones() {
		return phones;
	}

}
