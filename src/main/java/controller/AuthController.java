package controller;

import dao.UserDAO;
import model.User;

public class AuthController {

    private final UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
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

        User user = userDAO.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
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

        User existingUser = userDAO.findByEmail(email);
        if (existingUser != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        User newUser = new User(name, email, password);
        userDAO.save(newUser);
        return true;
    }
}
