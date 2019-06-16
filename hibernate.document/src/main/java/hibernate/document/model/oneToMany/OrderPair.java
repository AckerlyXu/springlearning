package hibernate.document.model.oneToMany;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "`Order`")
public class OrderPair {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String number;
	// 如果没有mappedBy,那么一对多会创建一张中间表
	// 但是所有的关联关系都会被子实体管理
	@OneToMany(mappedBy = "order", cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<OrderItemPair> orderItems = new HashSet<OrderItemPair>();

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "OrderPair [id=" + id + ", number=" + number + "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<OrderItemPair> getOrderItems() {
		return orderItems;
	}

	// 工具方法，方便添加关联关系
	public void addOrderItem(OrderItemPair item) {
		orderItems.add(item);
		item.setOrder(this);
	}

	public void removeOrderItem(OrderItemPair item) {
		orderItems.remove(item);
		item.setOrder(null);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
