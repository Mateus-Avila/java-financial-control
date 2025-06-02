package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransactionTable extends JTable {
    public TransactionTable() {
        super(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Tipo", "Valor", "Categoria", "Data", "Descrição"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        setAutoCreateRowSorter(true);
        setFillsViewportHeight(true);
    }
}
