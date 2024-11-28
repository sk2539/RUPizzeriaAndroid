package com.example.rupizzeriaandroid;

import java.util.ArrayList;

public class PizzaManager {
    private static PizzaManager instance;
    private final ArrayList<Pizza> pizzas;
    private PizzaManager() {
        pizzas = new ArrayList<>();
    }

    /**
     * Get the singleton instance of PizzaManager.
     *
     * @return The single instance of PizzaManager.
     */
    public static PizzaManager getInstance() {
        if (instance == null) {
            synchronized (PizzaManager.class) { // Thread safety
                if (instance == null) {
                    instance = new PizzaManager();
                }
            }
        }
        return instance;
    }

    /**
     * Add a pizza to the current list of pizzas.
     *
     * @param pizza The pizza to add.
     */
    public void addPizza(Pizza pizza) {
        if (pizza != null) {
            pizzas.add(pizza);
        }
    }

    /**
     * Get the current list of pizzas.
     *
     * @return The list of pizzas.
     */
    public ArrayList<Pizza> getPizzas() {
        return new ArrayList<>(pizzas); // Return a copy to avoid external modification
    }

    /**
     * Clear all pizzas from the current order.
     */
    public void clearPizzas() {
        pizzas.clear();
    }

    /**
     * Remove a specific pizza from the order.
     *
     * @param pizza The pizza to remove.
     * @return True if the pizza was removed; false otherwise.
     */
    public boolean removePizza(Pizza pizza) {
        return pizzas.remove(pizza);
    }

    /**
     * Get the total number of pizzas in the order.
     *
     * @return The total number of pizzas.
     */
    public int getPizzaCount() {
        return pizzas.size();
    }
}
