package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * This class represents a Build Your Own pizza which extends Pizza class.
 * It allows customers to select their own toppings and calculates price based on size and number of toppings.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class BuildYourOwn extends Pizza{
    /**
     * Creates a Build Your Own pizza with specified toppings, crust type and size.
     * @param arr the list of toppings selected for the pizza
     * @param crust the type of crust for the pizza
     * @param size the size of the pizza
     */
    public BuildYourOwn(ArrayList<Topping> arr, Crust crust, Size size) {
        super(arr, crust, size);
    }

    /**
     * Creates a default Build Your Own pizza with no parameters.
     * It does nothing until properties are set.
     */
    public BuildYourOwn(){}


    /**
     * Calculates and returns the price of Build Your Own pizza based on size and number of toppings.
     * Base price varies by size and each topping costs additional $1.69 up to maximum of 7 toppings.
     * @return the total price of the pizza as a double value
     */
    @Override
    public double price() {
        double price = 0.0;
        if(this.getSize().equals(Size.SMALL)){
            price += 8.99;
        }
        else if(this.getSize().equals(Size.MEDIUM)){
            price += 10.99;
        }
        else if(this.getSize().equals(Size.LARGE)){
            price += 12.99;
        }
        if(this.getToppings()!=null && this.getToppings().size() <= 7){
            price += (1.69 * this.getToppings().size());
        }
        price = (int)(price * 100) / 100.0;
        return price;
    }
}
