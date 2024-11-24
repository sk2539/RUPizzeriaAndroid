package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * The ChicagoPizza class implements the PizzaFactory interface and provides
 * methods for creating various types of pizzas specific to the Chicago style.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class ChicagoPizza implements PizzaFactory{
    /**
     * Creates a Deluxe pizza with a predefined set of toppings and a deep-dish crust.
     * @return a Pizza object representing the Deluxe pizza.
     */
    @Override
    public Pizza createDeluxe() {
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.GREENPEPPER);
        toppings.add(Topping.ONION);
        toppings.add(Topping.MUSHROOM);
        Pizza deluxe = new Deluxe(toppings, Crust.DEEPDISH, null);
        return deluxe;
    }

    /**
     * Creates a Meatzza pizza with a predefined set of meat toppings and a stuffed crust.
     * @return a Pizza object representing the Meatzza pizza.
     */
    @Override
    public Pizza createMeatzza() {
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.HAM);
        Pizza meatzza = new Meatzza(toppings, Crust.STUFFED, null);
        return meatzza;
    }

    /**
     * Creates a BBQ Chicken pizza with a predefined set of toppings and a pan crust.
     * @return a Pizza object representing the BBQ Chicken pizza.
     */
    @Override
    public Pizza createBBQChicken() {
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.BBQCHICKEN);
        toppings.add(Topping.GREENPEPPER);
        toppings.add(Topping.PROVOLONE);
        toppings.add(Topping.CHEDDAR);
        Pizza bbqChicken = new BBQChicken(toppings, Crust.PAN, null);
        return bbqChicken;
    }

    /**
     * Creates a Build Your Own pizza with an empty list of toppings and a pan crust.
     * @return a Pizza object representing the Build Your Own pizza.
     */
    @Override
    public Pizza createBuildYourOwn() {
        Pizza buildYourOwn = new BuildYourOwn(null, Crust.PAN, null);
        return buildYourOwn;
    }
}

