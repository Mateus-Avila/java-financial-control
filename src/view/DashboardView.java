package view;

import controller.DashboardController;
import model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DashboardView extends JPanel {
    private DashboardController controller;
    private JLabel balanceLabel;
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JTable recentTransactionsTable;
    private DefaultTableModel tableModel;

    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JComboBox<String> typeFilterComboBox;
    private JButton filterButton;

    public DashboardView(int userId) {
        this.controller = new DashboardController(userId);
        initializeUI();
        updateData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));

        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy"));

        typeFilterComboBox = new JComboBox<>(new String[]{"Todos", "Receita", "Despesa"});

        filterButton = new JButton("Filtrar");
        filterButton.addActionListener(e -> updateData());

        filterPanel.add(new JLabel("Início:"));
        filterPanel.add(startDateSpinner);
        filterPanel.add(new JLabel("Fim:"));
        filterPanel.add(endDateSpinner);
        filterPanel.add(new JLabel("Tipo:"));
        filterPanel.add(typeFilterComboBox);
        filterPanel.add(filterButton);

        // Painel de resumo
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        balanceLabel = new JLabel("Saldo: R$ 0,00", SwingConstants.CENTER);
        incomeLabel = new JLabel("Receitas: R$ 0,00", SwingConstants.CENTER);
        expenseLabel = new JLabel("Despesas: R$ 0,00", SwingConstants.CENTER);

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        balanceLabel.setFont(labelFont);
        incomeLabel.setFont(labelFont);
        expenseLabel.setFont(labelFont);

        balanceLabel.setForeground(Color.BLUE);
        incomeLabel.setForeground(new Color(0, 128, 0));
        expenseLabel.setForeground(Color.RED);

        summaryPanel.add(createSummaryCard(balanceLabel));
        summaryPanel.add(createSummaryCard(incomeLabel));
        summaryPanel.add(createSummaryCard(expenseLabel));

        // Tabela de transações
        String[] columnNames = {"Tipo", "Valor", "Categoria", "Data"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recentTransactionsTable = new JTable(tableModel);
        recentTransactionsTable.setAutoCreateRowSorter(true);

        // Layout final
        add(filterPanel, BorderLayout.NORTH);
        add(summaryPanel, BorderLayout.CENTER);
        add(new JScrollPane(recentTransactionsTable), BorderLayout.SOUTH);
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
        SwingUtilities.invokeLater(() -> {
            try {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                // Filtros
                Date startDate = ((SpinnerDateModel) startDateSpinner.getModel()).getDate();
                Date endDate = ((SpinnerDateModel) endDateSpinner.getModel()).getDate();
                String selectedType = (String) typeFilterComboBox.getSelectedItem();

                Transaction.Type filterType = null;
                if ("Receita".equals(selectedType)) {
                    filterType = Transaction.Type.INCOME;
                } else if ("Despesa".equals(selectedType)) {
                    filterType = Transaction.Type.EXPENSE;
                }

                List<Transaction> transactions = controller.getFilteredTransactions(startDate, endDate, filterType);

                // Cálculo dos totais
                double income = transactions.stream()
                        .filter(t -> t.getType() == Transaction.Type.INCOME)
                        .mapToDouble(Transaction::getAmount)
                        .sum();

                double expense = transactions.stream()
                        .filter(t -> t.getType() == Transaction.Type.EXPENSE)
                        .mapToDouble(Transaction::getAmount)
                        .sum();

                double balance = income - expense;

                balanceLabel.setText(String.format("Saldo: %s", currencyFormat.format(balance)));
                incomeLabel.setText(String.format("Receitas: %s", currencyFormat.format(income)));
                expenseLabel.setText(String.format("Despesas: %s", currencyFormat.format(expense)));

                // Atualiza tabela
                tableModel.setRowCount(0);
                transactions.stream()
                        .sorted((t1, t2) -> t2.getDate().compareTo(t1.getDate()))
                        .limit(5)
                        .forEach(t -> {
                            tableModel.addRow(new Object[]{
                                    t.getType() == Transaction.Type.INCOME ? "Receita" : "Despesa",
                                    currencyFormat.format(t.getAmount()),
                                    t.getCategory().getName(),
                                    dateFormat.format(t.getDate())
                            });
                        });

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao atualizar dados: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
