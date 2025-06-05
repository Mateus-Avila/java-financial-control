package controller;

import dao.CategoryDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import model.Transaction;
import model.Category;
import model.User;

import java.util.Date;
import java.util.List;

public class TransactionController {

    private final TransactionDAO transactionDAO;
    private final CategoryDAO categoryDAO;
    private final UserDAO userDAO;
    private final int userId;

    public TransactionController(int userId) {
        this.transactionDAO = new TransactionDAO();
        this.categoryDAO = new CategoryDAO();
        this.userDAO = new UserDAO();
        this.userId = userId;
    }

    /**
     * Registra uma nova transação.
     */
    public Transaction addTransaction(Transaction.Type type, double amount,
                                      Category category, Date date, String description) {
        if (type == null) {
            throw new IllegalArgumentException("Tipo de transação é obrigatório");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }

        if (category == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }

        if (date == null) {
            date = new Date();
        }

        User user = userDAO.findById(userId);
        Transaction transaction = new Transaction(type, amount, category, user, date, description);
        transactionDAO.save(transaction);
        return transaction;
    }

    /**
     * Retorna todas as transações do sistema.
     */
    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll(userId);
    }

    /**
     * Retorna transações filtradas por data, tipo e categoria.
     */
    public List<Transaction> getTransactions(Date startDate, Date endDate,
                                             Transaction.Type type, Category category) {
        List<Transaction> filtered = transactionDAO.findByDateRange(startDate, endDate, userId);

        return filtered.stream()
                .filter(t -> type == null || t.getType() == type)
                .filter(t -> category == null || t.getCategory().equals(category))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Retorna todas as categorias cadastradas.
     */
    public List<Category> getAllCategories() {
        return categoryDAO.findAll(userId);
    }

    /**
     * Retorna o saldo total (receitas - despesas).
     */
    public double getBalance() {
        return getTotalIncome() - getTotalExpense();
    }

    /**
     * Retorna o total de receitas.
     */
    public double getTotalIncome() {
        return transactionDAO.sumByType(Transaction.Type.INCOME, userId);
    }

    /**
     * Retorna o total de despesas.
     */
    public double getTotalExpense() {
        return transactionDAO.sumByType(Transaction.Type.EXPENSE, userId);
    }
}
