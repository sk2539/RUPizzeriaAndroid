package com.example.rupizzeriaandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private Spinner typeSpinner;
    private Spinner pizzaSpinner;
    private ImageView pizzaImageView;

    private List<Topping> selectedToppings = new ArrayList<>();
    private int numberOfToppings = 0;

    private ImageView sausageLayer, pepperoniLayer, greenpepperLayer, onionLayer,
            mushroomLayer, chickenLayer, provoloneLayer, cheddarLayer,
            beefLayer, hamLayer, broccoliLayer, spinachLayer, jalapenoLayer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        // Window insets for modern UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        typeSpinner = findViewById(R.id.chooseType);
        pizzaSpinner = findViewById(R.id.choosePizzaType);
        pizzaImageView = findViewById(R.id.mainImage);

        setupSpinners();

        // Home button
        ImageButton imageButton = findViewById(R.id.homeButton);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Shopping cart button
        ImageButton shoppingCart = findViewById(R.id.shoppingCart);
        shoppingCart.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        });

        // Add to order button
        Button addToOrderButton = findViewById(R.id.addToOrderButton);
        addToOrderButton.setOnClickListener(v -> onAddToOrderClick());

        // Trigger ToppingsPopup
        Button selectToppingsButton = findViewById(R.id.SelectToppingsButton); // Add this button in activity_order.xml
        selectToppingsButton.setOnClickListener(v -> {
            ToppingsPopup toppingsPopup = new ToppingsPopup();
            toppingsPopup.show(getSupportFragmentManager(), "ToppingsPopup");
        });

        // Initialize pizza layers
        initializeLayers();
    }

    private void setupSpinners() {
        String[] orderOptions = {"Select pizza style", "Chicago Pizza", "New York Pizza"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, orderOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        String[] pizzaTypeOptions = {"Select pizza type", "Deluxe", "BBQ Chicken", "Meatzza", "Build your own"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, pizzaTypeOptions
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaSpinner.setAdapter(adapter2);

        typeSpinner.setOnItemSelectedListener(createOnItemSelectedListener());
        pizzaSpinner.setOnItemSelectedListener(createOnItemSelectedListener());
    }

    private void initializeLayers() {
        sausageLayer = findViewById(R.id.sausageLayer);
        pepperoniLayer = findViewById(R.id.pepperoniLayer);
        greenpepperLayer = findViewById(R.id.greenPepperLayer);
        onionLayer = findViewById(R.id.onionLayer);
        mushroomLayer = findViewById(R.id.mushroomLayer);
        chickenLayer = findViewById(R.id.chickenLayer);
        provoloneLayer = findViewById(R.id.provoloneLayer);
        cheddarLayer = findViewById(R.id.cheddarLayer);
        beefLayer = findViewById(R.id.beefLayer);
        hamLayer = findViewById(R.id.hamLayer);
        broccoliLayer = findViewById(R.id.broccoliLayer);
        spinachLayer = findViewById(R.id.spinachLayer);
        jalapenoLayer = findViewById(R.id.jalapenoLayer);
    }

    private void setImageforBYO(String topping) {
        switch (topping) {
            case "Sausage":
                sausageLayer.setImageResource(R.drawable.sausagetopping);
                break;
            case "Pepperoni":
                pepperoniLayer.setImageResource(R.drawable.pepperonitopping);
                break;
            case "Greenpepper":
                greenpepperLayer.setImageResource(R.drawable.greenpeppertopping);
                break;
            case "Onion":
                onionLayer.setImageResource(R.drawable.oniontopping);
                break;
            case "Mushroom":
                mushroomLayer.setImageResource(R.drawable.mushroomtopping);
                break;
            case "BBQ Chicken":
                chickenLayer.setImageResource(R.drawable.chickentopping);
                break;
            case "Provolone":
                provoloneLayer.setImageResource(R.drawable.provolonetopping);
                break;
            case "Cheddar":
                cheddarLayer.setImageResource(R.drawable.cheddartopping);
                break;
            case "Beef":
                beefLayer.setImageResource(R.drawable.beeftopping);
                break;
            case "Ham":
                hamLayer.setImageResource(R.drawable.hamtopping);
                break;
            case "Broccoli":
                broccoliLayer.setImageResource(R.drawable.broccolitopping);
                break;
            case "Spinach":
                spinachLayer.setImageResource(R.drawable.spinachtopping);
                break;
            case "Jalapeno":
                jalapenoLayer.setImageResource(R.drawable.jalapenotopping);
                break;
        }
    }

    private void removeImageForBYO(String topping) {
        switch (topping) {
            case "Sausage":
                sausageLayer.setImageResource(0);
                break;
            case "Pepperoni":
                pepperoniLayer.setImageResource(0);
                break;
            case "Greenpepper":
                greenpepperLayer.setImageResource(0);
                break;
            case "Onion":
                onionLayer.setImageResource(0);
                break;
            case "Mushroom":
                mushroomLayer.setImageResource(0);
                break;
            case "BBQ Chicken":
                chickenLayer.setImageResource(0);
                break;
            case "Provolone":
                provoloneLayer.setImageResource(0);
                break;
            case "Cheddar":
                cheddarLayer.setImageResource(0);
                break;
            case "Beef":
                beefLayer.setImageResource(0);
                break;
            case "Ham":
                hamLayer.setImageResource(0);
                break;
            case "Broccoli":
                broccoliLayer.setImageResource(0);
                break;
            case "Spinach":
                spinachLayer.setImageResource(0);
                break;
            case "Jalapeno":
                jalapenoLayer.setImageResource(0);
                break;
        }
    }

    private void clearAllToppingsImages() {
        sausageLayer.setImageResource(0);
        pepperoniLayer.setImageResource(0);
        greenpepperLayer.setImageResource(0);
        onionLayer.setImageResource(0);
        mushroomLayer.setImageResource(0);
        chickenLayer.setImageResource(0);
        provoloneLayer.setImageResource(0);
        cheddarLayer.setImageResource(0);
        beefLayer.setImageResource(0);
        hamLayer.setImageResource(0);
        broccoliLayer.setImageResource(0);
        spinachLayer.setImageResource(0);
        jalapenoLayer.setImageResource(0);
        numberOfToppings = 0;
    }

    private void showMaxToppingsAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Maximum Toppings Reached")
                .setMessage("You can only select up to 7 toppings.")
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss())
                .show();
    }

    private void recalculatePrice() {
        EditText priceText = findViewById(R.id.priceText);
        if (typeSpinner.getSelectedItemPosition() == 0 || pizzaSpinner.getSelectedItemPosition() == 0) {
            priceText.setText("");
            return;
        }

        double basePrice = 10.0; // Example base price
        basePrice += selectedToppings.size() * 1.5; // Example topping price
        priceText.setText(String.format(Locale.getDefault(), "$%.2f", basePrice));
    }

    private void onAddToOrderClick() {
        if (typeSpinner.getSelectedItemPosition() == 0 || pizzaSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select pizza style and type.", Toast.LENGTH_SHORT).show();
            return;
        }
        Pizza pizza = createPizza();
        if (pizza != null) {
            PizzaManager.getInstance().addPizza(pizza);
            Toast.makeText(this, "Pizza added to order successfully!", Toast.LENGTH_SHORT).show();
            clearAllToppingsImages();
        }
    }

    private Pizza createPizza() {
        boolean isChicagoStyle = typeSpinner.getSelectedItemPosition() == 1;
        PizzaFactory pizzaFactory = isChicagoStyle ? new ChicagoPizza() : new NYPizza();
        Pizza pizza = null;

        switch (pizzaSpinner.getSelectedItemPosition()) {
            case 1:
                pizza = pizzaFactory.createDeluxe();
                break;
            case 2:
                pizza = pizzaFactory.createBBQChicken();
                break;
            case 3:
                pizza = pizzaFactory.createMeatzza();
                break;
            case 4:
                pizza = pizzaFactory.createBuildYourOwn();
                pizza.setToppings(new ArrayList<>(selectedToppings));
                break;
        }

        if (pizza != null) {
            pizza.setSize(getSizeUI());
        }
        return pizza;
    }

    private Size getSizeUI() {
        RadioGroup radioGroupSize = findViewById(R.id.radioGroupSize);
        int checkedId = radioGroupSize.getCheckedRadioButtonId();
        if (checkedId != -1) {
            RadioButton selectedButton = findViewById(checkedId);
            return Size.valueOf(selectedButton.getText().toString().toUpperCase());
        }
        return null;
    }

    private AdapterView.OnItemSelectedListener createOnItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePizzaImage();
                recalculatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(OrderActivity.this, "Please make a selection.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void updatePizzaImage() {
        int typePosition = typeSpinner.getSelectedItemPosition();
        int pizzaPosition = pizzaSpinner.getSelectedItemPosition();

        if (typePosition == 1 && pizzaPosition == 1) {
            pizzaImageView.setImageResource(R.drawable.chicagodeluxepizza);
        } else if (typePosition == 2 && pizzaPosition == 1) {
            pizzaImageView.setImageResource(R.drawable.nydeluxe);
        } else if (typePosition == 1 && pizzaPosition == 2) {
            pizzaImageView.setImageResource(R.drawable.chicagobbqchicken);
        } else if (typePosition == 2 && pizzaPosition == 2) {
            pizzaImageView.setImageResource(R.drawable.nybbqchicken);
        } else if (typePosition == 1 && pizzaPosition == 3) {
            pizzaImageView.setImageResource(R.drawable.chicagomeatzza);
        } else if (typePosition == 2 && pizzaPosition == 3) {
            pizzaImageView.setImageResource(R.drawable.nymeattza);
        } else if (pizzaPosition == 4) {
            pizzaImageView.setImageResource(R.drawable.buildyourownpizza);
        } else {
            pizzaImageView.setImageResource(0); // No selection
        }
    }
}