package model;

import jakarta.persistence.*;
import model.User;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    public enum Type {
        INCOME, EXPENSE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String description;

    public Transaction() {
        // Construtor vazio obrigatório para Hibernate
    }

    public Transaction(Type type, double amount, Category category, User user, Date date, String description) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.user = user;
        this.date = date != null ? date : new Date();
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", category=" + category +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
