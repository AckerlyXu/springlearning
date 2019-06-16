package hibernate.document.model.collection.bidirectionalmap;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;

import hibernate.document.model.PhoneType;

@Entity
public class MapPairPerson {
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

	// 存储key的时候按照枚举存储
	@MapKeyEnumerated
	@OneToMany(mappedBy = "person", cascade = { javax.persistence.CascadeType.ALL }, orphanRemoval = true)

	// PhoneType是EntityMapPohone的type属性
	@MapKey(name = "type")

	private Map<PhoneType, EntityPairMapPhone> phoneRegister = new HashMap<PhoneType, EntityPairMapPhone>();

	public Map<PhoneType, EntityPairMapPhone> getPhoneRegister() {
		return phoneRegister;
	}

	public void setPhoneRegister(Map<PhoneType, EntityPairMapPhone> phoneRegister) {
		this.phoneRegister = phoneRegister;
	}

}
