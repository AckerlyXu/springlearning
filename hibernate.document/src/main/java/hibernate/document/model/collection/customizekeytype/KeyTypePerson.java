package hibernate.document.model.collection.customizekeytype;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;

@Entity
public class KeyTypePerson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	// 存储的时候按照timestamp存储
	@Temporal(TemporalType.TIMESTAMP)
	@ElementCollection
	@CollectionTable(name = "phone_register")
	// 指定value这里是Date的列名
	@Column(name = "since")
	@MapKeyColumn(name = "mapkey")
	// 指定key的type
	@MapKeyType(@Type(type = "uuid-char"))
	private Map<UUID, Date> phoneRegister = new HashMap<UUID, Date>();

	public Map<UUID, Date> getPhoneRegister() {
		return phoneRegister;
	}

	public void setPhoneRegister(Map<UUID, Date> phoneRegister) {
		this.phoneRegister = phoneRegister;
	}
}
