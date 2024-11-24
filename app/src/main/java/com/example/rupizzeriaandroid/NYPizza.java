package com.example.rupizzeriaandroid;

import java.util.ArrayList;

/**
 * The NYPizza class implements the PizzaFactory interface and provides
 * methods for creating various types of pizzas specific to the New York style.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class NYPizza implements PizzaFactory {
    /**
     * Creates a Deluxe pizza with a predefined set of toppings and a Brooklyn-style crust.
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
        Pizza deluxe = new Deluxe(toppings, Crust.BROOKLYN, null);
        return deluxe;
    }

    /**
     * Creates a Deluxe pizza with a predefined set of toppings and a Brooklyn-style crust.
     * @return a Pizza object representing the Deluxe pizza.
     */
    @Override
    public Pizza createMeatzza() {
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.HAM);
        Pizza meatzza = new Meatzza(toppings, Crust.HANDTOSSED, null);
        return meatzza;
    }

    /**
     * Creates a BBQ Chicken pizza with a predefined set of toppings and a thin crust.
     * @return a Pizza object representing the BBQ Chicken pizza.
     */
    @Override
    public Pizza createBBQChicken() {
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.BBQCHICKEN);
        toppings.add(Topping.GREENPEPPER);
        toppings.add(Topping.PROVOLONE);
        toppings.add(Topping.CHEDDAR);
        Pizza bbqChicken = new BBQChicken(toppings, Crust.THIN, null);
        return bbqChicken;
    }

    /**
     * Creates a Build Your Own pizza with an empty list of toppings and a hand-tossed crust.
     * @return a Pizza object representing the Build Your Own pizza.
     */
    @Override
    public Pizza createBuildYourOwn() {
        Pizza buildYourOwn = new BuildYourOwn(null, Crust.HANDTOSSED, null);
        return buildYourOwn;
    }
}

