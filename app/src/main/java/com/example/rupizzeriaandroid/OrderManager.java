package com.example.rupizzeriaandroid;

import java.util.ArrayList;

public class OrderManager {
    private static OrderManager instance;
    private final ArrayList<Order> orders = new ArrayList<>();
    private int orderNumber = 1;

    private OrderManager() {}

    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

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
}