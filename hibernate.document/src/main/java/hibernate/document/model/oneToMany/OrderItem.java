package hibernate.document.model.oneToMany;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderItem {
	@Id
	@GeneratedValue
	private Integer id;

	public Integer getId() {
		return id;
	}

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItem(String product, int count) {
		super();

		this.product = product;
		this.count = count;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private String product;
	private int count;
}
