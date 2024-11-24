package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * The Order class represents a customer's pizza order, containing an order number
 * and a list of pizzas in the order. Provides methods for managing the pizzas in the order.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class Order {
    private int number;
    private ArrayList<Pizza> pizzas;

    /**
     * Constructs an Order object with the specified order number and a list of pizzas.
     * @param number the unique order number.
     * @param pizzas the list of pizzas in the order.
     */
    public Order(int number, ArrayList<Pizza> pizzas){
        this.number = number;
        this.pizzas = pizzas;
    }

    /**
     * Constructs an Order object with the specified order number and a single pizza.
     * Initializes the list of pizzas and adds the given pizza to it.
     * @param number the unique order number.
     * @param pizza a single pizza to add to the order.
     */
    public Order(int number, Pizza pizza) {
        this.number = number;
        this.pizzas = new ArrayList<>();
        if (pizza != null) {
            this.pizzas.add(pizza);
        }
    }

    /**
     * Sets the order number.
     * @param num the order number to set.
     */
    public void setOrderNum(int num){
        this.number = num;
    }

    /**
     * Gets the order number.
     * @return the order number.
     */
    public int getOrderNum(){
        return number;
    }

    /**
     * Gets the list of pizzas in the order.
     * @return the list of pizzas.
     */
    public ArrayList<Pizza> getOrder(){
        return pizzas;
    }

    /**
     * Adds a pizza to the order.
     * @param pizza the pizza to add.
     */
    public void addPizza(Pizza pizza){
        pizzas.add(pizza);
    }

    /**
     * Removes the pizza at the specified index from the order.
     * @param i the index of the pizza to remove.
     */
    public void removeithPizza(int i){
        pizzas.remove(i);
    }

    /**
     * Removes a specific pizza from the order. If multiple pizzas match, only the first is removed.
     * @param pizza the pizza to remove.
     */
    public void removePizza(Pizza pizza){
        for(int i = 0; i<pizzas.size(); i++){
            if(pizzas.get(i).equals(pizza)){
                pizzas.remove(i);
            }
        }
    }
}

