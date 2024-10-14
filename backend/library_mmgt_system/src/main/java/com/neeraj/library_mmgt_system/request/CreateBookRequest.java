package com.neeraj.library_mmgt_system.request;

public class CreateBookRequest {

    private String title;
    private String author;
    private String isbn;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String imageUrl;

    public CreateBookRequest() {
        // Default constructor
    }

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {  // Method to get category name
        return category;
    }

    public void setCategoryName(String category) {  // Method to set category name
        this.category = category;
    }
}

