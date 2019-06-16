package hibernate.document.model.collection.valuemap;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import hibernate.document.model.PhoneType;

@Embeddable
public class ValueMapPhone {

	private PhoneType type;
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
		ValueMapPhone other = (ValueMapPhone) obj;
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
