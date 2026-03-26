package com.epam.framework.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<Product> products = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String postalCode;

    public Order addProduct(Product product) {
        products.add(product);
        return this;
    }

    public Order withShippingInfo(String firstName, String lastName, String postalCode) {
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.postalCode = postalCode;
        return this;
    }

    public List<Product> getProducts()  { return products; }
    public String getFirstName()        { return firstName; }
    public String getLastName()         { return lastName; }
    public String getPostalCode()       { return postalCode; }

    @Override
    public String toString() {
        return "Order{products=" + products + ", customer='" + firstName + " " + lastName + "'}";
    }
}
