package hibernate.document.model.oneToOne;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

@Entity

public class PersonPair {
	@Id
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "custom-uuid")

	private UUID id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "person")

	private PersonDetailPair personDetail;

	public PersonDetailPair getPersonDetail() {
		return personDetail;
	}

	public void setPersonDetail(PersonDetailPair personDetail) {
		this.personDetail = personDetail;
		personDetail.setPerson(this);
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
