package hibernate.document.model.collection.array;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@TypeDef(name = "comma", typeClass = CommaDelimitedStringsType.class)
public class ArrayBinaryPerson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public List<String> getListNames() {
		return listNames;
	}

	public void setListNames(List<String> listNames) {
		this.listNames = listNames;
	}

	private String[] names;
	@Type(type = "comma")
	private List<String> listNames;

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public Long getId() {
		return id;
	}

}
