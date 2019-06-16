package hibernate.document.model.any;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ManyToAny;

@org.hibernate.annotations.AnyMetaDef(name = "PropertyMetaDefRepo", metaType = "string", idType = "integer", metaValues = {
		@org.hibernate.annotations.MetaValue(value = "str", targetEntity = hibernate.document.model.any.StringProperty.class),
		@org.hibernate.annotations.MetaValue(value = "integer", targetEntity = hibernate.document.model.any.IntegerProperty.class) })
@Entity
public class PropertyRepository {
	@Id
	@GeneratedValue
	private Integer id;

	private String repoName;

	@ManyToAny(metaDef = "PropertyMetaDefRepo", metaColumn = @Column(name = "property_type"))
	@Cascade(CascadeType.ALL)
	@JoinTable(name = "repository_properties", joinColumns = @JoinColumn(name = "repo_id"), inverseJoinColumns = @JoinColumn(name = "property_id"))

	private Set<Property<?>> properties = new HashSet<>();

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public Set<Property<?>> getProperties() {
		return properties;
	}

	public void setProperties(Set<Property<?>> properties) {
		this.properties = properties;
	}

	public Integer getId() {
		return id;
	}

}
