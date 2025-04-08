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

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Menu superior
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem exitItem = new JMenuItem("Sair");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Painel de abas
        tabbedPane = new JTabbedPane();

        DashboardView dashboardView = new DashboardView(userId);
        TransactionView transactionView = new TransactionView(userId);
        CategoryView categoryView = new CategoryView(userId);

        tabbedPane.addTab("Dashboard", dashboardView);
        tabbedPane.addTab("Transações", transactionView);
        tabbedPane.addTab("Categorias", categoryView);

        add(tabbedPane);

        // Atualiza os dados ao abrir
        dashboardView.updateData();
        transactionView.updateData();
        categoryView.updateData();
    }
}