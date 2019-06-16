package hibernate.document.model.inheritance;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.DiscriminatorFormula;

@Entity(name = "Account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when creditLimit is  null then ( case when overdraftFee is null then null else 'debit' end) else"
		+ " 'credit' end")
@DiscriminatorValue("null")
public class SingleTableAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String owner;
	private BigDecimal balance;

	public String getOwner() {
		return owner;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
