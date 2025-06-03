package view;

import controller.TransactionController;
import model.Category;
import model.Transaction;
import view.components.CategoryComboBox;
import view.components.TransactionTable;
import javax.swing.SpinnerDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionView extends JPanel {
    private TransactionController controller;
    private JComboBox<String> typeComboBox;
    private JTextField amountField;
    private JComboBox<Category> categoryComboBox;
    private JSpinner dateSpinner;
    private JTextArea descriptionArea;
    private JButton saveButton;
    private TransactionTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionView() {
        this.controller = new TransactionController();
        initializeUI();
    }

    // Inicializa os componentes da interface
    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        SpinnerDateModel spinnerModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(spinnerModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        formPanel.add(dateSpinner);

        formPanel.add(new JLabel("Descrição:"));
        descriptionArea = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(descriptionArea));

        saveButton = new JButton("Salvar Transação");
        saveButton.addActionListener(e -> saveTransaction());

        String[] columns = {"ID", "Tipo", "Valor", "Categoria", "Data", "Descrição"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionTable = new TransactionTable();
        transactionTable.setModel(tableModel);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        updateData();
    }

    // Salva uma nova transação
    private void saveTransaction() {
        try {
            if (categoryComboBox.getSelectedItem() == null) {
                throw new Exception("Selecione uma categoria");
            }

            if (amountField.getText().trim().isEmpty()) {
                throw new Exception("Informe o valor");
            }

            Transaction.Type type = typeComboBox.getSelectedIndex() == 0 ?
                    Transaction.Type.INCOME : Transaction.Type.EXPENSE;

            double amount = Double.parseDouble(amountField.getText());
            Category category = (Category) categoryComboBox.getSelectedItem();
            Date date = ((SpinnerDateModel) dateSpinner.getModel()).getDate();
            String description = descriptionArea.getText();

            if (amount <= 0) {
                throw new Exception("O valor deve ser positivo");
            }

            if (description.trim().isEmpty()) {
                throw new Exception("Informe uma descrição");
            }

            controller.addTransaction(type, amount, category, date, description);
            updateData();

            amountField.setText("");
            descriptionArea.setText("");
            dateSpinner.setValue(new Date());

            JOptionPane.showMessageDialog(this, "Transação salva com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar transação: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Atualiza a lista de categorias e a tabela de transações
    public void updateData() {
        try {
            categoryComboBox.removeAllItems();
            List<Category> categories = controller.getAllCategories();
            categories.forEach(categoryComboBox::addItem);

            tableModel.setRowCount(0);
            List<Transaction> transactions = controller.getAllTransactions();

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (Transaction t : transactions) {
                tableModel.addRow(new Object[]{
                        t.getId(),
                        t.getType() == Transaction.Type.INCOME ? "Receita" : "Despesa",
                        currencyFormat.format(t.getAmount()),
                        t.getCategory().getName(),
                        dateFormat.format(t.getDate()),
                        t.getDescription()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar categorias: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
