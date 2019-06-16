package hibernate.document.model.inheritance;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("credit")
public class SingleTableCreditAccount extends SingleTableAccount {
	private BigDecimal creditLimit;

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

}
