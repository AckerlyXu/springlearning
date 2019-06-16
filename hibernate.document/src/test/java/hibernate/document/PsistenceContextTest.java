package hibernate.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hibernate.document.model.Student;
import hibernate.document.model.manyToMany.Action;
import hibernate.document.model.manyToMany.Role;
import hibernate.document.model.oneToMany.OrderItemPair;
import hibernate.document.model.oneToMany.OrderPair;
import hibernate.document.model.persistencecontext.FilterBook;
import hibernate.document.model.persistencecontext.FilterPerson;
import hibernate.document.model.persistencecontext.LazyLoadModel;
import hibernate.document.model.persistencecontext.Reader;
import hibernate.document.util.HibernateUtil;

public class PsistenceContextTest {

	/*
	 * 测试联合主键
	 */
	// @Test
	public void test() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		LazyLoadModel model = new LazyLoadModel();
		model.setAccountsPayableXrefId(UUID.randomUUID());
		model.setImage(new byte[] { 1, 2, 3, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6 });
		model.setName("abc");
		session.persist(model);

		System.out.println("++++++++++++++++++++++++++++++++++++++" + model.getId());
		tran.commit();
		session.clear();
		tran = session.beginTransaction();
		LazyLoadModel model1 = session.find(LazyLoadModel.class, 1);
		UUID accountsPayableXrefId = model1.getAccountsPayableXrefId();

		model = new LazyLoadModel();
		model.setAccountsPayableXrefId(UUID.randomUUID());
		model.setImage(new byte[] { 1, 2, 3, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6 });
		model.setName("abc");
		session.persist(model);
		model.setName("def");
		System.out.println("++++++++++++++++++++++++++++++++++++++" + model.getId());
		tran.commit();

		session.close();
		HibernateUtil.shutdown();
	}

	// @Test
	public void testGetReference() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		OrderPair order = new OrderPair();
		order.setNumber("123");
		OrderItemPair item = new OrderItemPair("beaf", 11);
		item.setOrder(order);
		OrderItemPair item2 = new OrderItemPair("pork", 13);

		order.addOrderItem(item);
		// persist和save方法是不是真的发送sql，还取决于主键生成策略，这里sequence就不发送
		session.persist(item2);
		session.save(order);

		session.clear();
		System.out.println("测试开始");
		// 用byId方式的再加上load方法属于完全加载，不属于懒加载
		OrderItemPair item3 = session.byId(OrderItemPair.class).load(1);
		assertEquals(item3.getProduct(), "pork");
		// 获得关联对象的代理对象，这在只需要建立关联而不需要关联对象的实际数据时更加高效
		// 因为不会发送sql语句查询关联对象
		OrderPair proxy = session.getReference(OrderPair.class, 1);
		// 也可以通过load方法获得代理对象
		// session.load(OrderPair.class, 1);
		System.out.println(proxy.getClass());
		item3.setOrder(proxy);

		tran.commit();

		session.close();
		HibernateUtil.shutdown();
	}

	/*
	 * 测试multiId,jpa没有提供类似的方法
	 */
	// @Test
	public void testMutiId() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
//		OrderPair order = new OrderPair();
//		order.setNumber("123");
		OrderItemPair item = new OrderItemPair("beaf", 11);

		OrderItemPair item2 = new OrderItemPair("pork", 13);
		OrderItemPair item3 = new OrderItemPair("chicken", 19);
		// order.addOrderItem(item);
		// persist和save方法是不是真的发送sql，还取决于主键生成策略，这里sequence就不发送
		session.persist(item2);
		session.save(item);
		session.save(item3);
		session.clear();

		OrderItemPair item1 = session.get(OrderItemPair.class, 1);
		session.delete(item1);
		// 返回的数据会按照输入的顺序排列，如2,1,3返回item2,item1,item3
		List<OrderItemPair> items = session.byMultipleIds(OrderItemPair.class).enableOrderedReturn(true)
				.enableReturnOfDeletedEntities(false).enableSessionCheck(true).withBatchSize(1).multiLoad(2, 1, 5, 3);
		// assertSame(items.get(1), item1);

		// enableOrderedReturn(true)会到缓存中找，如果找到直接返回这个对象，并且在sql语句中不会包含该id
		// 好像对懒加载对象无效

		// assertArrayEquals(items.stream().mapToInt(i -> i.getId()).toArray(), new
		// int[] { 2, 1, 3 });

		// 排序不排序enableOrderedReturn会对null的处理产生影响，如果排序，null会插入对应的位置，如果不排序，null不会出现在最终的结果中
		assertEquals(4, items.size());
		assertNull(items.get(1));

		// enableReturnOfDeletedEntities会排除在当前上下文中已经被删除的对象
		tran.commit();

		session.close();
		HibernateUtil.shutdown();
	}

	/*
	 * 测试filter
	 */
	// @Test
	public void testFilterDef() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Reader reader = new Reader();
		reader.setName("ackerly");

		FilterBook book = new FilterBook();
		book.setIs_active(true);
		book.setName("spring mvc");
		reader.getBooks().add(book);

		book = new FilterBook();
		book.setIs_active(false);
		book.setName("spring core");
		reader.getBooks().add(book);
		session.persist(reader);

		tran.commit();

		session.close();

		session = sessionFactory.openSession();
		session.enableFilter("active_filter").setParameter("active", true);
		// filter适合于hql,不是通过find或get方法
		List<FilterBook> list = session.createQuery("from FilterBook", FilterBook.class).list();
		assertEquals(1, list.size());
		// 更改filter参数的值
		session.enableFilter("active_filter").setParameter("active", false);
		Reader reader1 = session.find(Reader.class, 1);
		assertEquals(1, reader1.getBooks().size());
		assertFalse(((FilterBook) reader1.getBooks().toArray()[0]).isIs_active());
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试SqlFragmentAlias
	 */
	// @Test
	public void testSqlFragmentAlias() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		FilterPerson person = new FilterPerson();
		person.setIs_deleted(true);
		person.setName("ackerly");
		session.persist(person);

		person = new FilterPerson();
		person.setIs_deleted(false);
		person.setName("ackerly");
		session.persist(person);

		person = new FilterPerson();
		person.setIs_deleted(false);
		person.setName("ack");
		session.persist(person);

		tran.commit();

		session.close();

		session = sessionFactory.openSession();
		session.enableFilter("deleted").setParameter("is_deleted", false);
		// filter适合于hql,不是通过find或get方法
		List<FilterPerson> list = session.createQuery("from FilterPerson", FilterPerson.class).list();
		assertEquals(1, list.size());

		session.close();
		HibernateUtil.shutdown();
	}

	/*
	 * 测试filterJoinTable
	 */
	// @Test
	public void testFilterJoinTable() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Role role = new Role();
		role.setName("this role");

		Action action = new Action();
		action.setName("this action");
		role.getActions().add(action);
		action = new Action();
		action.setName("two action");
		role.getActions().add(action);
		session.persist(role);
		tran.commit();
		session.close();

		session = sessionFactory.openSession();
		session.enableFilter("action").setParameter("action_id", 2);
		tran = session.beginTransaction();
		Role myRole = session.createQuery("from Role", Role.class).getSingleResult();
		assertTrue(myRole.getActions().size() == 1);
		// myRole.getActions().remove(myRole.getActions().toArray()[0]);
		session.flush();
		tran.commit();
		HibernateUtil.shutdown();
	}

	/*
	 * 测试一个对象的状态
	 */
//	@Test
	public void testEntityState() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Role role = new Role();
		role.setName("this role");

		Action action = new Action();
		// 还没有处于持久态
		assertFalse(session.contains(action));
		action.setName("this action");
		role.getActions().add(action);
		action = new Action();
		action.setName("two action");
		role.getActions().add(action);
		session.persist(role);
		// 已经处于持久态
		assertTrue(session.contains(action));
		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		tran = session.beginTransaction();
		Role myRole = session.createQuery("from Role", Role.class).getSingleResult();

		// myRole已经被加载了
		assertTrue(Hibernate.isInitialized(myRole));
		// actions还没有被加载
		assertFalse(Hibernate.isInitialized(myRole.getActions()));
		// name属性已经被加载了
		assertTrue(Hibernate.isPropertyInitialized(myRole, "name"));
		tran.commit();
		// 也可以使用PersistenceUtil判断状态
		PersistenceUtil persistenceUnitUtil = Persistence.getPersistenceUtil();
		assertNotNull(persistenceUnitUtil);
		assertFalse(persistenceUnitUtil.isLoaded(myRole.getActions()));

		// detach以后将不能加载关联对象，并且一旦试图加载会报错
		session.evict(myRole);
		assertFalse(session.contains(myRole));

		session.close();
		HibernateUtil.shutdown();
	}

	/*
	 * 测试OnDelete注解 当使用了onDelete注解，删除主表row的时候会自动删除引用它的从表记录而不需要额外的sql语句
	 */
	// @Test
	public void OneToMany() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		OrderPair order = new OrderPair();
		order.setNumber("123");
		OrderItemPair item = new OrderItemPair("beaf", 11);
		OrderItemPair item2 = new OrderItemPair("pork", 13);
		order.getOrderItems().add(item2);
		order.getOrderItems().add(item);
		session.save(order);
		session.flush();

		session.remove(order);
		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		HibernateUtil.shutdown();
	}

	/**
	 * 测试flush
	 */
	// @Test
	public void testFlush() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		OrderPair order = new OrderPair();
		order.setNumber("123");

		session.save(order);
		// session.flush();
		// session.clear();
		Student student = new Student();
		student.setAge(12);
		student.setName("ackerlyx");
		// 如果主键是identity，那么也会flush
		session.save(student);

		List<OrderPair> orders = session.createQuery("from OrderPair", OrderPair.class).list();
		// 在执行hql之前会把如果session中存在student缓存，会自动flush,否则不会
		List<Student> students = session.createQuery("from Student", Student.class).list();
		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		HibernateUtil.shutdown();
	}

}
