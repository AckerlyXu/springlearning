package hibernate.document.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(name = "`User`")
public class User {
	@EmbeddedId
	private PK pk;

	private String hobby;

	public PK getPk() {
		return pk;
	}

	public void setPk(PK pk) {
		this.pk = pk;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

}
