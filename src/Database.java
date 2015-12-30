
import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database {
	final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session = threadLocal.get();

	private static Database instance = new Database();

	public static Database getInstace() {
		return instance;
	}

	private Database() {

	}

	public void persistStructure(ModifiedStructure structure) {
		session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.save(structure);
			session.getTransaction().commit();
			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
