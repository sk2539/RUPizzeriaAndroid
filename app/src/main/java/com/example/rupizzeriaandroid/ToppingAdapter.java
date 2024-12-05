package com.example.rupizzeriaandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.Set;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder> {

    private final Topping[] toppings = Topping.values();
    private final Set<Topping> selectedToppings = new HashSet<>();
    private final Context context;
    private final OnToppingClickListener onToppingClickListener;

    public interface OnToppingClickListener {
        void onToppingClick(Topping topping, boolean isSelected);
    }

    public ToppingAdapter(Context context, OnToppingClickListener listener) {
        this.context = context;
        this.onToppingClickListener = listener;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.topping_item, parent, false);
        return new ToppingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = toppings[position];
        holder.textView.setText(topping.toString());

        // Set the image for the topping
        int imageResId = getToppingImageResource(topping);
        holder.imageView.setImageResource(imageResId);

        // Update selection state
        if (selectedToppings.contains(topping)) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.maroon));
            holder.textView.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.darkbeige));
            holder.textView.setTextColor(Color.BLACK);
        }

        // Handle topping click
        holder.itemView.setOnClickListener(v -> {
            boolean isSelected = !selectedToppings.contains(topping);
            if (isSelected) {
                selectedToppings.add(topping);
            } else {
                selectedToppings.remove(topping);
            }
            onToppingClickListener.onToppingClick(topping, isSelected);
            notifyItemChanged(position);
        });
    }

    private int getToppingImageResource(Topping topping) {
        switch (topping) {
            case MUSHROOM:
                return R.drawable.mushroomtopping;
            case SAUSAGE:
                return R.drawable.sausagetopping;
            case PEPPERONI:
                return R.drawable.pepperonitopping;
            case GREENPEPPER:
                return R.drawable.greenpeppertopping;
            case ONION:
                return R.drawable.oniontopping;
            case BBQCHICKEN:
                return R.drawable.chickentopping;
            case PROVOLONE:
                return R.drawable.provolonetopping;
            case CHEDDAR:
                return R.drawable.cheddartopping;
            case BEEF:
                return R.drawable.beeftopping;
            case HAM:
                return R.drawable.hamtopping;
            case BROCCOLI:
                return R.drawable.broccolitopping;
            case SPINACH:
                return R.drawable.spinachtopping;
            case JALAPENO:
                return R.drawable.jalapenotopping;
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return toppings.length;
    }

    static class ToppingViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.toppingImage);
            textView = itemView.findViewById(R.id.toppingText);
        }
    }

    public Set<Topping> getSelectedToppings() {
        return selectedToppings;
    }
}
