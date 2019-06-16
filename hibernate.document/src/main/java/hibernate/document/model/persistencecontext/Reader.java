package hibernate.document.model.persistencecontext;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@FilterDef(name = "active_filter", parameters = @ParamDef(name = "active", type = "boolean"))
@Entity
public class Reader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Filter(name = "active_filter", condition = "active_status=:active")
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<FilterBook> books = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public Set<FilterBook> getBooks() {
		return books;
	}

}
