package hibernate.document.model.inheritance;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("debit")
public class SingleTableDebit extends SingleTableAccount {
	private BigDecimal overdraftFee;

	public BigDecimal getOverdraftFee() {
		return overdraftFee;
	}

	public void setOverdraftFee(BigDecimal overdraftFee) {
		this.overdraftFee = overdraftFee;
	}

}
