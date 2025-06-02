package view;

import controller.AuthController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AuthView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private AuthController authController;

    public AuthView() {
        super("Autenticação");
        this.authController = new AuthController();
        initializeUI();
    }

    // Inicializa os componentes da interface de login
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::handleLogin);

        JButton registerButton = new JButton("Cadastrar");
        registerButton.addActionListener(this::showRegisterDialog);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Executa o processo de login ao clicar em "Login"
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            User user = authController.login(email, password);
            if (user != null) {
                new MainView(user.getId()).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciais inválidas", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Exibe o diálogo de cadastro de novo usuário
    private void showRegisterDialog(ActionEvent event) {
        JDialog registerDialog = new JDialog(this, "Cadastro", true);
        registerDialog.setSize(350, 250);
        registerDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(passwordField);
        panel.add(new JLabel("Confirmar Senha:"));
        panel.add(confirmPasswordField);

        JButton registerButton = new JButton("Cadastrar");
        registerButton.addActionListener(e -> {
            try {
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!password.equals(confirmPassword)) {
                    throw new IllegalArgumentException("As senhas não coincidem");
                }

                authController.register(
                        nameField.getText(),
                        emailField.getText(),
                        password
                );

                JOptionPane.showMessageDialog(registerDialog, "Cadastro realizado com sucesso!");
                registerDialog.dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(registerDialog, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerDialog.add(panel, BorderLayout.CENTER);
        registerDialog.add(registerButton, BorderLayout.SOUTH);
        registerDialog.setVisible(true);
    }
}
