package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTabbedPane tabbedPane;
    private int userId;

    public MainView(int userId) {
        super("Gestão Financeira Pessoal");
        this.userId = userId;
        initializeUI();
    }

    // Configura a interface principal da aplicação
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem exitItem = new JMenuItem("Sair");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        tabbedPane = new JTabbedPane();

        DashboardView dashboardView = new DashboardView();
        TransactionView transactionView = new TransactionView();
        CategoryView categoryView = new CategoryView();

        tabbedPane.addTab("Dashboard", dashboardView);
        tabbedPane.addTab("Transações", transactionView);
        tabbedPane.addTab("Categorias", categoryView);

        add(tabbedPane);

        // Atualiza os dados da aba ao ser selecionada
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            Component selectedComponent = tabbedPane.getComponentAt(selectedIndex);

            if (selectedComponent instanceof TransactionView) {
                ((TransactionView) selectedComponent).updateData();
            } else if (selectedComponent instanceof CategoryView) {
                ((CategoryView) selectedComponent).updateData();
            } else if (selectedComponent instanceof DashboardView) {
                ((DashboardView) selectedComponent).updateData();
            }
        });

        dashboardView.updateData();
        transactionView.updateData();
        categoryView.updateData();
    }
}
