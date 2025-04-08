package model;

import java.util.*;

public class Database {
    private static Database instance;
    private List<User> users;
    private Map<Integer, List<Category>> userCategories;
    private Map<Integer, List<Transaction>> userTransactions;

    private Database() {
        users = new ArrayList<>();
        userCategories = new HashMap<>();
        userTransactions = new HashMap<>();
        initializeSampleData(); // Adiciona dados iniciais
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

    // Métodos para Category - CORRIGIDOS
    public void addCategory(int userId, Category category) {
        if (!userCategories.containsKey(userId)) {
            userCategories.put(userId, new ArrayList<>());
        }
        userCategories.get(userId).add(category);
    }

    public List<Category> getUserCategories(int userId) {
        return userCategories.getOrDefault(userId, new ArrayList<>());
    }

    // Métodos para Transaction
    public void addTransaction(int userId, Transaction transaction) {
        if (!userTransactions.containsKey(userId)) {
            userTransactions.put(userId, new ArrayList<>());
        }
        userTransactions.get(userId).add(transaction);
    }

    public List<Transaction> getUserTransactions(int userId) {
        return userTransactions.getOrDefault(userId, new ArrayList<>());
    }

    // Dados iniciais para teste
    private void initializeSampleData() {
        // Usuário admin padrão
        User admin = new User("Admin", "admin@email.com", "admin123");
        addUser(admin);

        // Categorias padrão
        List<Category> defaultCategories = new ArrayList<>();
        defaultCategories.add(new Category("Alimentação", "Supermercado e restaurantes"));
        defaultCategories.add(new Category("Transporte", "Combustível e passagens"));
        defaultCategories.add(new Category("Moradia", "Aluguel e contas"));
        defaultCategories.add(new Category("Lazer", "Cinema e entretenimento"));

        userCategories.put(admin.getId(), defaultCategories);

        // Transações de exemplo
        List<Transaction> defaultTransactions = new ArrayList<>();
        defaultTransactions.add(new Transaction(
                Transaction.Type.INCOME,
                5000.00,
                defaultCategories.get(0),
                new Date(),
                "Salário"
        ));
        defaultTransactions.add(new Transaction(
                Transaction.Type.EXPENSE,
                350.50,
                defaultCategories.get(1),
                new Date(),
                "Combustível"
        ));

        userTransactions.put(admin.getId(), defaultTransactions);
    }
}