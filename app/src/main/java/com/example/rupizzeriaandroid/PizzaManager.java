package com.example.rupizzeriaandroid;

import java.util.ArrayList;

public class PizzaManager {
    private static PizzaManager instance;
    private ArrayList<Pizza> pizzas;

    private PizzaManager() {
        pizzas = new ArrayList<>();
    }

    public static PizzaManager getInstance() {
        if (instance == null) {
            instance = new PizzaManager();
        }
        return instance;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public void clearPizzas() {
        pizzas.clear();
    }
}

