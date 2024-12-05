package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * Singleton class for managing pizza orders in the application.
 * Provides functionality to add, remove, and retrieve orders,
 * as well as generate unique order numbers.
 */
public class OrderManager {
    private static OrderManager instance;
    private final ArrayList<Order> orders = new ArrayList<>();
    private int orderNumber = 1;

    /**
     * Private constructor to prevent instantiation.
     * Ensures only one instance of the class exists.
     */
    private OrderManager() {}

    /**
     * Retrieves the singleton instance of the OrderManager.
     * If no instance exists, a new one is created.
     *
     * @return The singleton instance of OrderManager.
     */
    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    /**
     * Generates a unique order number for each new order.
     * This method is synchronized to ensure thread safety.
     *
     * @return The next unique order number.
     */
    public synchronized int generateOrderNumber() {
        return orderNumber++;
    }

    /**
     * Adds a new order to the list of managed orders.
     *
     * @param order The order to add.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes an existing order from the list of managed orders.
     *
     * @param order The order to remove.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Retrieves a copy of the current list of orders.
     * Returns a new ArrayList to protect the internal list from modification.
     *
     * @return A copy of the current list of orders.
     */
    public ArrayList<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Clears all orders from the list.
     */
    public void clearOrders() {
        orders.clear();
    }

    /**
     * Retrieves the current order number, which represents
     * the next order number to be assigned.
     *
     * @return The current order number.
     */
    public int getCurrOrderNumber(){
        return orderNumber;
    }
}