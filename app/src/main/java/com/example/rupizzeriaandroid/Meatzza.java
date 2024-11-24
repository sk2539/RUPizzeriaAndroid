package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * The Meatzza class represents a type of pizza with a variety of meat toppings.
 * This class extends the Pizza class and provides specific pricing logic for the Meatzza pizza.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class Meatzza extends Pizza{
    /**
     * Constructs a Meatzza pizza with the specified toppings, crust, and size.
     * @param arr the list of toppings for the pizza.
     * @param crust the type of crust for the pizza.
     * @param size the size of the pizza.
     */
    public Meatzza(ArrayList<Topping> arr, Crust crust, Size size) {
        super(arr, crust, size);
    }

    /**
     * Default constructor for the Meatzza class.
     * Creates a Meatzza pizza with no toppings, crust, or size specified.
     */
    public Meatzza(){}

    /**
     * Calculates the price of the Meatzza pizza based on its size.
     * @return the price of the pizza as a double.
     */
    @Override
    public double price() {
        if(this.getSize().equals(Size.SMALL)){
            return 17.99;
        }
        else if(this.getSize().equals(Size.MEDIUM)){
            return 19.99;
        }
        else if(this.getSize().equals(Size.LARGE)){
            return 21.99;
        }
        return 0.00;
    }
}