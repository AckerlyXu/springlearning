package hibernate.document.model.oneToOne;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

@Entity
public class PersonDetailPair {
	@Id
	@Type(type = "uuid-char")
	private UUID id;
	private int age;
	@OneToOne
	@JoinColumn(name = "person_id")
	@MapsId
	// @PrimaryKeyJoinColumn
	private PersonPair person;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public PersonPair getPerson() {
		return person;
	}

	public void setPerson(PersonPair person) {
		this.person = person;
		// 如果使用@PrimaryKeyJoinColumn
		// 需要加上这一句
		this.id = person.getId();

	}

}
