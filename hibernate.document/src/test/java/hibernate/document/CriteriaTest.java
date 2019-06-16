package hibernate.document;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import hibernate.document.model.Student;
import hibernate.document.model.StudentWrapper;
import hibernate.document.model.Student_;
import hibernate.document.util.HibernateUtil;

public class CriteriaTest {

	@Test
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

		// 基本使用
//		CriteriaBuilder builder = session.getCriteriaBuilder();
//		CriteriaQuery<Student> query = builder.createQuery(Student.class);
//		Root<Student> root = query.from(Student.class);
//		query.where(builder.equal(root.get("name"), "myname"));
//		Student stu = session.createQuery(query).uniqueResult();

		// 投影
//		CriteriaBuilder builder = session.getCriteriaBuilder();
//		CriteriaQuery<String> query = builder.createQuery(String.class);
//		Root<Student> root = query.from(Student.class);
//		query.where(builder.equal(root.get(Student_.name), "myname"));
//		query.select(builder.upper(root.get(Student_.name)));
//		String name = session.createQuery(query).getSingleResult();
//
//		assertEquals(name, "MYNAME");

		// 选择多个值
//		CriteriaBuilder builder = session.getCriteriaBuilder();
//		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
//		Root<Student> root = query.from(Student.class);
//		// 第一种方法
//		// query.select(builder.array(root.get(Student_.name), root.get(Student_.age)));
//		// 第二种方法
//		query.multiselect(root.get(Student_.name), root.get(Student_.age));
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<StudentWrapper> query = builder.createQuery(StudentWrapper.class);
		Root<Student> root = query.from(Student.class);
		// 选择多个值的第三种方法
		query.select(builder.construct(StudentWrapper.class, root.get(Student_.name), root.get(Student_.age)));

		List<StudentWrapper> resultList = session.createQuery(query).getResultList();

		for (StudentWrapper objects : resultList) {
			System.out.println(objects.getName() + ":" + objects.getAge());
		}

		HibernateUtil.shutdown();
	}

}
