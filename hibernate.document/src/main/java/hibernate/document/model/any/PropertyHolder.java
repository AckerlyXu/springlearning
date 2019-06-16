package hibernate.document.model.any;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Any;

@org.hibernate.annotations.AnyMetaDef(name = "PropertyMetaDef", metaType = "string", idType = "integer", metaValues = {
		@org.hibernate.annotations.MetaValue(value = "str", targetEntity = hibernate.document.model.any.StringProperty.class),
		@org.hibernate.annotations.MetaValue(value = "integer", targetEntity = hibernate.document.model.any.IntegerProperty.class) })
@Entity
public class PropertyHolder {
	@Id
	@GeneratedValue
	private Integer id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Integer getId() {
		return id;
	}

	private String name;
	@Any(metaColumn = @Column(name = "property_type"), metaDef = "PropertyMetaDef")
	@JoinColumn(name = "property_id")
	private Property property;
}
