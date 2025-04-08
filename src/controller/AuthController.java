package controller;

import model.User;
import model.Database;

public class AuthController {
    private Database db;

    public AuthController() {
        this.db = Database.getInstance();
    }

    /**
     * Realiza o login do usuário
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return Objeto User se autenticado, null caso contrário
     */
    public User login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email e senha são obrigatórios");
        }

        return db.authenticateUser(email, password);
    }

    /**
     * Registra um novo usuário
     * @param name Nome completo
     * @param email Email válido
     * @param password Senha (mínimo 6 caracteres)
     * @return true se registro bem sucedido
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

        // Verifica se usuário já existe
        if (db.authenticateUser(email, password) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        User newUser = new User(name, email, password);
        db.addUser(newUser);
        return true;
    }
}