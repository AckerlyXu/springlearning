package hibernate.document.model.manyToMany;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity

public class RolePair {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 多对多不要设置级联删除
	@ManyToMany(mappedBy = "roles", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<ActionPair> actions = new HashSet<ActionPair>();
	private String name;

	public String getName() {
		return name;
	}

	public void addAction(ActionPair action) {
		actions.add(action);
		action.getRoles().add(this);
	}

	public void removeAction(ActionPair action) {
		actions.remove(action);
		action.getRoles().remove(this);
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ActionPair> getActions() {
		return actions;
	}

	public Integer getId() {
		return id;
	}

}
