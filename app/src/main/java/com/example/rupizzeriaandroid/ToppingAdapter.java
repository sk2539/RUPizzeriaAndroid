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

        });
    }

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
