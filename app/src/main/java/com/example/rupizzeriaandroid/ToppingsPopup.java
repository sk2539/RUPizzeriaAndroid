package com.example.rupizzeriaandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToppingsPopup extends DialogFragment {

    private List<Topping> selectedToppings = new ArrayList<>();
    private ToppingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toppingspopup, container, false);
        RecyclerView toppingsList = view.findViewById(R.id.toppingsList);
        Button closeButton = view.findViewById(R.id.closeButton);
        toppingsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ToppingAdapter(getContext(), (topping, isSelected) -> {
            if (isSelected) {
                if (selectedToppings.size() >= 7) {
                    showMaxToppingsAlert();
                    return;
                }
                selectedToppings.add(topping);
            } else {
                selectedToppings.remove(topping);
            }
        });
        toppingsList.setAdapter(adapter);
        closeButton.setOnClickListener(v -> dismiss());
        return view;
    }

    private void showMaxToppingsAlert() {
        ;
    }
}
