package hibernate.document.model.oneToOne;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Type;

@Entity
public class PersonDetail {
	@Id
	@Type(type = "uuid-char")
	private UUID id;
	@Column(nullable = true)
	private Integer age;
	@OneToOne
	// @MapsId
	@PrimaryKeyJoinColumn
	private Person person;

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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		// 如果使用@PrimaryKeyJoinColumn
		// 需要加上这一句
		this.id = person.getId();
	}

}
