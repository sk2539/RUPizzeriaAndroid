package com.example.rupizzeriaandroid;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private ArrayList<Pizza> pizzas;
    private int selectedPosition = RecyclerView.NO_POSITION;

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
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pizza pizza = pizzas.get(position);
        if (pizza != null) {
            holder.nameTextView.setText(pizza.getClass().getSimpleName() + " - " + pizza.toString());
            holder.priceTextView.setText(String.format("$%.2f", pizza.price()));
        } else {
            holder.nameTextView.setText("Unknown Pizza");
            holder.priceTextView.setText("$0.00");
        }
        holder.itemView.setBackgroundColor(selectedPosition == position ? Color.LTGRAY : Color.TRANSPARENT);
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public void updatePizzas(ArrayList<Pizza> newPizzas) {
        if (newPizzas == null) {
            newPizzas = new ArrayList<>();
        }
        pizzas.clear();
        pizzas.addAll(newPizzas);
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public Pizza getSelectedPizza() {
        if (selectedPosition != RecyclerView.NO_POSITION && selectedPosition < pizzas.size()) {
            return pizzas.get(selectedPosition);
        }
        return null;
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