package hibernate.document.model.inheritance;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class CreditAccount extends Account {
	private BigDecimal creditLimit;

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

}
