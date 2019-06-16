package hibernate.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import hibernate.document.model.PhoneType;
import hibernate.document.model.collection.array.ArrayBinaryPerson;
import hibernate.document.model.collection.bidirectionalmap.EntityPairMapPhone;
import hibernate.document.model.collection.bidirectionalmap.MapPairPerson;
import hibernate.document.model.collection.customizekeytype.KeyTypePerson;
import hibernate.document.model.collection.unidirectionalmap.EntityMapPhone;
import hibernate.document.model.collection.unidirectionalmap.MapKeyPerson;
import hibernate.document.model.collection.valueCollection.ValueCollectionPerson;
import hibernate.document.model.collection.valuemap.ValueMapPerson;
import hibernate.document.model.collection.valuemap.ValueMapPhone;
import hibernate.document.util.HibernateUtil;

public class CollectionTest {

	// @Test
	public void test() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		ValueCollectionPerson person = new ValueCollectionPerson();
		person.setName("person");
		person.getPhones().add("12345");
		person.getPhones().add("456789");
		person.getPhones().add("456789");
		person.getPhones().add("4567810");
		session.save(person);
		tran.commit();
		session.close();

		session = factory.openSession();
		tran = session.beginTransaction();
		ValueCollectionPerson per = session.find(ValueCollectionPerson.class, 1);
		assertTrue(per.getPhones().size() == 4);
		// 如果没有加OrderColumn，这个操作会删除所有，在把其它的插入
		per.getPhones().remove(1);
		tran.commit();
		session.close();
		HibernateUtil.shutdown();

	}

	/**
	 * 测试映射valueMap
	 */
	// @Test
	public void testValueMap() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		ValueMapPerson person = new ValueMapPerson();
		person.setName("person1");
		ValueMapPhone phone = new ValueMapPhone();
		phone.setNumber("123");
		phone.setType(PhoneType.LAND_LINE);
		person.getPhoneRegister().put(phone, new Date());

		phone = new ValueMapPhone();
		phone.setNumber("456");
		phone.setType(PhoneType.MOBILE);
		person.getPhoneRegister().put(phone, new Date(2019, 10, 11));
		session.persist(person);

		tran.commit();
		session.close();

		session = factory.openSession();
		tran = session.beginTransaction();
		ValueMapPerson per = session.find(ValueMapPerson.class, 1l);
		assertTrue(per.getPhoneRegister().size() == 2);

		per.getPhoneRegister().remove(phone);
		tran.commit();
		session.close();
		HibernateUtil.shutdown();

	}

	/**
	 * 测试映射自定义mapkey的type
	 */
	// @Test
	public void testMapType() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		KeyTypePerson person = new KeyTypePerson();
		person.setName("person1");
		UUID iUuid = UUID.randomUUID();
		person.getPhoneRegister().put(iUuid, new Date());

		person.getPhoneRegister().put(UUID.randomUUID(), new Date(2019, 10, 11));
		session.persist(person);

		tran.commit();
		session.close();

		session = factory.openSession();
		tran = session.beginTransaction();
		KeyTypePerson per = session.find(KeyTypePerson.class, 1l);
		assertTrue(per.getPhoneRegister().size() == 2);
		// 如果没有加OrderColumn，这个操作会删除所有，在把其它的插入
		per.getPhoneRegister().remove(iUuid);
		tran.commit();
		session.close();
		HibernateUtil.shutdown();

	}

	/**
	 * 测试map的单向一对多
	 */
	// @Test
	public void testUnidirectionMap() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		MapKeyPerson person = new MapKeyPerson();
		person.setName("person1");
		EntityMapPhone phone = new EntityMapPhone();
		phone.setNumber("1234");
		phone.setSince(new Date());
		phone.setType(PhoneType.LAND_LINE);
		Date date = new Date();
		person.getPhoneRegister().put(phone.getSince(), phone);
		phone = new EntityMapPhone();
		phone.setNumber("5678");
		phone.setType(PhoneType.MOBILE);
		phone.setSince(new Date(2019, 10, 11));
		person.getPhoneRegister().put(phone.getSince(), phone);
		session.persist(person);

		tran.commit();
		session.close();

		session = factory.openSession();
		tran = session.beginTransaction();
		MapKeyPerson per = session.find(MapKeyPerson.class, 1l);
		assertTrue(per.getPhoneRegister().size() == 2);

		per.getPhoneRegister().remove(new Date(per.getPhoneRegister().values().iterator().next().getSince().getTime()));

		tran.commit();
		session.close();
		HibernateUtil.shutdown();

	}

	/**
	 * 测试map的双向一对多
	 */
	// @Test
	public void testBidirectionMap() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		MapPairPerson person = new MapPairPerson();
		person.setName("person1");
		EntityPairMapPhone phone = new EntityPairMapPhone();
		phone.setPerson(person);
		phone.setNumber("1234");
		phone.setSince(new Date());
		phone.setType(PhoneType.LAND_LINE);
		Date date = new Date();
		person.getPhoneRegister().put(phone.getType(), phone);
		phone = new EntityPairMapPhone();
		phone.setPerson(person);
		phone.setNumber("5678");
		phone.setType(PhoneType.MOBILE);
		phone.setSince(new Date(2019, 10, 11));
		person.getPhoneRegister().put(phone.getType(), phone);
		session.persist(person);

		tran.commit();
		session.close();

		session = factory.openSession();
		tran = session.beginTransaction();
		MapPairPerson per = session.find(MapPairPerson.class, 1l);
		assertTrue(per.getPhoneRegister().size() == 2);

		per.getPhoneRegister().remove(per.getPhoneRegister().values().iterator().next().getType());

		tran.commit();
		session.close();
		HibernateUtil.shutdown();

	}

	/**
	 * 数组默认作为binary存储, 也可以把list转换成varchar
	 */

	@Test
	public void testArray() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		ArrayBinaryPerson person = new ArrayBinaryPerson();
		person.setNames(new String[] { "person1", "person2", "person3" });
		person.setListNames(Arrays.asList("person1", "person2", "person3"));
		session.persist(person);

		tran.commit();
		session.close();

		session = factory.openSession();
		tran = session.beginTransaction();
		ArrayBinaryPerson per = session.find(ArrayBinaryPerson.class, 1l);
		assertEquals(3, per.getNames().length);
		assertEquals(3, per.getListNames().size());
		tran.commit();
		session.close();
		HibernateUtil.shutdown();

	}
}
