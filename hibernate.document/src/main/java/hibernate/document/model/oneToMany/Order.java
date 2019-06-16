package hibernate.document.model.oneToMany;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "`Order`")
public class Order {
	@Id
	@GeneratedValue
	private Integer id;

	private String number;
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)

	private Set<OrderItem> orderItems = new HashSet<OrderItem>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
