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
     * Adiciona uma nova transação
     * @param type Tipo (RECEITA/DESPESA)
     * @param amount Valor
     * @param category Categoria
     * @param date Data
     * @param description Descrição
     * @return Transação criada
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
            date = new Date(); // Data atual como padrão
        }

        Transaction transaction = new Transaction(type, amount, category, date, description);
        db.addTransaction(currentUserId, transaction);
        return transaction;
    }

    /**
     * Obtém todas as transações sem filtros
     * @return Lista completa de transações
     */
    public List<Transaction> getAllTransactions() {
        return db.getUserTransactions(currentUserId);
    }

    /**
     * Obtém transações com filtros
     * @param startDate Data inicial (opcional)
     * @param endDate Data final (opcional)
     * @param type Tipo (opcional)
     * @param category Categoria (opcional)
     * @return Lista filtrada de transações
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
     * Obtém todas as categorias disponíveis
     * @return Lista de categorias
     */
    public List<Category> getAllCategories() {
        return db.getUserCategories(currentUserId);
    }

    /**
     * Calcula o saldo total
     * @return Saldo (receitas - despesas)
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
     * Obtém o total de receitas
     * @return Soma de todas as receitas
     */
    public double getTotalIncome() {
        return db.getUserTransactions(currentUserId).stream()
                .filter(t -> t.getType() == Transaction.Type.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Obtém o total de despesas
     * @return Soma de todas as despesas
     */
    public double getTotalExpense() {
        return db.getUserTransactions(currentUserId).stream()
                .filter(t -> t.getType() == Transaction.Type.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}