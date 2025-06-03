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

    public List<Transaction> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Transaction", Transaction.class).list();
        }
    }

    public List<Transaction> findByType(Type type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transaction> query = session.createQuery("FROM Transaction WHERE type = :type", Transaction.class);
            query.setParameter("type", type);
            return query.list();
        }
    }

    public List<Transaction> findByDateRange(Date startDate, Date endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transaction> query = session.createQuery(
                    "FROM Transaction WHERE date BETWEEN :start AND :end", Transaction.class);
            query.setParameter("start", startDate);
            query.setParameter("end", endDate);
            return query.list();
        }
    }

    public double sumByType(Type type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                    "SELECT SUM(amount) FROM Transaction WHERE type = :type", Double.class);
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
