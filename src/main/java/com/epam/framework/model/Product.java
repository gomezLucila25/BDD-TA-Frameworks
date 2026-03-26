package com.epam.framework.model;

public class Product {

    private final String name;
    private final double price;
    private String description;

    public Product(String name, double price) {
        this.name  = name;
        this.price = price;
    }

    public Product(String name, double price, String description) {
        this(name, price);
        this.description = description;
    }

    public String getName()        { return name; }
    public double getPrice()       { return price; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + "}";
    }
}
