package com.example.rupizzeriaandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.Set;

/**
 * Adapter class for displaying a list of pizza toppings in a RecyclerView.
 * Handles the selection and deselection of toppings and notifies listeners of user actions.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder> {

    private final Topping[] toppings = Topping.values();
    private static final Set<Topping> selectedToppings = new HashSet<>();
    private final Context context;
    private final OnToppingClickListener onToppingClickListener;

    /**
     * Retrieves the resource ID for the image associated with a specific topping.
     *
     * @param topping The topping for which to retrieve the image resource ID.
     * @return The resource ID of the topping image.
     */
    private int getToppingImageResource(Topping topping) {
        switch (topping) {
            case MUSHROOM:
                return R.drawable.mushroom;
            case SAUSAGE:
                return R.drawable.sausage;
            case PEPPERONI:
                return R.drawable.pepperoni;
            case GREENPEPPER:
                return R.drawable.greenpepper;
            case ONION:
                return R.drawable.onion;
            case BBQCHICKEN:
                return R.drawable.chicken;
            case PROVOLONE:
                return R.drawable.provolone;
            case CHEDDAR:
                return R.drawable.cheddar;
            case BEEF:
                return R.drawable.beef;
            case HAM:
                return R.drawable.ham;
            case BROCCOLI:
                return R.drawable.broccoli;
            case SPINACH:
                return R.drawable.spinach;
            case JALAPENO:
                return R.drawable.jalapeno;
            default:
                return 0;
        }
    }

    /**
     * Interface for handling topping click events.
     * Provides the topping clicked and its selection state.
     */
    public interface OnToppingClickListener {
        void onToppingClick(Topping topping, boolean isSelected);
    }

    /**
     * Constructor for the ToppingAdapter.
     *
     * @param context  The application context.
     * @param listener The listener to handle topping click events.
     */
    public ToppingAdapter(Context context, OnToppingClickListener listener) {
        this.context = context;
        this.onToppingClickListener = listener;
    }

    /**
     * Creates a new ViewHolder for a topping item.
     * Inflates the layout and initializes the ViewHolder.
     *
     * @param parent   The parent ViewGroup into which the new view will be added.
     * @param viewType The view type of the new view (unused here).
     * @return A new ToppingViewHolder for a topping item.
     */

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.topping_item, parent, false);
        return new ToppingViewHolder(itemView);
    }

    /**
     * Binds data to a ViewHolder for a specific topping.
     * Updates the view's appearance based on the topping's selection state.
     *
     * @param holder   The ToppingViewHolder to bind data to.
     * @param position The position of the topping in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = toppings[position];
        holder.textView.setText(topping.toString());
        int imageResId = getToppingImageResource(topping);
        holder.imageView.setImageResource(imageResId);


        if (selectedToppings.contains(topping)) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.maroon));
            holder.textView.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.darkbeige));
            holder.textView.setTextColor(Color.BLACK);
        }

        holder.itemView.setOnClickListener(v -> {
            boolean isSelected = selectedToppings.contains(topping);

            if (isSelected) {
                selectedToppings.remove(topping);
                v.setBackgroundResource(R.color.darkbeige);
            } else {
                if (selectedToppings.size() < 7) {
                    selectedToppings.add(topping);
                    v.setBackgroundResource(R.color.maroon);
                } else {
                    Toast.makeText(context, "You can select up to 7 toppings only.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            notifyItemChanged(position);
            onToppingClickListener.onToppingClick(topping, !isSelected);
        });
    }


    /**
     * Returns the total number of toppings available.
     *
     * @return The number of toppings in the adapter.
     */
    @Override
    public int getItemCount() {
        return toppings.length;
    }

    /**
     * Retrieves the set of currently selected toppings.
     *
     * @return A set containing the selected toppings.
     */
    public static Set<Topping> getSelectedToppings() {
        return selectedToppings;
    }

    /**
     * ViewHolder class for caching views in the RecyclerView.
     * Provides references to the topping image and text views for efficient recycling.
     */
    static class ToppingViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        /**
         * Constructor for the ToppingViewHolder.
         *
         * @param itemView The root view of the topping item layout.
         */
        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.toppingImage);
            textView = itemView.findViewById(R.id.toppingText);
        }
    }
}
