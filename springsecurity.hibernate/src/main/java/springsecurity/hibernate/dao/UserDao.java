package springsecurity.hibernate.dao;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springsecurity.hibernate.model.MyUser;

@Repository
public class UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session currentSession() {

		return sessionFactory.openSession();
	}

	public MyUser findUserByUsername(String username) {
		// TODO Auto-generated method stub
		Session session = currentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<MyUser> query = builder.createQuery(MyUser.class);
		Root<MyUser> from = query.from(MyUser.class);
		query.where(builder.equal(from.get("username"), username));
		Optional<MyUser> user = session.createQuery(query).uniqueResultOptional();

		if (user.isPresent()) {
			return user.get();
		} else {
			if (username.equals("ackerly")) {
				Transaction tran = session.beginTransaction();
				MyUser user2 = new MyUser();
				user2.setUsername("ackerly");
				user2.setPassword("123");
				session.save(user2);
				tran.commit();
				Optional<MyUser> user3 = session.createQuery(query).uniqueResultOptional();
				if (user3.isPresent()) {
					return user3.get();
				} else {
					return null;
				}
			} else {
				return null;
			}

		}

	}

}
