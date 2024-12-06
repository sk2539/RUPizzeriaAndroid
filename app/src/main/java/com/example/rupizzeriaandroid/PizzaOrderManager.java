package com.example.rupizzeriaandroid;

import java.util.ArrayList;

public class PizzaOrderManager {
    private static PizzaOrderManager instance;
    private final ArrayList<Order> orders = new ArrayList<>();
    private final ArrayList<Pizza> pizzas = new ArrayList<>();
    private int orderNumber = 1;

    // Private constructor to prevent instantiation
    private PizzaOrderManager() {}

    // Singleton instance getter
    public static synchronized PizzaOrderManager getInstance() {
        if (instance == null) {
            instance = new PizzaOrderManager();
        }
        return instance;
    }

    // Order management methods
    public synchronized int generateOrderNumber() {
        return orderNumber++;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public ArrayList<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public void clearOrders() {
        orders.clear();
    }

    public int getCurrOrderNumber() {
        return orderNumber;
    }

    // Pizza management methods
    public void addPizza(Pizza pizza) {
        if (pizza != null) {
            pizzas.add(pizza);
        }
    }

    public ArrayList<Pizza> getPizzas() {
        return new ArrayList<>(pizzas);
    }

    public void clearPizzas() {
        pizzas.clear();
    }

    public boolean removePizza(Pizza pizza) {
        return pizzas.remove(pizza);
    }

    public int getPizzaCount() {
        return pizzas.size();
    }
}
