package com.example.vamsi.login;

public class Product {
    private int id;
    private int image; // Resource ID for the product image
    private String description;
    private double price;

    // Constructors
    public Product() {
    }

    public Product(int id, int image, String description, double price) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.price = price;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
