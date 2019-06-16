package hibernate.document.model.persistencecontext;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SqlFragmentAlias;
import org.hibernate.annotations.Type;

@Entity
@SecondaryTable(name = "personDetail")
@FilterDef(name = "deleted", parameters = @ParamDef(name = "is_deleted", type = "boolean"))
@Filter(name = "deleted", aliases = {

		@SqlFragmentAlias(alias = "p", table = "filterperson"), @SqlFragmentAlias(alias = "pd", table = "personDetail")

}, condition = "{p}.name='ackerly' and {pd}.is_deleted = :is_deleted")
public class FilterPerson {
	@Id
	@Type(type = "uuid-char")
	@GeneratedValue()

	private UUID id;

	private String name;

	@Column(table = "personDetail")
	private boolean is_deleted;

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public UUID getId() {

		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
