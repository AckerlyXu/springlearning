package hibernate.document.model.manyToMany;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ActionPair {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany
	private Set<RolePair> roles = new HashSet<RolePair>();
	private String name;

	public String getName() {
		return name;
	}

	public Set<RolePair> getRoles() {
		return roles;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

}
