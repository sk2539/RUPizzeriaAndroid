package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * The Deluxe class represents a type of pizza with a predefined set of premium toppings.
 * This class extends the Pizza class and provides specific pricing logic for the Deluxe pizza.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class Deluxe extends Pizza{
    /**
     * Constructs a Deluxe pizza with the specified toppings, crust, and size.
     * @param arr the list of toppings for the pizza.
     * @param crust the type of crust for the pizza.
     * @param size the size of the pizza.
     */
    public Deluxe(ArrayList<Topping> arr, Crust crust, Size size) {
        super(arr, crust, size);
    }

    /**
     * Default constructor for the Deluxe class.
     * Creates a Deluxe pizza with no toppings, crust, or size specified.
     */
    public Deluxe(){}

    /**
     * Calculates the price of the Deluxe pizza based on its size.
     * @return the price of the pizza as a double.
     */
    @Override
    public double price() {
        if(this.getSize().equals(Size.SMALL)){
            return 16.99;
        }
        else if(this.getSize().equals(Size.MEDIUM)){
            return 18.99;
        }
        else if(this.getSize().equals(Size.LARGE)){
            return 20.99;
        }
        return 0.00;
    }
}
