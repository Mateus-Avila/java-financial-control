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
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Category) {
                    ((JLabel) c).setText(((Category) value).getName());
                }

                return c;
            }
        });
    }
}