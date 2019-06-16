package hibernate.document.model.collection.bidirectionalmap;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import hibernate.document.model.PhoneType;

@Entity
public class EntityPairMapPhone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private PhoneType type;

	private Date since;
	@ManyToOne
	private MapPairPerson person;

	public MapPairPerson getPerson() {
		return person;
	}

	public void setPerson(MapPairPerson person) {
		this.person = person;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Long getId() {
		return id;
	}

	@Column(name = "`number`")
	private String number;

	public PhoneType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		EntityPairMapPhone other = (EntityPairMapPhone) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
