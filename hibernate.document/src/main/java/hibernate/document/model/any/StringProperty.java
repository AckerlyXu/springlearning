package hibernate.document.model.any;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StringProperty implements Property<String> {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "`name`")
	private String name;
	@Column(name = "`value`")
	private String value;

	public Integer getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
