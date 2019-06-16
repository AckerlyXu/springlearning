package hibernate.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import hibernate.document.model.PK;
import hibernate.document.model.Phone;
import hibernate.document.model.Student;
import hibernate.document.model.SubSystem;
import hibernate.document.model.User;
import hibernate.document.model.UserNext;
import hibernate.document.model.JoinFormula.Car;
import hibernate.document.model.JoinFormula.CarCountry;
import hibernate.document.model.any.IntegerProperty;
import hibernate.document.model.any.PropertyHolder;
import hibernate.document.model.any.PropertyRepository;
import hibernate.document.model.any.StringProperty;
import hibernate.document.model.joinColumnOrFormula.CarCF;
import hibernate.document.model.joinColumnOrFormula.CarCountryCF;
import hibernate.document.model.manyToMany.Action;
import hibernate.document.model.manyToMany.ActionPair;
import hibernate.document.model.manyToMany.ActionThree;
import hibernate.document.model.manyToMany.Role;
import hibernate.document.model.manyToMany.RolePair;
import hibernate.document.model.manyToMany.RoleThree;
import hibernate.document.model.notfound.City;
import hibernate.document.model.notfound.CityPerson;
import hibernate.document.model.oneToMany.Order;
import hibernate.document.model.oneToMany.OrderItem;
import hibernate.document.model.oneToMany.OrderItemPair;
import hibernate.document.model.oneToMany.OrderPair;
import hibernate.document.model.oneToOne.Person;
import hibernate.document.model.oneToOne.PersonDetail;
import hibernate.document.model.oneToOne.PersonDetailPair;
import hibernate.document.model.oneToOne.PersonPair;
import hibernate.document.util.HibernateUtil;

public class MyTest {

	/*
	 * 测试联合主键
	 */
	// @Test
	public void test() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		User user = new User();
		user.setHobby("footable");

		session.saveOrUpdate(user);
		user = new User();
		user.setHobby("footable");
		user.setPk(new PK("ackerl", "123"));
		session.saveOrUpdate(user);
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/*
	 * 测试联合主键 ，使用idclass
	 */
	// @Test
	public void test2() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		UserNext user = new UserNext();

		user.setHobby("footable");
		SubSystem system = new SubSystem("1", "des1");
		user.setSubsystem(system);
		// 不知道为什么，如果不加IdClass，级联更新没有用，必须手动更新
		session.saveOrUpdate(system);
		user.setUsername("user1");
		session.saveOrUpdate(user);
		/*
		 * user = new UserNext();
		 * 
		 * user.setHobby("baskball"); user.setId(new PKNext(new SubSystem("2", "des3"),
		 * "user3")); session.saveOrUpdate(user);
		 */
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试自动生成的Id
	 */

	// @Test
	public void test3() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Student student = new Student();
		student.setName("ackerly");
		student.setAge(12);
		session.save(student);
		assertNotNull(student.getId());
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试Id引用
	 */
	// @Test
	public void test4() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Person person = new Person();
		// person.setId(UUID.randomUUID());

		person.setName("徐强");
		PersonDetail detail = new PersonDetail();
		session.save(person);
		detail.setPerson(person);

		detail.setAge(12);

		session.save(detail);
		assertNotNull(detail.getId());

		tran.commit();

		session.close();

		HibernateUtil.shutdown();
	}

	/**
	 * 测试双向oneToOne
	 */
	// @Test
	public void oneToOne() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		PersonPair person = new PersonPair();
		// person.setId(UUID.randomUUID());

		person.setName("徐强");
		PersonDetailPair detail = new PersonDetailPair();
		person.setPersonDetail(detail);
		detail.setAge(12);
		session.save(person);

		assertNotNull(detail.getId());

		tran.commit();

		session.close();
		session = sessionFactory.openSession();
		PersonPair query = session.createQuery("from PersonPair", PersonPair.class).getSingleResult();
		PersonDetailPair personDetail = session.createQuery("from PersonDetailPair", PersonDetailPair.class)
				.getSingleResult();
		assertNotNull(query.getPersonDetail());
		assertNotNull(personDetail.getPerson());
		HibernateUtil.shutdown();
	}

	/**
	 * 测试ManyToOne
	 */

//	@Test
	public void manyToOne() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Person person = new Person();
		person.setName("ackerlyx");

		// 如果cascade不设置，那么需要先更新person,再更新phone
		// session.save(person);

		Phone phone = new Phone();
		phone.setPhoneNumber("123");

		session.save(phone);
		session.flush();
		phone.setPerson(null);

		// session.remove(phone);
		phone.setPerson(person);
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试单方面OneToMany
	 */
	// @Test
	public void OneToMany() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Order order = new Order();
		order.setNumber("123");
		OrderItem item = new OrderItem("beaf", 11);
		OrderItem item2 = new OrderItem("pork", 13);
		order.getOrderItems().add(item2);
		order.getOrderItems().add(item);
		session.save(order);
		session.flush();

		// 如果不设置orphanRemoval=true，那么只会删除关联关系，而不会删除关联的实体
		order.getOrderItems().remove(item);
		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		// 查询id为1的order,并且找到它关联的orderItem
		order = session.find(Order.class, 1);
		assertTrue(order.getOrderItems().size() > 0);
		HibernateUtil.shutdown();
	}

	/**
	 * 测试双向OneToMany
	 */
	// @Test
	public void bidirectionalOneToMany() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		OrderPair order = new OrderPair();
		order.setNumber("123");
		OrderItemPair item = new OrderItemPair("beaf", 11);
		item.setOrder(order);
		OrderItemPair item2 = new OrderItemPair("pork", 13);
		// 如果设置了mappedby,那么所有的关联关系都有子实体管理
		// 所以即使调用了父实体的add方法，也无法更新关联关系
		// 那么下次查询的时候关联的属性就查不出来了
		// order.getOrderItems().add(item2);
		// order.getOrderItems().add(item);

		// session.save(item);
		// session.save(item2);
		// item2.setOrder(order);

		order.addOrderItem(item);
		order.addOrderItem(item2);
		session.save(order);
		session.flush();

		// 如果不设置orphanRemoval=true，那么只会删除关联关系，而不会删除关联的实体
		order.getOrderItems().remove(item);
		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		// 查询id为1的order,并且找到它关联的orderItem
		order = session.find(OrderPair.class, 1);
		assertTrue(order.getOrderItems().size() > 0);
		item2 = session.find(OrderItemPair.class, 3);
		assertNotNull(item2.getOrder());
		HibernateUtil.shutdown();
	}

	/**
	 * 测试用懒加载建立一对多关联
	 */
	@Test
	public void oneManyBuildRelationShip() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		OrderPair order = new OrderPair();
		order.setNumber("123");
		OrderItemPair item = new OrderItemPair("beaf", 11);

		session.save(item);
		session.save(order);
		session.flush();

		session.close();

		session = sessionFactory.openSession();
		tran = session.beginTransaction();

		OrderPair lazyOrder = session.load(OrderPair.class, 1);
		OrderItemPair orderItemPair = session.get(OrderItemPair.class, 1);

		orderItemPair.setOrder(lazyOrder);
		OrderItemPair item2 = new OrderItemPair("pork", 13);
		item2.setOrder(lazyOrder);
		session.persist(item2);
		tran.commit();
		HibernateUtil.shutdown();
	}

	/*
	 * 测试多对多
	 */
	// @Test
	public void testManyToMany() {
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
		tran = session.beginTransaction();
		Role myRole = session.createQuery("from Role", Role.class).getSingleResult();
		assertTrue(myRole.getActions().size() == 2);
		myRole.getActions().remove(myRole.getActions().toArray()[0]);
		session.flush();
		tran.commit();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试双向多对多
	 */
//	@Test
	public void testManyToManyPair() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		RolePair role = new RolePair();
		role.setName("this role");

		ActionPair action = new ActionPair();
		role.addAction(action);
		action.setName("this action");

		action = new ActionPair();
		action.setName("two action");
		role.addAction(action);

		session.persist(role);

		tran.commit();
		session.close();

		session = sessionFactory.openSession();
		tran = session.beginTransaction();
		RolePair myRole = session.createQuery("from RolePair", RolePair.class).getSingleResult();
		assertTrue(myRole.getActions().size() == 2);
		List<ActionPair> list = session.createQuery("from ActionPair", ActionPair.class).list();
		assertTrue(list.get(0).getRoles().size() == 1);
		myRole.getActions().remove(myRole.getActions().toArray()[0]);
		session.flush();
		tran.commit();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试双向多对多加中间实体类
	 */
	// @Test
	public void testManyToManyWithLinkedEntity() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		RoleThree role = new RoleThree();
		role.setName("this role");

		ActionThree action = new ActionThree();
		session.persist(action);
		role.addAction(action);
		action.setName("this action");

		action = new ActionThree();
		session.persist(action);
		action.setName("two action");
		role.addAction(action);

		session.persist(role);

		tran.commit();
		session.close();

		session = sessionFactory.openSession();
		tran = session.beginTransaction();
		RoleThree myRole = session.createQuery("from RoleThree", RoleThree.class).getSingleResult();
		assertTrue(myRole.getRowActions().size() == 2);
		List<ActionThree> list = session.createQuery("from ActionThree", ActionThree.class).list();
		assertTrue(list.get(0).getRowActions().size() == 1);
		myRole.removeAction(list.get(0));
		session.flush();
		tran.commit();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试@NotFound
	 */
	// @Test
	public void testNotFound() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		City city = new City();
		city.setName("wu xi");
		session.persist(city);

		CityPerson cPerson = new CityPerson();
		cPerson.setCityName("wu xi");
		// cPerson.setCity(city);
		cPerson.setName("ackerly");
		session.persist(cPerson);
		tran.commit();
		session = sessionFactory.openSession();

		tran = session.beginTransaction();
		CityPerson person = session.get(CityPerson.class, 1);
		assertNotNull(person.getCity());
		person.setCityName("hello");
		tran.commit();
		session.refresh(person);
		;
		// person = session.get(CityPerson.class, 1);
		assertNull(person.getCity());
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试any
	 */
	// @Test
	public void testAny() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		StringProperty property = new StringProperty();
		property.setName("I'm string");
		property.setValue("string value");
		session.persist(property);
		IntegerProperty integerProperty = new IntegerProperty();
		integerProperty.setName("I'm integer");
		integerProperty.setValue(123);
		session.persist(integerProperty);
		PropertyHolder holder = new PropertyHolder();
		holder.setName("string holder");
		holder.setProperty(property);

		session.persist(holder);
		holder = new PropertyHolder();
		session.persist(holder);
		holder.setName("int holder");
		holder.setProperty(integerProperty);
		tran.commit();
		session = sessionFactory.openSession();

		tran = session.beginTransaction();
		PropertyHolder proper = session.find(PropertyHolder.class, 4);
		assertNotNull(proper.getProperty());
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试manyToAny
	 */
//	@Test
	public void testManyToAny() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		StringProperty property = new StringProperty();
		property.setName("I'm string");
		property.setValue("string value");
		session.persist(property);
		IntegerProperty integerProperty = new IntegerProperty();
		integerProperty.setName("I'm integer");
		integerProperty.setValue(123);
		session.persist(integerProperty);
		PropertyRepository repository = new PropertyRepository();
		repository.setRepoName("repository");
		repository.getProperties().add(property);
		repository.getProperties().add(integerProperty);
		session.persist(repository);

		tran.commit();
		session = sessionFactory.openSession();

		tran = session.beginTransaction();
		PropertyRepository proper = session.find(PropertyRepository.class, 3);
		assertEquals(2, proper.getProperties().size());
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试JoinFormula
	 */
	// @Test
	public void testJoinFormula() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		CarCountry carCountry = new CarCountry();
		carCountry.setName("china");
		session.save(carCountry);
		Car car = new Car();
		car.setName("carchina");
		session.save(car);
		session.flush();
		session.refresh(car);
		assertNotNull(car.getCarCountry());
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试JoinColumnOrFormula
	 */
	// @Test
	public void testJoinColumnOrFormula() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		CarCountryCF carCountry = new CarCountryCF();
		carCountry.setName("china");
		carCountry.setLanguage("chinese");
		carCountry.setIs_default(true);
		session.save(carCountry);
		CarCF car = new CarCF();
		car.setLanguage("chinese");
		car.setName("carchina");
		session.save(car);
		session.flush();
		session.refresh(car);
		assertNotNull(car.getCf());
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * 测试 ColumnDefault 和 DynamicInsert 以及@Formula Formula只会在select的时候帮你计算，并不会真正生成列
	 */
	// @Test
	public void testColumnDefault() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Student student = new Student();
		session.save(student);
		session.flush();
		System.out.println(student);
		session.refresh(student);

		System.out.println(student);
		tran.commit();

		session.close();
		HibernateUtil.shutdown();
	}

}
