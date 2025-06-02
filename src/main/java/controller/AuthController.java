package controller;

import model.User;
import model.Database;

public class AuthController {
    private Database db;

    public AuthController() {
        this.db = Database.getInstance();
    }

    /**
     * Efetua o login do usuário.
     *
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return Usuário autenticado ou null se inválido
     */
    public User login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email e senha são obrigatórios");
        }

        return db.authenticateUser(email, password);
    }

    /**
     * Realiza o cadastro de um novo usuário.
     *
     * @param name Nome do usuário
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return true se o cadastro for bem-sucedido
     */
    public boolean register(String name, String email, String password) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        }

        if (db.authenticateUser(email, password) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        User newUser = new User(name, email, password);
        db.addUser(newUser);
        return true;
    }
}
