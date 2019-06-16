package hibernate.document.model.inheritance;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class DebitAccount extends Account {
	private BigDecimal overdraftFee;

	public BigDecimal getOverdraftFee() {
		return overdraftFee;
	}

	public void setOverdraftFee(BigDecimal overdraftFee) {
		this.overdraftFee = overdraftFee;
	}

}
