package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static Database instance;
    private List<User> users;
    private Map<Integer, List<Category>> userCategories;
    private Map<Integer, List<Transaction>> userTransactions;

    private Database() {
        users = new ArrayList<>();
        userCategories = new HashMap<>();
        userTransactions = new HashMap<>();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Métodos para User
    public void addUser(User user) {
        users.add(user);
        userCategories.put(user.getId(), new ArrayList<>());
        userTransactions.put(user.getId(), new ArrayList<>());
    }

    public User authenticateUser(String email, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // Métodos para Category
    public void addCategory(int userId, Category category) {
        userCategories.get(userId).add(category);
    }

    public List<Category> getUserCategories(int userId) {
        return userCategories.get(userId);
    }

    // Métodos para Transaction
    public void addTransaction(int userId, Transaction transaction) {
        userTransactions.get(userId).add(transaction);
    }

    public List<Transaction> getUserTransactions(int userId) {
        return userTransactions.get(userId);
    }
}