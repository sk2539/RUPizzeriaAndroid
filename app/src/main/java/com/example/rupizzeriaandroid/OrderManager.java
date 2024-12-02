package com.example.rupizzeriaandroid;

import java.util.ArrayList;

public class OrderManager {
    private static OrderManager instance;
    private final ArrayList<Order> orders = new ArrayList<>();

    private OrderManager() {}

    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
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
}