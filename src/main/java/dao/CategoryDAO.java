package dao;

import model.Category;
import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryDAO {

    public void save(Category category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Category> findAll(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Category> query = session.createQuery(
                    "FROM Category WHERE user.id = :userId", Category.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    public Category findById(int id, int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Category> query = session.createQuery(
                    "FROM Category WHERE id = :id AND user.id = :userId", Category.class);
            query.setParameter("id", id);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        }
    }

    public void update(Category category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Category category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
