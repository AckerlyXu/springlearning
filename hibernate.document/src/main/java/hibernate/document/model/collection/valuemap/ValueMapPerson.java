package hibernate.document.model.collection.valuemap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ValueMapPerson {
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
	private Map<ValueMapPhone, Date> phoneRegister = new HashMap<ValueMapPhone, Date>();

	public Map<ValueMapPhone, Date> getPhoneRegister() {
		return phoneRegister;
	}

	public void setPhoneRegister(Map<ValueMapPhone, Date> phoneRegister) {
		this.phoneRegister = phoneRegister;
	}

}
