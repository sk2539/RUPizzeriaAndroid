package com.example.rupizzeriaandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private Spinner typeSpinner;
    private Spinner pizzaSpinner;
    private ImageView pizzaImageView;
    ArrayList<Topping> toppings = new ArrayList<>();

    private int numberOfToppings = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        typeSpinner = findViewById(R.id.chooseType);
        String[] orderOptions = {"Choose an option", "Chicago Pizza", "New York Pizza"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                orderOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        pizzaSpinner = findViewById(R.id.choosePizzaType);
        String[] pizzaTypeOptions = {"Choose an option", "Deluxe", "BBQ Chicken", "Meatzza", "Build your own"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                pizzaTypeOptions
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaSpinner.setAdapter(adapter2);
        pizzaImageView = findViewById(R.id.mainImage);
        typeSpinner.setOnItemSelectedListener(createOnItemSelectedListener());
        pizzaSpinner.setOnItemSelectedListener(createOnItemSelectedListener());
        ImageButton imageButton = findViewById(R.id.homeButton);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, MainActivity.class);
            startActivity(intent);
        });
        ChipGroup chipGroup = findViewById(R.id.toppings);
        chipGroup.setEnabled(false);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnClickListener(view -> {
                if (numberOfToppings >= 7 && !chip.isSelected()) {
                    showMaxToppingsAlert();
                    return;
                }
                if (!chip.isSelected()) {
                    numberOfToppings++;
                    chip.setSelected(true);
                    toppings.add(getToppingSelected(chip.getText().toString()));
                    chip.setChipBackgroundColorResource(R.color.maroon);
                    chip.setTextColor(Color.WHITE);
                    Log.d("ToppingCount", "Number of toppings selected: " + numberOfToppings);
                } else {
                    numberOfToppings--;
                    chip.setSelected(false);
                    toppings.remove(getToppingSelected(chip.getText().toString()));
                    chip.setChipBackgroundColorResource(R.color.darkbeige);
                    chip.setTextColor(Color.BLACK);
                    Log.d("ToppingCount", "Number of toppings selected: " + numberOfToppings);
                }
            });
        }


    }

    private void showMaxToppingsAlert() {
        new AlertDialog.Builder(OrderActivity.this)
                .setTitle("Maximum Toppings Reached")
                .setMessage("You can only select up to 7 toppings.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private Topping getToppingSelected(String toppingName) {
        switch (toppingName.toUpperCase()) {
            case "SAUSAGE":
                return Topping.SAUSAGE;
            case "PEPPERONI":
                return Topping.PEPPERONI;
            case "GREENPEPPER":
                return Topping.GREENPEPPER;
            case "ONION":
                return Topping.ONION;
            case "MUSHROOM":
                return Topping.MUSHROOM;
            case "BBQCHICKEN":
                return Topping.BBQCHICKEN;
            case "PROVOLONE":
                return Topping.PROVOLONE;
            case "CHEDDAR":
                return Topping.CHEDDAR;
            case "BEEF":
                return Topping.BEEF;
            case "HAM":
                return Topping.HAM;
            case "BROCCOLI":
                return Topping.BROCCOLI;
            case "SPINACH":
                return Topping.SPINACH;
            case "JALAPENO":
                return Topping.JALAPENO;
            default:
                return null;
        }
    }

    /**
     * Updates the image based on the current selections of both spinners.
     */
    private void updatePizzaImage() {
        int typePosition = typeSpinner.getSelectedItemPosition();
        int pizzaPosition = pizzaSpinner.getSelectedItemPosition();

        if (typePosition == 0) { // No pizza style selected
            pizzaImageView.setImageDrawable(null);
            Toast.makeText(this, "Please select a pizza style", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pizzaPosition == 0) { // No pizza type selected
            pizzaImageView.setImageDrawable(null);
            Toast.makeText(this, "Please select a pizza type", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isChicagoStyle = (typePosition == 1); // Chicago is at index 1

        ChipGroup chipGroup = findViewById(R.id.toppings);

        // Update the image based on the current selections
        switch (pizzaPosition) {
            case 1: // Deluxe
                pizzaImageView.setImageResource(isChicagoStyle ? R.drawable.chicagodeluxepizza : R.drawable.nydeluxe);
                clearAllSelections();
                disableChips(false);
                selectTopping(Topping.SAUSAGE);
                toppings.add(Topping.SAUSAGE);
                selectTopping(Topping.PEPPERONI);
                toppings.add(Topping.PEPPERONI);
                selectTopping(Topping.GREENPEPPER);
                toppings.add(Topping.GREENPEPPER);
                selectTopping(Topping.ONION);
                toppings.add(Topping.ONION);
                selectTopping(Topping.MUSHROOM);
                toppings.add(Topping.MUSHROOM);
                break;
            case 2: // BBQ Chicken
                pizzaImageView.setImageResource(isChicagoStyle ? R.drawable.chicagobbqchicken : R.drawable.nybbqchicken);
                clearAllSelections();
                disableChips(false);
                selectTopping(Topping.BBQCHICKEN);
                toppings.add(Topping.BBQCHICKEN);
                selectTopping(Topping.GREENPEPPER);
                toppings.add(Topping.GREENPEPPER);
                selectTopping(Topping.PROVOLONE);
                toppings.add(Topping.PROVOLONE);
                selectTopping(Topping.CHEDDAR);
                toppings.add(Topping.CHEDDAR);
                break;
            case 3: // Meatzza
                pizzaImageView.setImageResource(isChicagoStyle ? R.drawable.chicagomeatzza : R.drawable.nymeattza);
                clearAllSelections();
                disableChips(false);
                selectTopping(Topping.SAUSAGE);
                toppings.add(Topping.SAUSAGE);
                toppings.add(Topping.PEPPERONI);
                selectTopping(Topping.GREENPEPPER);
                selectTopping(Topping.BEEF);
                toppings.add(Topping.BEEF);
                selectTopping(Topping.HAM);
                toppings.add(Topping.HAM);
                break;
            case 4: // Build Your Own
                pizzaImageView.setImageResource(isChicagoStyle ? R.drawable.buildyourownpizza : R.drawable.buildyourownpizza);
                enableChips(true);
                clearAllSelections();
                break;
            default:
                pizzaImageView.setImageDrawable(null);
                break;
        }
        chipGroup.setEnabled(false);
    }

    private void disableChips(boolean enable) {
        ChipGroup chipGroup = findViewById(R.id.toppings);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setEnabled(enable);
        }
    }

    private void enableChips(boolean enable) {
        ChipGroup chipGroup = findViewById(R.id.toppings);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setEnabled(enable);
        }
    }

    private void clearAllSelections() {
        ChipGroup chipGroup = findViewById(R.id.toppings);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setSelected(false);
            chip.setChipBackgroundColorResource(R.color.darkbeige);
            chip.setTextColor(Color.BLACK);
        }

        // Clear the toppings list
        toppings.clear();
        numberOfToppings = 0;
        Log.d("Toppings", "Cleared all selections: " + toppings.toString());
    }


    private void selectTopping(Topping topping) {
        ChipGroup chipGroup = findViewById(R.id.toppings);
        toppings.add(topping);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.getText().toString().equalsIgnoreCase(topping.name())) {
                chip.setSelected(true);
                chip.setChipBackgroundColorResource(R.color.maroon);
                chip.setTextColor(Color.WHITE);
            }
        }
    }


    /**
     * Creates a listener for both spinners to trigger image updates.
     *
     * @return The listener.
     */
    private AdapterView.OnItemSelectedListener createOnItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePizzaImage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(OrderActivity.this, "Please make a selection", Toast.LENGTH_SHORT).show();
            }
        };
    }


}