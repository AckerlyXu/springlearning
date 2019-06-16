package hibernate.document.model.oneToMany;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderItemPair {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private OrderPair order;

	public OrderPair getOrder() {
		return order;
	}

	public void setOrder(OrderPair order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public OrderItemPair() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItemPair(String product, int count) {
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

	@Override
	public String toString() {
		return "OrderItemPair [id=" + id + ", product=" + product + ", count=" + count + "]";
	}

}
