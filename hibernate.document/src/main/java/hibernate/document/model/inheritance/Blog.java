package hibernate.document.model.inheritance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@Entity(name = "Blog")
//由于设置了EXPLICIT，所以多态查询的时候会跳过它
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Blog implements DomainModelEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	private String site;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	// Getter and setters omitted for brevity
}