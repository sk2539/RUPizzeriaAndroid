package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * This class represents a BBQ Chicken pizza which extends the Pizza class.
 * It includes specific pricing based on size and inherits topping, crust, and size properties.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class BBQChicken extends Pizza{
    /**
     * Creates a BBQ Chicken pizza with the specified toppings, crust type, and size.
     * @param toppings the list of toppings on the pizza.
     * @param crust the type of crust for the pizza.
     * @param size the size of the pizza.
     */
    public BBQChicken(ArrayList<Topping> toppings, Crust crust, Size size) {
        super(toppings, crust, size);
    }

    /**
     * Creates a default BBQ Chicken pizza with no parameters.
     * It does nothing until properties are set.
     */
    public BBQChicken(){}

    /**
     * Calculates and returns the price of the BBQ Chicken pizza based on its size.
     * @return the price of the pizza as a double value.
     */
    @Override
    public double price() {
        if(this.getSize().equals(Size.SMALL)){
            return 14.99;
        }
        else if(this.getSize().equals(Size.MEDIUM)){
            return 16.99;
        }
        else if(this.getSize().equals(Size.LARGE)){
            return 19.99;
        }
        return 0.00;
    }
}
