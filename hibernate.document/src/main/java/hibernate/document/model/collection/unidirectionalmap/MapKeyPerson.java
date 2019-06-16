package hibernate.document.model.collection.unidirectionalmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.MapKeyTemporal;
import javax.persistence.OneToMany;
import javax.persistence.TemporalType;

@Entity
public class MapKeyPerson {
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

	// 存储key的时候按照timestamp存储
	@MapKeyTemporal(TemporalType.TIMESTAMP)
	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, orphanRemoval = true)
	@JoinTable(name = "phone_register", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "phone_id"))
	// date是EntityMapPohone的since属性
	@MapKey(name = "since")

	private Map<Date, EntityMapPhone> phoneRegister = new HashMap<Date, EntityMapPhone>();

	public Map<Date, EntityMapPhone> getPhoneRegister() {
		return phoneRegister;
	}

	public void setPhoneRegister(Map<Date, EntityMapPhone> phoneRegister) {
		this.phoneRegister = phoneRegister;
	}

}
