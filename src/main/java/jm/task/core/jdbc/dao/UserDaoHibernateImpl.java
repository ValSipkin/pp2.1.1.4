package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT auto_increment NOT NULL PRIMARY KEY, " +
                    "name varchar(45) charset utf8mb3 not null, " +
                    "lastname varchar(45) charset utf8mb3 not null, " +
                    "age tinyint not null)";
            session.createSQLQuery(createTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
        }
    }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            String dropTableSQL = "DROP TABLE IF EXISTS users;";
            session.createSQLQuery(dropTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
    }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e ) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> users = null;
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            users.forEach(System.out::println);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            String clearTableSQL = "DELETE FROM users;";
            session.createSQLQuery(clearTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
