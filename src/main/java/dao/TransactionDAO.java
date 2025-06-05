package dao;

import model.Transaction;
import model.Transaction.Type;
import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class TransactionDAO {

    public void save(Transaction transaction) {
        org.hibernate.Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(transaction);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Transaction> findAll(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transaction> query = session.createQuery(
                    "FROM Transaction WHERE user.id = :userId", Transaction.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    public List<Transaction> findByType(Type type, int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transaction> query = session.createQuery(
                    "FROM Transaction WHERE user.id = :userId AND type = :type", Transaction.class);
            query.setParameter("type", type);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    public List<Transaction> findByDateRange(Date startDate, Date endDate, int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transaction> query = session.createQuery(
                    "FROM Transaction WHERE user.id = :userId AND date BETWEEN :start AND :end", Transaction.class);
            query.setParameter("userId", userId);
            query.setParameter("start", startDate);
            query.setParameter("end", endDate);
            return query.list();
        }
    }

    public double sumByType(Type type, int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                    "SELECT SUM(amount) FROM Transaction WHERE user.id = :userId AND type = :type", Double.class);
            query.setParameter("userId", userId);
            query.setParameter("type", type);
            Double result = query.uniqueResult();
            return result != null ? result : 0.0;
        }
    }

    public void delete(Transaction transaction) {
        org.hibernate.Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(transaction);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
