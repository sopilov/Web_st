import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;


public class DBService {

    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "test");
        configuration.setProperty("hibernate.connection.password", "test");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        return configuration;
    }

    public UsersDataSet getUser(String name) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet dataSet = dao.get(dao.getUserId(name));
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }


    public long addUser(String login, String password) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(login, password);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void getAll () {
        Session session = sessionFactory.openSession();
        System.out.println("извлечение всех записей с помощью Criteria:");
        List<UsersDataSet> users = session.createCriteria(UsersDataSet.class).list(); //извлечение всех записей с помощью Criteria
        for (UsersDataSet user : users) {
            System.out.println(user);
        }

        System.out.println("извлечение всех записей с помощью HQL:");
        List<UsersDataSet> users2 = session.createQuery("from UsersDataSet").list();  // извлечение всех записей с помощью HQL
        for (UsersDataSet user : users2) {
            System.out.println(user);
        }

        System.out.println("использование именованных параметров:");
        Query query = session.createQuery("from UsersDataSet where name = :paramName"); // использование именованных параметров
        String userName = "user1";
        query.setParameter("paramName", userName);
        UsersDataSet user = (UsersDataSet) query.uniqueResult();
        System.out.println(user);


        System.out.println("Достать с помощью hql только имена всех ползователей:");
        List<String> usersName = session.createQuery("select user.name from UsersDataSet as user").list();
        for (String name: usersName) {
            System.out.println(name);
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
