package com.example.rupizzeriaandroid;

/**
 * The PizzaFactory interface defines methods for creating different types of pizzas.
 * Implementing classes must provide specific implementations for each pizza type.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public interface PizzaFactory {
    /**
     * Creates a Deluxe pizza with predefined toppings, crust, and size.
     * @return a new Deluxe pizza.
     */
    Pizza createDeluxe();

    /**
     * Creates a Meatzza pizza with predefined toppings, crust, and size.
     * @return a new Meatzza pizza.
     */
    Pizza createMeatzza();

    /**
     * Creates a BBQ Chicken pizza with predefined toppings, crust, and size.
     * @return a new BBQ Chicken pizza.
     */
    Pizza createBBQChicken();

    /**
     * Creates a "Build Your Own" pizza that allows customization of toppings, crust, and size.
     * @return a new customizable pizza.
     */
    Pizza createBuildYourOwn();
}