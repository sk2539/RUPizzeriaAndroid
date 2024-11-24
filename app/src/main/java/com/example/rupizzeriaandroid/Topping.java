package com.example.rupizzeriaandroid;

/**
 * The Topping enum represents various toppings that can be added to a pizza.
 * It includes both meat and vegetable options.
 *  @author Nithya Konduru, Dhyanashri Raman
 */
public enum Topping {
    SAUSAGE,
    PEPPERONI,
    GREENPEPPER,
    ONION,
    MUSHROOM,
    BBQCHICKEN,
    PROVOLONE,
    CHEDDAR,
    BEEF,
    HAM,
    BROCCOLI,
    SPINACH,
    JALAPENO;

    /**
     * Returns the string representation of the topping.
     * This method overrides the default `toString` implementation.
     * @return the name of the topping.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Converts a string to the corresponding Topping enum value.
     * The input string is case-insensitive.
     * @param toppingName the name of the topping.
     * @return the Topping enum value, or null if the input does not match any topping.
     * @throws IllegalArgumentException if the input is null.
     */
    public static Topping stringToTopping(String toppingName) {
        if (toppingName == null) {
            throw new IllegalArgumentException("Topping name cannot be null");
        }
        switch (toppingName.toUpperCase()) {
            case "SAUSAGE":
                return SAUSAGE;
            case "PEPPERONI":
                return PEPPERONI;
            case "GREENPEPPER":
                return GREENPEPPER;
            case "ONION":
                return ONION;
            case "MUSHROOM":
                return MUSHROOM;
            case "BBQCHICKEN":
                return BBQCHICKEN;
            case "PROVOLONE":
                return PROVOLONE;
            case "CHEDDAR":
                return CHEDDAR;
            case "BEEF":
                return BEEF;
            case "HAM":
                return HAM;
            case "BROCCOLI":
                return BROCCOLI;
            case "SPINACH":
                return SPINACH;
            case "JALAPENO":
                return JALAPENO;
            default:
                return null;
        }
    }
}
