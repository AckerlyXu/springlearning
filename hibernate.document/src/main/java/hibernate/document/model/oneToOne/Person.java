package hibernate.document.model.oneToOne;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity

public class Person {
	@Id
	@Type(type = "uuid-char")
	@GeneratedValue(generator = "custom-uuid")
//	@GenericGenerator(name = "custom-uuid", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
//			@org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	private UUID id;

	private String name;

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
