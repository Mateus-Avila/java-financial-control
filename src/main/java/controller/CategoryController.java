package controller;

import dao.CategoryDAO;
import dao.UserDAO;
import model.Category;
import model.User;

import java.util.List;

public class CategoryController {

    private final CategoryDAO categoryDAO;
    private final UserDAO userDAO;
    private final int userId;

    public CategoryController(int userId) {
        this.categoryDAO = new CategoryDAO();
        this.userDAO = new UserDAO();
        this.userId = userId;
    }

    /**
     * Cadastra uma nova categoria.
     *
     * @param name Nome da categoria
     * @param description Descrição da categoria
     * @return Categoria criada
     */
    public Category addCategory(String name, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }

        List<Category> existing = categoryDAO.findAll(userId);
        if (existing.stream().anyMatch(c -> c.getName().equalsIgnoreCase(name))) {
            throw new IllegalArgumentException("Categoria já existe");
        }
        User user = userDAO.findById(userId);
        Category newCategory = new Category(name, description, user);
        categoryDAO.save(newCategory);
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
        Category category = categoryDAO.findById(categoryId, userId);

        if (category == null) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }

        if (newName != null && !newName.isEmpty()) {
            category.setName(newName);
        }

        if (newDescription != null) {
            category.setDescription(newDescription);
        }

        categoryDAO.update(category);
        return category;
    }

    /**
     * Remove uma categoria pelo ID.
     *
     * @param categoryId ID da categoria
     * @return true se removido
     */
    public boolean removeCategory(int categoryId) {
        Category category = categoryDAO.findById(categoryId, userId);
        if (category != null) {
            categoryDAO.delete(category);
            return true;
        }
        return false;
    }

    /**
     * Retorna todas as categorias do sistema.
     */
    public List<Category> getAllCategories() {
        return categoryDAO.findAll(userId);
    }

    /**
     * Retorna uma categoria pelo ID.
     *
     * @param categoryId ID da categoria
     * @return Categoria correspondente ou null
     */
    public Category getCategoryById(int categoryId) {
        return categoryDAO.findById(categoryId, userId);
    }
}
