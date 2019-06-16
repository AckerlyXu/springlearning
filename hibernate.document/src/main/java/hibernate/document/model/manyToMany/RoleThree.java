package hibernate.document.model.manyToMany;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity

public class RoleThree {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RowAction> rowActions = new HashSet<RowAction>();

	private String name;

	public String getName() {
		return name;
	}

	public Set<RowAction> getRowActions() {
		return rowActions;
	}

	public void addAction(ActionThree action) {
		RowAction rowAction = new RowAction();
		rowAction.setRole(this);
		rowAction.setAction(action);

		action.getRowActions().add(rowAction);
		rowActions.add(rowAction);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleThree other = (RoleThree) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void removeAction(ActionThree action) {
		// 删除的逻辑还需要理解理解
		RowAction rowAction = new RowAction();
		rowAction.setAction(action);
		rowAction.setRole(this);
		action.getRowActions().remove(rowAction);
		rowActions.remove(rowAction);
		rowAction.setAction(null);
		rowAction.setRole(null);
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

}
