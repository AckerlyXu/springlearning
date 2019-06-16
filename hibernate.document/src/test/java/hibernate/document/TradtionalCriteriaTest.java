package hibernate.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StringType;
import org.junit.Test;

import hibernate.document.model.Student;
import hibernate.document.model.oneToMany.OrderItemPair;
import hibernate.document.model.oneToMany.OrderPair;
import hibernate.document.util.HibernateUtil;

public class TradtionalCriteriaTest {
	// @Test
	public void test() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		for (int i = 0; i < 10; i++) {
			Student student = new Student();
			student.setName("student" + i);
			student.setAge(i);
			student.setDate(new Date(119, i + 1, i));
			session.save(student);
		}
		Student student = new Student();
		student.setName("myname");
		student.setAge(15);
		session.save(student);
		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Student.class);
		criteria.add(Restrictions.le("age", 7));
		criteria.add(Restrictions.ilike("name", "stu", MatchMode.ANYWHERE));

		// Restrictions.disjunction()是或者条件
		criteria.add(Restrictions.disjunction().add(Restrictions.eq("id", 1)).add(Restrictions.eq("id", 2))
				.add(Restrictions.eq("id", 3)));
		List<Student> resultList = criteria.list();

		for (Student objects : resultList) {
			System.out.println(objects.getName() + ":" + objects.getAge());
		}

		HibernateUtil.shutdown();
	}

	public static void prepareStudent(Session session) {

		for (int i = 0; i < 10; i++) {
			Student student = new Student();
			student.setName("student" + i);
			student.setAge(i);
			student.setDate(new Date(119, i + 1, i));
			session.save(student);
		}
		Student student = new Student();
		student.setName("myname");
		student.setAge(15);
		session.flush();
	}

	// sqlRestriction和property创建Criteria,order的使用
	// @Test
	public void sqlRedrictionTest() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		prepareStudent(session);
		tran.commit();
		session.close();

		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Student.class);
		// {alias}会被最终的别名代替
		criteria.add(Restrictions.sqlRestriction(" lower({alias}.name) like ?", "%stu%", StringType.INSTANCE));
		// 使用property创建Criterion
		Property prop = Property.forName("age");
		Criterion in = prop.in(2, 3, 4, 5, 6);
		criteria.add(in);

		// 添加order的两种方式
		criteria.addOrder(prop.asc());
		criteria.addOrder(Order.desc("name"));

		List<Student> resultList = criteria.list();

		for (Student objects : resultList) {
			System.out.println(objects.getName() + ":" + objects.getAge());
		}

		HibernateUtil.shutdown();
	}

	// 限制关联属性通过creatCriteria
	// @Test
	public void createCriteriaNav() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		HqlTest.prepareDataForOneToMany(session);

		tran.commit();
		session.clear();
		List<OrderPair> list5 = session.createCriteria(OrderPair.class).setFetchMode("orderItems", FetchMode.EAGER)
				.list();
		// 这里积极加载成功了
		assertTrue(Hibernate.isInitialized(list5.get(0).getOrderItems()));

		List<OrderPair> list = session.createCriteria(OrderPair.class).createCriteria("orderItems", JoinType.INNER_JOIN)
				.add(Restrictions.gt("count", 12)).list();

		// 这里测试会失败，因为只要有一个count>12，OrderPair就会被选出来
//		for (OrderPair orderPair : list) {
//			for (OrderItemPair orderItemPair : orderPair.getOrderItems()) {
//				assertTrue(orderItemPair.getCount() > 12);
//			}
//		}
		// 如果是eqProperty的话，那么就是实体的两个属性相比较 比如eqProperty(count,name),就是count属性和name属性比较
		List<OrderItemPair> list2 = session.createCriteria(OrderItemPair.class).createAlias("order", "o")
				.add(Restrictions.eq("o.number", "123").ignoreCase()).list();
		for (OrderItemPair object : list2) {
			assertEquals(object.getOrder().getNumber(), "123");
		}
		// .setFetchMode("orderItems", FetchMode.EAGER)这里没有用
		List<Map> listmap = session.createCriteria(OrderPair.class)
				// Criteria.LEFT_JOIN指定左连接，这样能把没有OrderItemPair的OrderPair选出来
				.createCriteria("orderItems", "oi", Criteria.LEFT_JOIN).add(Restrictions.gt("count", 12))
				// 设置积极加载orderItems
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		for (Map map : listmap) {
			OrderPair pair = (OrderPair) map.get(Criteria.ROOT_ALIAS);
			assertTrue(Hibernate.isInitialized(pair.getOrderItems()));
			// 这里获得的pair2是所有count大于12的
			OrderItemPair pair2 = (OrderItemPair) map.get("oi");
			assertTrue(pair2.getCount() > 12);
		}
		HibernateUtil.shutdown();
	}

	/**
	 * 使用example
	 */
	@Test
	public void example() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		HqlTest.prepareDataForOneToMany(session);
		OrderPair pair = new OrderPair();
		pair.setNumber("123");
		OrderItemPair pair2 = new OrderItemPair();
		pair2.setProduct("beaf");
		pair2.setCount(11); // 对orderPair使用Example
		List<OrderPair> list = session.createCriteria(OrderPair.class).add(Example.create(pair))
				// 对orderItems使用example
				.createCriteria("orderItems").add(Example.create(pair2).excludeNone().ignoreCase().enableLike()).list();
		assertEquals(list.size(), 1);
		tran.commit();
		session.clear();

		HibernateUtil.shutdown();
	}

}
