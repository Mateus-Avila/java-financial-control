package controller;

import model.Category;
import model.Database;
import java.util.List;

public class CategoryController {
    private Database db;
    private int currentUserId;

    public CategoryController(int userId) {
        this.db = Database.getInstance();
        this.currentUserId = userId;
    }

    /**
     * Cadastra uma nova categoria para o usuário atual.
     *
     * @param name Nome da categoria
     * @param description Descrição da categoria
     * @return Categoria criada
     */
    public Category addCategory(String name, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }

        if (db.getUserCategories(currentUserId).stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name))) {
            throw new IllegalArgumentException("Categoria já existe");
        }

        Category newCategory = new Category(name, description);
        db.addCategory(currentUserId, newCategory);
        return newCategory;
    }

    /**
     * Atualiza uma categoria existente.
     *
     * @param categoryId ID da categoria
     * @param newName Novo nome
     * @param newDescription Nova descrição
     * @return Categoria atualizada
     */
    public Category updateCategory(int categoryId, String newName, String newDescription) {
        Category category = getCategoryById(categoryId);

        if (category == null) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }

        if (newName != null && !newName.isEmpty()) {
            category.setName(newName);
        }

        if (newDescription != null) {
            category.setDescription(newDescription);
        }

        return category;
    }

    /**
     * Remove uma categoria pelo ID.
     *
     * @param categoryId ID da categoria
     * @return true se a categoria for removida
     */
    public boolean removeCategory(int categoryId) {
        List<Category> categories = db.getUserCategories(currentUserId);
        return categories.removeIf(c -> c.getId() == categoryId);
    }

    /**
     * Retorna todas as categorias do usuário atual.
     */
    public List<Category> getAllCategories() {
        return db.getUserCategories(currentUserId);
    }

    /**
     * Retorna uma categoria pelo ID.
     *
     * @param categoryId ID da categoria
     * @return Categoria correspondente ou null
     */
    public Category getCategoryById(int categoryId) {
        return db.getUserCategories(currentUserId).stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst()
                .orElse(null);
    }
}
