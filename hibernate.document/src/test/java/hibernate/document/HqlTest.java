package hibernate.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Persistence;
import javax.persistence.TemporalType;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;
import org.junit.Test;

import hibernate.document.model.Student;
import hibernate.document.model.oneToMany.OrderItemPair;
import hibernate.document.model.oneToMany.OrderPair;
import hibernate.document.util.HibernateUtil;

public class HqlTest {
	/**
	 * 基本的hql
	 */
//	@Test
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
		// session.flush();

		Query<Student> query = session
				.createQuery("select s from Student s where s.name like :name  " + "and s.date > :date", Student.class);

		List<Student> students = query.setParameter("name", "%student%")
				// date类型的要指定日期的类型
				.setParameter("date", new Date(119, 5, 1), TemporalType.DATE).getResultList();
		for (Student student2 : students) {
			assertTrue(student2.getName().contains("student"));
		}
		assertEquals(students.size(), 6);

		// 使用named query
		// 以及使用scroll
		try (ScrollableResults scroll = session.createNamedQuery("showStudent", Student.class).setParameter("age", 5)
				.scroll()) {
			while (scroll.next()) {
				Student student2 = (Student) scroll.get()[0];
				assertTrue(student2.getAge() > 5);
			}
		}

		// 使用stream
		try (Stream<Student> stream = session.createNamedQuery("showStudent", Student.class).setParameter("age", 6)
				.stream()) {
			int average = stream.collect(Collectors.averagingInt(Student::getAge)).intValue();
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++" + average);
		}
		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		HibernateUtil.shutdown();

	}

	/**
	 * hql连接查询
	 */
	public static void prepareDataForOneToMany(Session session) {
		OrderPair order = new OrderPair();
		order.setNumber("123");
		OrderItemPair item = new OrderItemPair("beaf", 11);
		item.setOrder(order);
		OrderItemPair item2 = new OrderItemPair("pork", 13);

		order.addOrderItem(item);
		order.addOrderItem(item2);
		session.save(order);

		order = new OrderPair();
		order.setNumber("123");
		item = new OrderItemPair("apple", 9);

		item2 = new OrderItemPair("juice", 11);
		order.addOrderItem(item);
		order.addOrderItem(item2);
		session.save(order);

		item = new OrderItemPair("pk", 9);
		order = new OrderPair();
		order.setNumber("345");

		session.save(order);
		session.save(item);

		order = new OrderPair();
		order.setNumber("456");
		item = new OrderItemPair("commputer", 11);

		item2 = new OrderItemPair("dress", 13);
		order.addOrderItem(item);
		order.addOrderItem(item2);
		session.save(order);

	}

	// @Test
	public void hqlOneToMany() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<Object[]> list = session.createQuery(
				" select o,oi from OrderPair o, OrderItemPair oi " + "where oi.order = o and oi.order is not null",
				Object[].class).list();
		assertEquals(list.size(), 4);
		HibernateUtil.shutdown();
	}

	/*
	 * 测试inner join
	 */
	// @Test
	public void hqlOneToMany2() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<Object[]> list = session
				.createQuery(" select o,oi from OrderPair o  inner join o.orderItems oi  " + "where oi.count>:count  ",
						Object[].class)
				.setParameter("count", 10).list();
		for (Object[] objects : list) {
			OrderPair pair = (OrderPair) objects[0];
			for (OrderItemPair itemPair : pair.getOrderItems()) {
				// 这个是不对的，不是order的itemPair>10,而是 objects[1]对应的itemPair>10
				assertTrue(itemPair.getCount() > 10);
			}
		}
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试left outer join
	 */
	// @Test
	public void hqlOneToMany3() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();
		// 注意fetch join不要跟分页方法连用.setFirstResult(2).setMaxResults(2)
		// 这将导致分页在内存中实现而不是在数据库层面实现
		List<OrderPair> list = session
				.createQuery(" select o from OrderPair o  left outer join  fetch o.orderItems oi  ", OrderPair.class)
				.setFirstResult(2).setMaxResults(2).list();
		// 这里Left out join会返回实际sql执行以后的总行数，有几行list的size就是几
		// 所以这里OrderPair的数量超过了OrderPair表的实际行数
		// assertTrue(list.size() == 7);
		// 加了fetch之后将会积极加载关联属性，不加fetch这里会是false
		assertTrue(Persistence.getPersistenceUtil().isLoaded(list.get(0).getOrderItems()));
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试with关键字 jpa中是on , 相当于sql当中的on
	 */
//	@Test
	public void hqlOneToMany4() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<OrderPair> list = session.createQuery(
				" select distinct o from OrderPair o  left outer  join o.orderItems oi with o.number !=:number  ",
				OrderPair.class).setParameter("number", "456").getResultList();

		assertFalse(Persistence.getPersistenceUtil().isLoaded(list.get(0).getOrderItems()));
		// 注意不是三，没有orderItem的order也被选了进来，如果是where就不会这样
		assertTrue(list.size() == 4);
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试path expressoin
	 */

	// @Test
	public void hqlOneToMany5() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<OrderItemPair> list = session
				// 这里通过 o.order.number访问了关联属性，会进行连接查询
				.createQuery(" select o from OrderItemPair o where o.order.number !=:number  ", OrderItemPair.class)
				.setParameter("number", "456").list();
		assertFalse(Persistence.getPersistenceUtil().isLoaded(list.get(0).getOrder()));
		assertEquals(4, list.size());
		session.close();
		HibernateUtil.shutdown();
	}

	//// 指定不要把distince发送到数据库，但是仍然返回不重复的OrderPair
	// .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
	// 但是我测了没有效果
	// 测试distinct
	// @Test
	public void hqlOneToMany6() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<OrderPair> list = session

				.createQuery(" select distinct o from OrderPair  o  join fetch  o.orderItems oi   ", OrderPair.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();

		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 在pathExpression中访问集合其实是访问里面的每个元素
	 */

	// @Test
	public void hqlOneToMany7() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<OrderPair> list = session

//				.createQuery(" select o from OrderPair  o  left  join fetch  o.orderItems oi where  oi.count>10  ",
//						OrderPair.class)
				// 也可以用这种语法
				.createQuery(" select o from OrderPair  o , in ( o.orderItems)  oi  where  oi.count>10  ",
						OrderPair.class)
				.getResultList();

		assertEquals(list.size(), 5);
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 在hql中使用函数或进行运算
	 */
	// @Test
	public void arithmetic() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		Student student = new Student();
		student.setAge(12);
		student.setDate(new Date());
		student.setName("徐强'则田zzz");
		session.save(student);
		session.flush();
		// 使用year函数获得年份
		Integer year = session.createQuery("select max(year( s.date)) from Student s", Integer.class).getSingleResult();
		assertEquals((int) year, new Date().getYear() + 1900);
		// 连接字符串的符号|| ,或者直接用concat函数
		// 也可以用cast转换类型 ,str函数是转换成字符串
		// 使用substring
		// upper lower trim length
		// locate相当于 indexof,它还可以添加第三个参数,表示要搜索的位置
		// abs , mod ,sqrt
		// CURRENT_DATE表示数据库当前时间
		// CURRENT_TIME当前时间 时分秒
		String name = session.createQuery(
				"select str(123456)  || str(CURRENT_TIMESTAMP,2,6 ) ||'  '|| cast(CURRENT_TIME as string) || cast(CURRENT_DATE as string) || locate('徐',s.name) ||upper(substring( s.name,1,6 ))||  cast(s.age as string) || cast(s.date as string) from Student s  ",
				String.class).getSingleResult();
		// assertEquals(name, "km12");
		System.err.println(name);
		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试maxElement
	 */
	// @Test
	public void hqlOneToMany8() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<OrderItemPair> list = session
		// 使用子查询
//				.createQuery(
//						" select oi from OrderItemPair  oi join  oi.order o  where  oi.count = (select max(oii.count) from OrderItemPair oii where oii.order=o)  ",
//						OrderItemPair.class)
				// 也可以用这种语法
				// maxelement选出id 最大的orderItems还有 minelement
				.createQuery(" select maxelement(o.orderItems) from OrderPair o  ", OrderItemPair.class)
				.getResultList();
		list.stream().forEach(System.out::println);
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试member of
	 */
	// @Test
	public void hqlOneToMany9() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<OrderPair> list = session
				// 这里member of的意思是 :item在当前OrderPair的orderItems之中 some
				// elements(o.orderItems)也是这个意思
				// .createQuery(" select o from OrderPair o where :item member of o.orderItems
				// ", OrderPair.class)
				.createQuery(" select  o from OrderPair o where  :item=some elements(o.orderItems) ", OrderPair.class)
				.setParameter("item", session.load(OrderItemPair.class, 1)).getResultList();
		list.stream().forEach(System.out::println);
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试 exists 和 all elements 还有size
	 */
	// @Test
	public void hqlOneToMany10() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<OrderPair> list = session
				.createQuery(" select  o from OrderPair o where exists  elements(o.orderItems) ", OrderPair.class)
//				.createQuery(
				// .createQuery(" select o from OrderPair o where size(o.orderItems) =2 ",
				// OrderPair.class)
//				.createQuery(
//						" select  o from OrderPair o  join o.orderItems oi where  10 >= all elements(o.orderItems) ",
//						OrderPair.class)
				.getResultList();
		list.stream().forEach(System.out::println);
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试case ,coalesce 和nullif
	 */
	// @Test
	public void testCase() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		Student student = new Student();
		student.setAge(12);
		student.setDate(new Date());
		student.setName("flag");
		session.save(student);
		session.flush();

		String name = session.createQuery(
				// nullif如果两个参数相同返回null
				" select (case when nullif(s.name,s.name) is null then lower('a') else s.name end ) || ' ' || COALESCE(s.name2,'我是第一个不是null ' ) from Student s ",
				String.class).getSingleResult();
		System.out.println(name);

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试构造函数投影
	 */
	// @Test
	public void testProjection() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		Student student = new Student();
		student.setAge(12);
		student.setDate(new Date());
		student.setName("flag");
		session.save(student);
		session.flush();

		OrderItemPair item = session.createQuery(
				// nullif如果两个参数相同返回null
				" select new OrderItemPair( (case when nullif(s.name,s.name) is null then lower('a') else s.name end ) || ' ' || COALESCE(s.name2,'我是第一个不是null ' ),12) from Student s ",
				OrderItemPair.class).getSingleResult();
		System.out.println(item);

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试all
	 */
	// @Test
	public void hqlOneToManyAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<OrderItemPair> list = session.createQuery(
				" select  oi from OrderPair o  join o.orderItems oi where oi.count>= all(select oii.count from OrderItemPair oii where oii.order=o) ",
				OrderItemPair.class)

				.getResultList();
		list.stream().forEach(System.out::println);
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试in和empty
	 */
	// @Test
	public void testIn() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();
		// :items参数可以是一个集合,不过不兼容jpa
		List<OrderItemPair> list = session
				.createQuery(" select oi from OrderItemPair oi where oi in :items  ", OrderItemPair.class)
				.setParameter("items",
						Arrays.asList(session.load(OrderItemPair.class, 1), session.load(OrderItemPair.class, 2)))

				.getResultList();

		List<OrderItemPair> list1 = session.createQuery(
				" select oi from OrderItemPair oi where oi in  (select  elements(o.orderItems) from OrderPair o where o.id=1)  ",
				OrderItemPair.class)

				.getResultList();
		list1.stream().forEach(System.out::println);
		List<OrderPair> list2 = session
				.createQuery(" select o from OrderPair o where o.orderItems is not empty  ", OrderPair.class)

				.getResultList();
		list2.stream().forEach(System.out::println);
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * group by
	 */

	@Test
	public void testGroupby() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		prepareDataForOneToMany(session);

		session.flush();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		List<Object[]> list1 = session
				.createQuery(" select sum(oi.count) as c ,o.number from OrderPair o join o.orderItems oi"
						+ " group by o.number order by c  ", Object[].class)

				.getResultList();
		for (Object[] objects : list1) {
			System.out.println(objects[1] + ":" + objects[0]);
		}
		session.close();
		HibernateUtil.shutdown();
	}
}
