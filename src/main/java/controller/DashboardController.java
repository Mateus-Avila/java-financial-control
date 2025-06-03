package controller;

import controller.TransactionController;
import model.Transaction;
import model.Category;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardController {
    private final TransactionController transactionController;

    public DashboardController() {
        this.transactionController = new TransactionController();
    }

    /**
     * Retorna o saldo atual.
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
        return transactionController.getAllTransactions().stream()
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

    /**
     * Retorna as transações com filtros.
     */
    public List<Transaction> getFilteredTransactions(Date start, Date end, Transaction.Type type, Category category) {
        return transactionController.getTransactions(start, end, type, category);
    }

    /**
     * Retorna todas as categorias cadastradas.
     */
    public List<Category> getAllCategories() {
        return transactionController.getAllCategories();
    }
}
