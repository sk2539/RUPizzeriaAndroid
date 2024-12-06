package com.example.rupizzeriaandroid;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class for managing and displaying a list of pizzas in a RecyclerView.
 * Handles the binding of pizza data to views and managing item selection.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private ArrayList<Pizza> pizzas;
    private int selectedPosition = RecyclerView.NO_POSITION;

    private int getPizzaImageResource(Pizza pizza) {
        if (pizza.toString().contains("Chicago Pizza")) {
            if (pizza instanceof Deluxe) {
                return R.drawable.chicagodeluxepizza;
            } else if (pizza instanceof BBQChicken) {
                return R.drawable.chicagobbqchicken;
            } else if (pizza instanceof Meatzza) {
                return R.drawable.chicagomeatzza;
            }
            else if (pizza instanceof BuildYourOwn) {
                return R.drawable.buildyourownpizza;
            }
        }
        else if (pizza.toString().contains("New York Pizza")) {
            if (pizza instanceof Deluxe) {
                return R.drawable.nydeluxe;
            } else if (pizza instanceof BBQChicken) {
                return R.drawable.nybbqchicken;
            } else if (pizza instanceof Meatzza) {
                return R.drawable.nymeattza;
            }
            else if (pizza instanceof BuildYourOwn) {
                return R.drawable.buildyourownpizza;
            }
        }
        return 0;
    }

    /**
     * Constructor for the PizzaAdapter.
     * Initializes the adapter with a list of pizzas.
     *
     * @param pizzas The list of pizzas to display. If null, an empty list is used.
     */
    public PizzaAdapter(ArrayList<Pizza> pizzas) {
        this.pizzas = (pizzas == null) ? new ArrayList<>() : new ArrayList<>(pizzas);
    }

    /**
     * Called when a new view holder is needed to represent an item.
     * Inflates the layout for a pizza item and returns the associated view holder.
     *
     * @param parent   The parent ViewGroup into which the new view will be added.
     * @param viewType The view type of the new view.
     * @return A new PizzaViewHolder for the pizza item.
     */
    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pizza_item, parent, false);
        return new PizzaViewHolder(view);
    }

    /**
     * Called to bind data to a view holder.
     * Populates the view holder with the pizza's details and handles item selection.
     *
     * @param holder   The PizzaViewHolder to bind data to.
     * @param position The position of the item in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pizza pizza = pizzas.get(position);
        if (pizza != null) {
            holder.nameTextView.setText(pizza.getClass().getSimpleName() + " - " + pizza.toString());
            holder.priceTextView.setText(String.format("$%.2f", pizza.price()));
            int imageResId = getPizzaImageResource(pizza);  // Get image resource ID for the pizza
            holder.pizzaImage.setImageResource(imageResId);
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

    /**
     * Returns the total number of items in the list.
     *
     * @return The number of pizzas in the adapter.
     */
    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    /**
     * Updates the list of pizzas and notifies the adapter to refresh the RecyclerView.
     *
     * @param newPizzas The new list of pizzas to display.
     */
    public void updatePizzas(ArrayList<Pizza> newPizzas) {
        if (newPizzas == null) {
            newPizzas = new ArrayList<>();
        }
        pizzas.clear();
        pizzas.addAll(newPizzas);
        notifyDataSetChanged();
    }

    /**
     * Retrieves the currently selected position in the RecyclerView.
     *
     * @return The index of the selected item, or NO_POSITION if no item is selected.
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * Retrieves the currently selected pizza.
     *
     * @return The selected Pizza object, or null if no item is selected.
     */
    public Pizza getSelectedPizza() {
        if (selectedPosition != RecyclerView.NO_POSITION && selectedPosition < pizzas.size()) {
            return pizzas.get(selectedPosition);
        }
        return null;
    }

    /**
     * ViewHolder class for caching views in the RecyclerView.
     * Provides references to the pizza name and price text views for efficient recycling.
     */
    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        ImageView pizzaImage;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewPizzaName);
            priceTextView = itemView.findViewById(R.id.textViewPizzaPrice);
            pizzaImage = itemView.findViewById(R.id.pizzaImage);
        }
    }
}