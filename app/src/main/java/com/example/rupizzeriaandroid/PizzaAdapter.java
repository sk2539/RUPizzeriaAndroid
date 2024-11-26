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

    // Constructor
    public PizzaAdapter(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
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
        holder.nameTextView.setText(pizza.toString());
        holder.priceTextView.setText(String.format("$%.2f", pizza.price()));
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
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
