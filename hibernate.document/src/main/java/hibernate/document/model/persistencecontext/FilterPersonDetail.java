package hibernate.document.model.persistencecontext;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "personDetail")
public class FilterPersonDetail {
	@Id
	@Type(type = "uuid-char")
	@GeneratedValue
	private UUID id;
	private Integer age;
	@OneToOne
	// @MapsId
	@PrimaryKeyJoinColumn
	private FilterPerson person;

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	private boolean is_deleted;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public FilterPerson getPerson() {
		return person;
	}

	public void setPerson(FilterPerson person) {
		this.person = person;
		// 如果使用@PrimaryKeyJoinColumn
		// 需要加上这一句
		this.id = person.getId();
	}

}
