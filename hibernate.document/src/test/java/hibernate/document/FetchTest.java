package hibernate.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import hibernate.document.model.fetch.eager.Department;
import hibernate.document.model.fetch.eager.Employee;
import hibernate.document.util.HibernateUtil;

public class FetchTest {

	// @Test
	public void testFetch() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Employee e1 = new Employee();
		Department department = new Department();
		department.setName("d1");
		e1.setDepartment(department);
		e1.setName("e1");
		session.save(department);
		session.save(e1);

		e1 = new Employee();
		department = new Department();
		department.setName("d1");
		e1.setDepartment(department);
		e1.setName("e1");
		session.save(department);
		session.save(e1);

		e1 = new Employee();
		department = new Department();
		department.setName("d1");
		e1.setDepartment(department);
		e1.setName("e1");
		session.save(department);
		session.save(e1);

		session.flush();
		session.clear();
		// 如果设置了eager并且在hql中没有连接语句，那么
		// 有多少条employee，就会另外查询多少次department
		// 这个时候最好用lazyload

		// 如果用find那么在eager的情况下会使用outer join
		List<Employee> list = session.createQuery("from Employee", Employee.class).list();

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		HibernateUtil.shutdown();
	}

	/**
	 * hql,criterial加载关联实体
	 */

	// @Test
	public void testFetchEager() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Employee e1 = new Employee();
		Department department = new Department();
		department.setName("d1");
		e1.setDepartment(department);
		e1.setName("e1");
		session.save(department);
		session.save(e1);

		e1 = new Employee();
		department = new Department();
		department.setName("d1");
		e1.setDepartment(department);
		e1.setName("e2");
		session.save(department);
		session.save(e1);

		e1 = new Employee();
		department = new Department();
		department.setName("d1");
		e1.setDepartment(department);
		e1.setName("e3");
		session.save(department);
		session.save(e1);

		session.flush();
		session.clear();

		// hql
		List<Employee> list = session
				.createQuery("select e from Employee e" + " left join e.department", Employee.class).list();

		// criterial

//		CriteriaBuilder builder = session.getCriteriaBuilder();
//		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
//		Root<Employee> from = query.from(Employee.class);
//		from.fetch("department", JoinType.LEFT);
//		query.select(from).where(builder.equal(from.get("name"), "e1"));
//		Employee singleResult = session.createQuery(query).getSingleResult();
//		assertEquals(singleResult.getName(), "e1");

		session.enableFetchProfile("employee.department");
		Employee employee = session.find(Employee.class, 1);

		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		HibernateUtil.shutdown();
	}

	/**
	 * 测试fetchType
	 */
	@Test
	public void testFetchType() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		assertNotNull(sessionFactory);
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Employee e1 = new Employee();
		Department department = new Department();
		department.setName("d1");
		e1.setDepartment(department);
		e1.setName("e1");
		session.save(department);
		session.save(e1);

		e1 = new Employee();

		department.setName("d1");
		e1.setDepartment(department);
		e1.setName("e1");

		session.save(e1);

		e1 = new Employee();

		e1.setDepartment(department);
		e1.setName("e1");

		session.save(e1);

		Department department2 = new Department();
		department2.setName("d2");
		e1 = new Employee();
		e1.setName("e3");
		department2.addEmployee(e1);
		e1 = new Employee();
		e1.setName("e4");
		department2.addEmployee(e1);
		e1 = new Employee();
		e1.setName("e5");
		department2.addEmployee(e1);
		session.persist(department2);

		session.flush();
		session.clear();
		List<Department> multiLoad = session.byMultipleIds(Department.class).multiLoad(1, 2);

		assertEquals(multiLoad.get(0).getEmployees().size(), 3);
		assertEquals(multiLoad.get(1).getEmployees().size(), 3);
		tran.commit();
		session.close();

		session = sessionFactory.openSession();

		HibernateUtil.shutdown();
	}
}
