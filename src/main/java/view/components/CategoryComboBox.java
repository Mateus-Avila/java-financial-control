package view.components;

import model.Category;
import javax.swing.*;
import java.awt.*;

public class CategoryComboBox extends JComboBox<Category> {
    public CategoryComboBox() {
        super();
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value == null) {
                    setText("Todas as categorias");
                } else if (value instanceof Category) {
                    setText(((Category) value).getName());
                }
                return this;
            }
        });
    }
}
