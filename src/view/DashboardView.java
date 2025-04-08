package view;

import controller.DashboardController;
import model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;

public class DashboardView extends JPanel {
    private DashboardController controller;

    private JLabel balanceLabel;
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JTable recentTransactionsTable;

    public DashboardView(int userId) {
        this.controller = new DashboardController(userId);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de resumo
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        balanceLabel = new JLabel("Saldo: R$ 0,00", SwingConstants.CENTER);
        incomeLabel = new JLabel("Receitas: R$ 0,00", SwingConstants.CENTER);
        expenseLabel = new JLabel("Despesas: R$ 0,00", SwingConstants.CENTER);

        // Estilização dos labels
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        balanceLabel.setFont(labelFont);
        incomeLabel.setFont(labelFont);
        expenseLabel.setFont(labelFont);

        balanceLabel.setForeground(Color.BLUE);
        incomeLabel.setForeground(new Color(0, 128, 0)); // Verde escuro
        expenseLabel.setForeground(Color.RED);

        summaryPanel.add(createSummaryCard(balanceLabel));
        summaryPanel.add(createSummaryCard(incomeLabel));
        summaryPanel.add(createSummaryCard(expenseLabel));

        // Tabela de transações recentes
        recentTransactionsTable = new JTable(new Object[][]{},
                new String[]{"Tipo", "Valor", "Categoria", "Data"});

        // Layout principal
        add(summaryPanel, BorderLayout.NORTH);
        add(new JScrollPane(recentTransactionsTable), BorderLayout.CENTER);
    }

    private JPanel createSummaryCard(JLabel contentLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.add(contentLabel, BorderLayout.CENTER);
        return card;
    }

    public void updateData() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        double balance = controller.getCurrentBalance();
        double income = controller.getTotalIncome();
        double expense = controller.getTotalExpense();

        balanceLabel.setText(String.format("Saldo: %s", currencyFormat.format(balance)));
        incomeLabel.setText(String.format("Receitas: %s", currencyFormat.format(income)));
        expenseLabel.setText(String.format("Despesas: %s", currencyFormat.format(expense)));

        // Atualiza tabela de transações recentes
        DefaultTableModel model = (DefaultTableModel) recentTransactionsTable.getModel();
        model.setRowCount(0);

        controller.getRecentTransactions(5).forEach(t -> {
            model.addRow(new Object[]{
                    t.getType() == Transaction.Type.INCOME ? "Receita" : "Despesa",
                    currencyFormat.format(t.getAmount()),
                    t.getCategory().getName(),
                    t.getDate()
            });
        });
    }
}