package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * The Pizza class represents a general pizza with attributes for toppings, crust type, and size.
 * This is an abstract class that serves as a base for specific pizza types.
 * It provides common methods for managing pizza attributes and enforces implementation of the price calculation.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public abstract class Pizza {
    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;

    /**
     * Abstract method to calculate the price of the pizza.
     * Subclasses must provide their own implementation.
     * @return the price of the pizza.
     */
    public abstract double price();

    /**
     * Default constructor for the Pizza class.
     * Initializes a pizza with no toppings, crust, or size.
     */
    public Pizza(){}

    /**
     * Constructs a Pizza object with the specified toppings, crust, and size.
     * @param toppings the list of toppings for the pizza.
     * @param crust the type of crust for the pizza.
     * @param size the size of the pizza.
     */
    public Pizza(ArrayList<Topping> toppings, Crust crust, Size size){
        this.toppings = toppings;
        this.crust = crust;
        this.size = size;
    }

    /**
     * Gets the list of toppings on the pizza.
     * @return the list of toppings.
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Gets the crust type of the pizza.
     * @return the crust type.
     */
    public Crust getCrust(){
        return crust;
    }

    /**
     * Gets the size of the pizza.
     * @return the size of the pizza.
     */
    public Size getSize(){
        return size;
    }

    /**
     * Sets the list of toppings for the pizza.
     * @param toppings the list of toppings to set.
     */
    public void setToppings(ArrayList<Topping> toppings){
        this.toppings = toppings;
    }

    /**
     * Sets the size of the pizza.
     * @param size the size to set.
     */
    public void setSize(Size size){
        this.size = size;
    }

    /**
     * Sets the crust type of the pizza.
     * @param crust the crust type to set.
     */
    public void setCrust(Crust crust){
        this.crust = crust;
    }

    /**
     * Compares this pizza with another object for equality.
     * Two pizzas are considered equal if they have the same size, crust, and sorted list of toppings.
     * @param obj the object to compare with.
     * @return true if the pizzas are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if(obj instanceof Pizza){
            Pizza pizza = (Pizza) obj;
            boolean isEqual = true;
            if(this.toppings.size() != pizza.toppings.size()){
                return false;
            }
            if (!this.size.equals(pizza.size) || !this.crust.equals(pizza.crust)) {
                return false;
            }
            this.toppings.sort(null);
            pizza.toppings.sort(null);
            for(int i = 0; i<this.toppings.size(); i++){
                if(this.toppings.get(i).equals(pizza.toppings.get(i))){
                    isEqual = false;
                }
            }
            return isEqual;
        }
        return false;
    }

    /**
     * Returns a string representation of the pizza object, including its size, crust, and toppings.
     * @return a string describing the pizza.
     */
    @Override
    public String toString() {
        String toppingsString = getToppings().isEmpty()
                ? "No toppings"
                : String.join(", ", getToppings().stream()
                .map(Topping::toString)
                .toArray(String[]::new));
        String pizzaType;
        if (getCrust() == Crust.DEEPDISH || getCrust() == Crust.PAN || getCrust() == Crust.STUFFED) {
            pizzaType = "Chicago Pizza";
        } else if (getCrust() == Crust.BROOKLYN || getCrust() == Crust.THIN || getCrust() == Crust.HANDTOSSED) {
            pizzaType = "New York Pizza";
        } else {
            pizzaType = "Unknown Pizza Type";
        }
        return String.format("%s [Size=%s, Crust=%s, Toppings=%s]",
                pizzaType,
                getSize().toString(),
                getCrust(),
                toppingsString);
    }
}
