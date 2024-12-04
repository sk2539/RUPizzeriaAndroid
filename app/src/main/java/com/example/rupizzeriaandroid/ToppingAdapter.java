package com.example.rupizzeriaandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView textView = new TextView(context);
        textView.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        ));
        textView.setPadding(16, 16, 16, 16);
        return new ToppingViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = toppings[position];
        holder.textView.setText(topping.toString());

        // Update selection state
        if (selectedToppings.contains(topping)) {
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.maroon));
            holder.textView.setTextColor(Color.WHITE);
        } else {
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.darkbeige));
            holder.textView.setTextColor(Color.BLACK);
        }

        // Handle topping click
        holder.textView.setOnClickListener(v -> {
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

    @Override
    public int getItemCount() {
        return toppings.length;
    }

    static class ToppingViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    public Set<Topping> getSelectedToppings() {
        return selectedToppings;
    }
}
