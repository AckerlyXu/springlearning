package hibernate.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import hibernate.document.model.naturlid.Book;
import hibernate.document.model.naturlid.Isbn;
import hibernate.document.model.naturlid.NatualPerson;
import hibernate.document.util.HibernateUtil;

public class NatualIdTest {
	/**
	 * 简单natualId的映射和加载
	 */
	// @Test
	public void test() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		NatualPerson person = new NatualPerson();
		person.setAge(12);
		person.setName("ackerly");
		session.persist(person);
		tran.commit();
		session.close();

		session = factory.openSession();
		tran = session.beginTransaction();
		NatualPerson loadPerson = session.byNaturalId(NatualPerson.class).using("name", "ackerly").load();
		assertEquals("ackerly", loadPerson.getName());
		loadPerson = session.bySimpleNaturalId(NatualPerson.class)

				.load("ackerly");
		assertEquals("ackerly", loadPerson.getName());
		tran.commit();
		session.close();
		HibernateUtil.shutdown();

	}

	/**
	 * 在一个实体中使用多个natualId
	 */
	@Test
	public void testMutiNatualId() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		Book book = new Book();

		book.setAuthor("ok");
		Isbn isbn = new Isbn();
		isbn.setIsbn10("10");
		isbn.setIsbn13("13");
		book.setTitle("ttile");
		book.setIsbn(isbn);
		session.persist(book);
		tran.commit();
		session.close();

		session = factory.openSession();
		tran = session.beginTransaction();

		Book book1 = null;
		// 如果session里面没有book1，那么返回的是代理对象
		book1 = session.byNaturalId(Book.class).using("author", "ok").using("isbn", isbn).getReference();
		assertNotNull(book1);

		assertFalse(book1.getClass() == Book.class);
		// 如果session里面没有book1,那么返回已经加载的book(不是代理)，反之返回session里面的book1（不管是不是代理）
		book1 = session.byNaturalId(Book.class).using("author", "ok").using("isbn", isbn).load();
		// assertTrue(book1.getClass() == Book.class);
		session.close();
		HibernateUtil.shutdown();

	}

}
