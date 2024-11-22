package com.example.rupizzeriaandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderActivity extends AppCompatActivity {

    private boolean isChicagoStyle = true;
    private Spinner typeSpinner;
    private Spinner pizzaSpinner;
    private ImageView pizzaImageView;

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

        // Add listeners to both spinners
        typeSpinner.setOnItemSelectedListener(createOnItemSelectedListener());
        pizzaSpinner.setOnItemSelectedListener(createOnItemSelectedListener());
    }

    /**
     * Updates the image based on the current selections of both spinners.
     */
    private void updatePizzaImage() {
        int typePosition = typeSpinner.getSelectedItemPosition();
        int pizzaPosition = pizzaSpinner.getSelectedItemPosition();

        if (typePosition == 0) { // No pizza style selected
            Toast.makeText(this, "Please select a pizza style", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pizzaPosition == 0) { // No pizza type selected
            Toast.makeText(this, "Please select a pizza type", Toast.LENGTH_SHORT).show();
            return;
        }

        isChicagoStyle = (typePosition == 1); // Chicago is at index 1

        // Update the image based on the current selections
        switch (pizzaPosition) {
            case 1: // Deluxe
                pizzaImageView.setImageResource(isChicagoStyle ? R.drawable.chicagodeluxepizza : R.drawable.nydeluxe);
                break;
            case 2: // BBQ Chicken
                pizzaImageView.setImageResource(isChicagoStyle ? R.drawable.chicagobbqchicken : R.drawable.nybbqchicken);
                break;
            case 3: // Meatzza
                pizzaImageView.setImageResource(isChicagoStyle ? R.drawable.chicagomeatzza : R.drawable.nymeattza);
                break;
            case 4: // Build Your Own
                pizzaImageView.setImageResource(isChicagoStyle ? R.drawable.buildyourownpizza : R.drawable.buildyourownpizza);
                break;
            default:
                break;
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