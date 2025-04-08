package view;

import controller.TransactionController;
import model.Category;
import model.Transaction;
import view.components.CategoryComboBox;
import view.components.TransactionTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;  // Import correto para List

public class TransactionView extends JPanel {
    private TransactionController controller;
    private JComboBox<String> typeComboBox;
    private JTextField amountField;
    private JComboBox<Category> categoryComboBox;
    private JFormattedTextField dateField;
    private JTextArea descriptionArea;
    private JButton saveButton;
    private TransactionTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionView(int userId) {
        this.controller = new TransactionController(userId);  // Inicializa o controller
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Tipo:"));
        typeComboBox = new JComboBox<>(new String[]{"Receita", "Despesa"});
        formPanel.add(typeComboBox);

        formPanel.add(new JLabel("Valor:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Categoria:"));
        categoryComboBox = new CategoryComboBox();
        formPanel.add(categoryComboBox);

        formPanel.add(new JLabel("Data:"));
        dateField = new JFormattedTextField(new Date());
        formPanel.add(dateField);

        formPanel.add(new JLabel("Descrição:"));
        descriptionArea = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(descriptionArea));

        // Botão de salvar
        saveButton = new JButton("Salvar Transação");
        saveButton.addActionListener(e -> saveTransaction());

        // Tabela de transações
        String[] columns = {"ID", "Tipo", "Valor", "Categoria", "Data", "Descrição"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionTable = new TransactionTable();
        transactionTable.setModel(tableModel);  // Define o model na tabela

        // Layout
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        // Carrega dados iniciais
        updateData();
    }

    private void saveTransaction() {
        try {
            Transaction.Type type = typeComboBox.getSelectedIndex() == 0 ?
                    Transaction.Type.INCOME : Transaction.Type.EXPENSE;

            double amount = Double.parseDouble(amountField.getText());
            Category category = (Category) categoryComboBox.getSelectedItem();
            Date date = (Date) dateField.getValue();
            String description = descriptionArea.getText();

            controller.addTransaction(type, amount, category, date, description);
            updateData();

            // Limpa campos
            amountField.setText("");
            descriptionArea.setText("");

            JOptionPane.showMessageDialog(this, "Transação salva com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar transação: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        // Atualiza combobox de categorias
        categoryComboBox.removeAllItems();
        controller.getAllCategories().forEach(categoryComboBox::addItem);

        // Atualiza tabela
        tableModel.setRowCount(0);
        List<Transaction> transactions = controller.getAllTransactions();

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        for (Transaction t : transactions) {
            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getType() == Transaction.Type.INCOME ? "Receita" : "Despesa",
                    currencyFormat.format(t.getAmount()),
                    t.getCategory().getName(),
                    t.getDate(),
                    t.getDescription()
            });
        }
    }
}