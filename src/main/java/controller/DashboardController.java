package controller;

import model.Transaction;
import model.Category;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardController {
    private TransactionController transactionController;

    public DashboardController(int userId) {
        this.transactionController = new TransactionController(userId);
    }

    /**
     * Retorna o saldo atual do usuário.
     */
    public double getCurrentBalance() {
        return transactionController.getBalance();
    }

    /**
     * Retorna o total de receitas.
     */
    public double getTotalIncome() {
        return transactionController.getTotalIncome();
    }

    /**
     * Retorna o total de despesas.
     */
    public double getTotalExpense() {
        return transactionController.getTotalExpense();
    }

    /**
     * Retorna as transações mais recentes.
     *
     * @param limit Quantidade máxima de transações
     */
    public List<Transaction> getRecentTransactions(int limit) {
        return transactionController.getTransactions(null, null, null, null).stream()
                .sorted((t1, t2) -> t2.getDate().compareTo(t1.getDate()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Retorna as despesas agrupadas por categoria.
     */
    public Map<Category, Double> getExpensesByCategory() {
        return transactionController.getTransactions(null, null, Transaction.Type.EXPENSE, null).stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    public List<Transaction> getFilteredTransactions(Date start, Date end, Transaction.Type type, Category category) {
        return transactionController.getTransactions(start, end, type, category);
    }

    public List<Category> getAllCategories() {
        return transactionController.getAllCategories();
    }
}
