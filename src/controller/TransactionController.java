package controller;

import model.Transaction;
import model.Category;
import model.Database;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionController {
    private Database db;
    private int currentUserId;

    public TransactionController(int userId) {
        this.db = Database.getInstance();
        this.currentUserId = userId;
    }

    /**
     * Registra uma nova transação para o usuário atual.
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

        Transaction transaction = new Transaction(type, amount, category, date, description);
        db.addTransaction(currentUserId, transaction);
        return transaction;
    }

    /**
     * Retorna todas as transações do usuário atual.
     */
    public List<Transaction> getAllTransactions() {
        return db.getUserTransactions(currentUserId);
    }

    /**
     * Retorna transações filtradas por data, tipo e categoria.
     */
    public List<Transaction> getTransactions(Date startDate, Date endDate,
                                             Transaction.Type type, Category category) {
        List<Transaction> transactions = db.getUserTransactions(currentUserId);

        return transactions.stream()
                .filter(t -> startDate == null || !t.getDate().before(startDate))
                .filter(t -> endDate == null || !t.getDate().after(endDate))
                .filter(t -> type == null || t.getType() == type)
                .filter(t -> category == null || t.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     * Retorna todas as categorias cadastradas pelo usuário.
     */
    public List<Category> getAllCategories() {
        return db.getUserCategories(currentUserId);
    }

    /**
     * Retorna o saldo total (receitas - despesas).
     */
    public double getBalance() {
        List<Transaction> transactions = db.getUserTransactions(currentUserId);

        double income = transactions.stream()
                .filter(t -> t.getType() == Transaction.Type.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(t -> t.getType() == Transaction.Type.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return income - expense;
    }

    /**
     * Retorna o total de receitas.
     */
    public double getTotalIncome() {
        return db.getUserTransactions(currentUserId).stream()
                .filter(t -> t.getType() == Transaction.Type.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Retorna o total de despesas.
     */
    public double getTotalExpense() {
        return db.getUserTransactions(currentUserId).stream()
                .filter(t -> t.getType() == Transaction.Type.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
