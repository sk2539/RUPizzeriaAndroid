package com.example.rupizzeriaandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * DialogFragment class for displaying a popup to select pizza toppings.
 * Provides a list of available toppings and handles user interactions for selection.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class ToppingsPopup extends DialogFragment {

    private static List<Topping> selectedToppings = new ArrayList<>();
    private ToppingAdapter adapter;

    /**
     * Called to create the view hierarchy for this fragment.
     * Inflates the layout and initializes the RecyclerView and its adapter.
     *
     * @param inflater  LayoutInflater object used to inflate the fragment's view.
     * @param container The parent view group into which the fragment's view will be added.
     * @param savedInstanceState If non-null, this fragment is being re-created from a previous saved state.
     * @return The created view for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toppingspopup, container, false);
        RecyclerView toppingsList = view.findViewById(R.id.toppingsList);
        Button closeButton = view.findViewById(R.id.closeButton);
        Button selectToppings = view.findViewById(R.id.selectToppings);
        toppingsList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ToppingAdapter(getContext(), (topping, isSelected) -> {
            if (isSelected) {
                if (selectedToppings.size() < 7) {
                    selectedToppings.add(topping);
                } else {
                    Toast.makeText(getContext(), "You can select up to 7 toppings only.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                selectedToppings.remove(topping);
            }
        });
        toppingsList.setAdapter(adapter);

        closeButton.setOnClickListener(v -> dismiss());
        selectToppings.setOnClickListener(v -> {
            if (listener != null) {
                listener.onToppingsSelected(new ArrayList<>(selectedToppings));
            }
            dismiss();
        });

        return view;
    }

    public interface OnToppingsSelectedListener {
        void onToppingsSelected(List<Topping> selectedToppings);
    }

    private OnToppingsSelectedListener listener;

    public void setOnToppingsSelectedListener(OnToppingsSelectedListener listener) {
        this.listener = listener;
    }

    public static List<Topping> getSelectedToppings() {
        return selectedToppings;
    }

}
