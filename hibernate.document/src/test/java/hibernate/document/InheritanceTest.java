package hibernate.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import hibernate.document.model.inheritance.Blog;
import hibernate.document.model.inheritance.CreditAccount;
import hibernate.document.model.inheritance.DebitAccount;
import hibernate.document.model.inheritance.DomainModelEntity;
import hibernate.document.model.inheritance.InterfaceBook;
import hibernate.document.model.inheritance.SingleTableAccount;
import hibernate.document.model.inheritance.SingleTableCreditAccount;
import hibernate.document.model.inheritance.SingleTableDebit;
import hibernate.document.util.HibernateUtil;

public class InheritanceTest {
	/**
	 * 简单的把共有的属性放到父类中，子类定义特有的属性
	 */
	// @Test
	public void test() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		CreditAccount account = new CreditAccount();
		account.setBalance(BigDecimal.valueOf(12.3));
		account.setOwner("mike");
		account.setCreditLimit(BigDecimal.valueOf(1.5));

		DebitAccount account2 = new DebitAccount();
		account2.setBalance(BigDecimal.valueOf(18.3));
		account2.setOverdraftFee(BigDecimal.valueOf(19.3));
		account.setOwner("kity");
		session.saveOrUpdate(account);
		session.saveOrUpdate(account2);
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}

	/**
	 * singleTable的继承，@DiscriminatorFormula和@DiscriminatorValue的用法
	 */

	// @Test
	public void testSingleTable() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		SingleTableCreditAccount account = new SingleTableCreditAccount();
		account.setBalance(BigDecimal.valueOf(12.3));
		account.setOwner("mike");
		account.setCreditLimit(BigDecimal.valueOf(1.5));
		session.persist(account);
		SingleTableDebit account2 = new SingleTableDebit();
		account2.setBalance(BigDecimal.valueOf(18.3));
		account2.setOverdraftFee(BigDecimal.valueOf(19.3));
		account2.setOwner("kity");

		session.saveOrUpdate(account2);

		SingleTableAccount account3 = new SingleTableAccount();
		account3.setOwner("david");
		account3.setBalance(BigDecimal.valueOf(123));
		session.persist(account3);
		tran.commit();
		session.close();

		session = sessionFactory.openSession();
		List<SingleTableAccount> list = session.createQuery("from Account", SingleTableAccount.class).list();
		assertTrue(list.get(0) instanceof SingleTableCreditAccount);
		assertTrue(list.get(1) instanceof SingleTableDebit);
		assertTrue(list.get(2) instanceof SingleTableAccount);
		session.close();
		HibernateUtil.shutdown();

	}

	/**
	 * 接口的实现
	 */
	@Test
	public void testInterface() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		InterfaceBook book = new InterfaceBook();
		book.setAuthor("auto");
		book.setTitle("my title");
		book.setVersion(1);
		session.persist(book);
		Blog blog = new Blog();
		blog.setSite("this site");
		blog.setVersion(2);
		session.persist(blog);
//		session.flush();
//		session.clear();
		List<DomainModelEntity> list = session
				.createQuery(" FROM hibernate.document.model.inheritance.DomainModelEntity", DomainModelEntity.class)
				.list();
		assertEquals(1, list.size());
		tran.commit();
		session.close();
		HibernateUtil.shutdown();
	}
}
