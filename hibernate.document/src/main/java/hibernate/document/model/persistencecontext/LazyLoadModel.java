package hibernate.document.model.persistencecontext;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class LazyLoadModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Basic(fetch = FetchType.LAZY)
	private UUID accountsPayableXrefId;

	@Lob
	@Basic(fetch = FetchType.LAZY)
//	@LazyGroup("lobs")
	private byte[] image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getAccountsPayableXrefId() {
		return accountsPayableXrefId;
	}

	public void setAccountsPayableXrefId(UUID accountsPayableXrefId) {
		this.accountsPayableXrefId = accountsPayableXrefId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

}
