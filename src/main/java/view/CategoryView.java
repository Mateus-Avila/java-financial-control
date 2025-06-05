package view;

import controller.CategoryController;
import model.Category;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CategoryView extends JPanel {
    private CategoryController controller;
    private final int userId;
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextArea descriptionArea;

    public CategoryView(int userId) {
        this.userId = userId;
        this.controller = new CategoryController(userId);
        initializeUI();
    }

    // Configura os elementos visuais da interface
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        nameField = new JTextField();
        descriptionArea = new JTextArea(3, 20);

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(new JScrollPane(descriptionArea));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(this::addCategory);

        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(this::deleteCategory);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        String[] columns = {"ID", "Nome", "Descrição"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        categoryTable = new JTable(tableModel);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(categoryTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Adiciona uma nova categoria
    private void addCategory(ActionEvent event) {
        try {
            String name = nameField.getText();
            String description = descriptionArea.getText();

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("O nome da categoria é obrigatório");
            }

            controller.addCategory(name, description);
            updateData();

            nameField.setText("");
            descriptionArea.setText("");

            JOptionPane.showMessageDialog(this, "Categoria adicionada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao adicionar categoria: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Remove a categoria selecionada
    private void deleteCategory(ActionEvent event) {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma categoria para excluir",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int categoryId = (int) tableModel.getValueAt(selectedRow, 0);
        String categoryName = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir a categoria '" + categoryName + "'?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.removeCategory(categoryId);
                updateData();
                JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao excluir categoria: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Atualiza a tabela com as categorias do usuário
    public void updateData() {
        tableModel.setRowCount(0);

        List<Category> categories = controller.getAllCategories();

        for (Category c : categories) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getName(),
                    c.getDescription()
            });
        }
    }
}
