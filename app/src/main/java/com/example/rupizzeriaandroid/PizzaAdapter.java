package com.example.rupizzeriaandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private ArrayList<Pizza> pizzas;
    public PizzaAdapter(ArrayList<Pizza> pizzas) {
        this.pizzas = (pizzas == null) ? new ArrayList<>() : new ArrayList<>(pizzas);
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pizza_item, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = pizzas.get(position);
        if (pizza != null) {
            holder.nameTextView.setText(pizza.toString()); // Use pizza.toString() or specific attributes
            holder.priceTextView.setText(String.format("$%.2f", pizza.price())); // Format the price correctly
        } else {
            holder.nameTextView.setText("Unknown Pizza");
            holder.priceTextView.setText("$0.00");
        }
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    /**
     * Update the pizza list and refresh the RecyclerView.
     *
     * @param newPizzas The updated list of pizzas.
     */
    public void updatePizzas(ArrayList<Pizza> newPizzas) {
        if (newPizzas == null) {
            newPizzas = new ArrayList<>();
        }
        pizzas.clear();
        pizzas.addAll(newPizzas);
        notifyDataSetChanged();
    }

    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewPizzaName);
            priceTextView = itemView.findViewById(R.id.textViewPizzaPrice);
        }
    }
}
