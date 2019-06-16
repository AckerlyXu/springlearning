package hibernate.document.model.manyToMany;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "`Role`")
@FilterDef(name = "action", parameters = { @ParamDef(name = "action_id", type = "integer") })
//@Filter(name = "action", condition = "action_id<:action_id")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 多对多不要设置级联删除
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "role_action", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "action_id"))
	// 这个whereJoinTable可以filter中间表的数据前提是中间表得有这么个列
	// @WhereJoinTable(clause = "created_on > CURRENT_TIMESTAMP()")

	@FilterJoinTable(name = "action", condition = "action_id<:action_id")
	private Set<Action> actions = new HashSet<>();
	private String name;

	public String getName() {
		return name;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

}
