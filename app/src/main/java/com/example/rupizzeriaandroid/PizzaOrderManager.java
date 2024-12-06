package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * Singleton class for managing pizzas and orders in the application.
 * Combines functionality for handling both pizzas and orders,
 * including generating unique order numbers and managing global pizza states.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class PizzaOrderManager {
    private static PizzaOrderManager instance;
    private final ArrayList<Order> orders = new ArrayList<>();
    private final ArrayList<Pizza> pizzas = new ArrayList<>();
    private int orderNumber = 1;

    /**
     * Private constructor to prevent instantiation.
     * Ensures only one instance of the class is created.
     */
    private PizzaOrderManager() {
    }

    /**
     * Retrieves the singleton instance of PizzaOrderManager.
     * If no instance exists, a new one is created.
     *
     * @return The singleton instance of PizzaOrderManager.
     */
    public static synchronized PizzaOrderManager getInstance() {
        if (instance == null) {
            instance = new PizzaOrderManager();
        }
        return instance;
    }

    /**
     * Generates a unique order number for each new order.
     * This method is synchronized to ensure thread safety in multi-threaded environments.
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
     * Returns a new ArrayList to protect the internal list from external modification.
     *
     * @return A copy of the current list of orders.
     */
    public ArrayList<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Retrieves the current order number.
     * This number represents the next order number to be assigned.
     *
     * @return The current order number.
     */
    public int getCurrOrderNumber() {
        return orderNumber;
    }

    /**
     * Adds a pizza to the list of pizzas not yet assigned to an order.
     *
     * @param pizza The pizza to add.
     */
    public void addPizza(Pizza pizza) {
        if (pizza != null) {
            pizzas.add(pizza);
        }
    }

    /**
     * Retrieves a copy of the current list of pizzas.
     * Returns a new ArrayList to protect the internal list from external modification.
     *
     * @return A copy of the current list of pizzas.
     */
    public ArrayList<Pizza> getPizzas() {
        return new ArrayList<>(pizzas);
    }

    /**
     * Clears all pizzas from the list.
     * Typically used when all pizzas are assigned to an order.
     */
    public void clearPizzas() {
        pizzas.clear();
    }

    /**
     * Removes a specific pizza from the list of pizzas.
     *
     * @param pizza The pizza to remove.
     * @return True if the pizza was successfully removed; false otherwise.
     */
    public boolean removePizza(Pizza pizza) {
        return pizzas.remove(pizza);
    }
}
