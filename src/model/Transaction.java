package model;

import java.util.Date;

public class Transaction {
    public enum Type { INCOME, EXPENSE }

    private static int nextId = 1;

    private int id;
    private Type type;
    private double amount;
    private Category category;
    private Date date;
    private String description;

    public Transaction(Type type, double amount, Category category, Date date, String description) {
        this.id = nextId++;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date != null ? date : new Date();
        this.description = description;
    }

    // Getters
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

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setType(Type type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(Category category) {
        this.category = category;
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