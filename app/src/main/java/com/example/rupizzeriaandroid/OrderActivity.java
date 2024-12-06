package com.example.rupizzeriaandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Locale;

/**
 * OrderActivity manages the pizza ordering process in the RU Pizzeria app.
 * It provides functionality for selecting pizza styles, types, sizes, and toppings,
 * and calculates the pizza price based on the user’s choices.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class OrderActivity extends AppCompatActivity {
    private Spinner typeSpinner;
    private Spinner pizzaSpinner;
    private ImageView pizzaImageView;
    ArrayList<Topping> toppings = new ArrayList<>();

    private int numberOfToppings = 0;
    private ImageView sausageLayer, pepperoniLayer, greenpepperLayer, onionLayer,
            mushroomLayer, chickenLayer, provoloneLayer, cheddarLayer,
            beefLayer, hamLayer, broccoliLayer, spinachLayer, jalapenoLayer;


    /**
     * Initializes topping selection chips, size radio buttons, and sets up event listeners.
     */
    private void onCreate2() {
        ChipGroup chipGroup = findViewById(R.id.toppings);
        chipGroup.setEnabled(false);
        Button setToppingsButton = findViewById(R.id.setToppings);
        setToppingsButton.setEnabled(false);
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
                    setImageforBYO(chip.getText().toString());
                    Log.d("ToppingCount", "Number of toppings selected: " + numberOfToppings);
                } else {
                    numberOfToppings--;
                    chip.setSelected(false);
                    toppings.remove(getToppingSelected(chip.getText().toString()));
                    chip.setChipBackgroundColorResource(R.color.darkbeige);
                    chip.setTextColor(Color.BLACK);
                    removeImageForBYO(chip.getText().toString());
                    Log.d("ToppingCount", "Number of toppings selected: " + numberOfToppings);
                }
                Log.d("Toppings", "Current toppings: " + toppings.toString());
                recalculatePrice();
            });
        }
        Button addToOrderButton = findViewById(R.id.addToOrderButton);
        addToOrderButton.setOnClickListener(v -> onAddToOrderClick());
        RadioGroup radioGroupSize = findViewById(R.id.radioGroupSize);
        radioGroupSize.setOnCheckedChangeListener((group, checkedId) -> {
            recalculatePrice();
        });
    }

    /**
     * Sets topping layers and button event listeners.
     */
    private void onCreate3() {
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
        Button btnShowTop = findViewById(R.id.setToppings);
        btnShowTop.setOnClickListener(v -> showToppingsPopup());
    }

    private void showToppingsPopup() {
        ToppingsPopup toppingsPopup = new ToppingsPopup();
        toppingsPopup.setOnToppingsSelectedListener(selectedToppings -> {
            toppings.clear();
            toppings.addAll(selectedToppings);
            updateToppingsView();
            recalculatePrice();
        });
        toppingsPopup.show(getSupportFragmentManager(), "ToppingsPopup");
    }

    private void updateToppingsView() {
        clearAllToppingsImages();
        for (Topping topping : toppings) {
            setImageforBYO(topping.toString());
        }
    }



    /**
     * Sets the image for the specified topping in the Build Your Own (BYO) pizza.
     *
     * @param topping The name of the topping to set the image for.
     */
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

    /**
     * Removes the image for the specified topping in the Build Your Own (BYO) pizza.
     *
     * @param topping The name of the topping to remove the image for.
     */
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

    /**
     * Clears all topping images and resets the number of selected toppings to zero.
     * This method is useful when starting a new pizza order.
     */
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

    /**
     * Displays an alert dialog when the maximum number of toppings (7) is reached.
     */
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

    /**
     * Retrieves the corresponding Topping enum value based on the topping name.
     *
     * @param toppingName The name of the topping as a string.
     * @return The corresponding Topping enum value, or null if the topping is not found.
     */
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
            case "BBQ CHICKEN":
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
     * Updates the pizza image and details based on the selected pizza style and type.
     */
    private void updatePizzaImage() {
        EditText crustText = findViewById(R.id.crustType);
        EditText priceText = findViewById(R.id.priceText);
        int typePosition = typeSpinner.getSelectedItemPosition();
        int pizzaPosition = pizzaSpinner.getSelectedItemPosition();
        boolean isChicagoStyle = (typePosition == 1);
        ChipGroup chipGroup = findViewById(R.id.toppings);
        clearAllToppingsImages();
        clearAllSelections();
        disableChips(false);
        Button setToppingsButton = findViewById(R.id.setToppings);
        switch (pizzaPosition) {
            case 1:
                setupPizza(isChicagoStyle, crustText, R.drawable.chicagodeluxepizza, R.drawable.nydeluxe, "Deep Dish", "Brooklyn",
                        Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREENPEPPER, Topping.ONION, Topping.MUSHROOM);
                break;
            case 2:
                setupPizza(isChicagoStyle, crustText, R.drawable.chicagobbqchicken, R.drawable.nybbqchicken, "Pan", "Thin",
                        Topping.BBQCHICKEN, Topping.GREENPEPPER, Topping.PROVOLONE, Topping.CHEDDAR);
                break;
            case 3:
                setupPizza(isChicagoStyle, crustText, R.drawable.chicagomeatzza, R.drawable.nymeattza, "Stuffed", "Hand-tossed",
                        Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREENPEPPER, Topping.BEEF, Topping.HAM);
                break;
            case 4:
                pizzaImageView.setImageResource(R.drawable.buildyourownpizza);
                crustText.setText(isChicagoStyle ? "Pan" : "Hand-tossed");
                enableChips(true);
                setToppingsButton.setEnabled(true);
                break;
            default:
                resetPizzaDisplay();
                break;
        }
        chipGroup.setEnabled(false);
        setToppingsButton.setEnabled(false);
    }

    /**
     * Configures the pizza display based on the selected style, crust, image, and toppings.
     *
     * @param isChicagoStyle Indicates if the selected style is Chicago.
     * @param crustText      The EditText to display the crust type.
     * @param chicagoImage   The image resource ID for Chicago-style pizza.
     * @param nyImage        The image resource ID for New York-style pizza.
     * @param chicagoCrust   The crust type for Chicago-style pizza.
     * @param nyCrust        The crust type for New York-style pizza.
     * @param toppingsToAdd  The toppings to add to the pizza.
     */
    private void setupPizza(boolean isChicagoStyle, EditText crustText, int chicagoImage, int nyImage,
                            String chicagoCrust, String nyCrust, Topping... toppingsToAdd) {
        pizzaImageView.setImageResource(isChicagoStyle ? chicagoImage : nyImage);
        crustText.setText(isChicagoStyle ? chicagoCrust : nyCrust);
        for (Topping topping : toppingsToAdd) {
            selectTopping(topping);
            toppings.add(topping);
        }
    }

    /**
     * Resets the pizza display to its default state, clearing toppings and images.
     */
    private void resetPizzaDisplay() {
        disableChips(false);
        clearAllToppingsImages();
        clearAllSelections();
        pizzaImageView.setImageDrawable(null);
    }

    /**
     * Recalculates the price of the pizza based on its size, style, and toppings.
     */
    private void recalculatePrice() {
        EditText priceText = findViewById(R.id.priceText);
        int typePosition = typeSpinner.getSelectedItemPosition();
        if (typePosition == 0) {
            priceText.setText("");
            return;
        }
        int pizzaPosition = pizzaSpinner.getSelectedItemPosition();
        if (pizzaPosition == 0) {
            priceText.setText("");
            return;
        }
        boolean isChicagoStyle = (typePosition == 1);
        PizzaFactory pizzaFactory = isChicagoStyle ? new ChicagoPizza() : new NYPizza();
        Pizza pizza = null;
        switch (pizzaPosition) {
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
                pizza.setToppings(new ArrayList<>(toppings));
                break;
            default:
                return;
        }
        Size selectedSize = getSizeUI();
        if (selectedSize != null) {
            pizza.setSize(selectedSize);
            double totalPrice = pizza.price();
            priceText.setText(String.format(Locale.getDefault(), "%.2f", totalPrice));
        }
    }

    ArrayList<Pizza> pizzas = new ArrayList<>();

    /**
     * Handles the action of adding a pizza to the order.
     */
    private void onAddToOrderClick() {
        clearAllToppingsImages();
        int typePosition = typeSpinner.getSelectedItemPosition();
        if (typePosition == 0) {
            Toast.makeText(this, "Please select a pizza style.", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean isChicagoStyle = (typePosition == 1);
        int pizzaPosition = pizzaSpinner.getSelectedItemPosition();
        if (pizzaPosition == 0) {
            Toast.makeText(this, "Please select a pizza type.", Toast.LENGTH_SHORT).show();
            return;
        }
        PizzaFactory pizzaFactory = isChicagoStyle ? new ChicagoPizza() : new NYPizza();
        Pizza pizza = null;
        switch (pizzaPosition) {
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
                pizza.setToppings(new ArrayList<>(toppings));
                break;
            default:
                Toast.makeText(this, "Invalid pizza type selected.", Toast.LENGTH_SHORT).show();
                return;
        }
        Size selectedSize = getSizeUI();
        if (selectedSize == null) {
            Toast.makeText(this, "Please select a size.", Toast.LENGTH_SHORT).show();
            return;
        }
        pizza.setSize(selectedSize);
        Crust selectedCrust = setCrustUI();
        if (selectedCrust == null) {
            Toast.makeText(this, "Invalid crust type.", Toast.LENGTH_SHORT).show();
            return;
        }
        pizza.setCrust(selectedCrust);
        EditText priceText = findViewById(R.id.priceText);
        double totalPrice = pizza.price();
        priceText.setText(String.format(Locale.getDefault(), "%.2f", totalPrice));
        PizzaOrderManager.getInstance().addPizza(pizza);
        Log.d("PizzaList", "Current pizzas: " + pizzas.toString());
        Toast.makeText(this, "Pizza added to order successfully!", Toast.LENGTH_SHORT).show();
        clearAllSelections();
        clearInputs();
        disableChips(true);
    }

    /**
     * Clears all input fields and selections to reset the UI for a new order.
     */
    private void clearInputs() {
        EditText pizzaTypeEditText = findViewById(R.id.crustType);
        pizzaTypeEditText.setText("");
        typeSpinner.setSelection(0);
        pizzaSpinner.setSelection(0);
        RadioGroup radioGroupSize = findViewById(R.id.radioGroupSize);
        radioGroupSize.clearCheck();
        toppings.clear();
    }

    /**
     * Retrieves the selected pizza size from the radio group UI.
     *
     * @return The selected size as a Size enum, or null if no size is selected.
     */
    private Size getSizeUI() {
        RadioGroup radioGroupSize = findViewById(R.id.radioGroupSize);
        int checkedId = radioGroupSize.getCheckedRadioButtonId();
        if (checkedId != -1) {
            RadioButton selectedButton = findViewById(checkedId);
            String sizeText = selectedButton.getText().toString().toUpperCase();

            switch (sizeText) {
                case "SMALL":
                    return Size.SMALL;
                case "MEDIUM":
                    return Size.MEDIUM;
                case "LARGE":
                    return Size.LARGE;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Determines the pizza crust type based on user input in the EditText field.
     *
     * @return The corresponding Crust enum value, or null if the input is invalid.
     */
    private Crust setCrustUI() {
        EditText crust = findViewById(R.id.crustType);
        String crustText = crust.getText().toString().trim();
        if (crustText.equalsIgnoreCase("Deep Dish")) {
            return Crust.DEEPDISH;
        } else if (crustText.equalsIgnoreCase("Pan")) {
            return Crust.PAN;
        } else if (crustText.equalsIgnoreCase("Stuffed")) {
            return Crust.STUFFED;
        } else if (crustText.equalsIgnoreCase("Brooklyn")) {
            return Crust.BROOKLYN;
        } else if (crustText.equalsIgnoreCase("Thin")) {
            return Crust.THIN;
        } else if (crustText.equalsIgnoreCase("Hand-tossed")) {
            return Crust.HANDTOSSED;
        } else {
            Toast.makeText(this, "Invalid crust type: " + crust, Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * Disables or enables the topping selection chips.
     *
     * @param enable Boolean value to enable or disable the chips.
     */
    private void disableChips(boolean enable) {
        ChipGroup chipGroup = findViewById(R.id.toppings);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setEnabled(enable);
        }
    }

    /**
     * Enables or disables the topping selection chips.
     *
     * @param enable Boolean value to enable or disable the chips.
     */
    private void enableChips(boolean enable) {
        ChipGroup chipGroup = findViewById(R.id.toppings);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setEnabled(enable);
        }
    }

    /**
     * Clears all topping selections, resets chip styles, and clears the toppings list.
     */
    private void clearAllSelections() {
        ChipGroup chipGroup = findViewById(R.id.toppings);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setSelected(false);
            chip.setChipBackgroundColorResource(R.color.darkbeige);
            chip.setTextColor(Color.BLACK);
        }
        toppings.clear();
        numberOfToppings = 0;
        Log.d("Toppings", "Cleared all selections: " + toppings.toString());
    }

    /**
     * Selects a topping visually by updating its chip and adding it to the toppings list.
     *
     * @param topping The topping to be selected.
     */
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
                recalculatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(OrderActivity.this, "Please make a selection", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Called when the activity is first created. Sets up UI elements, spinners,
     * buttons, and event listeners for managing the pizza ordering process.
     *
     * @param savedInstanceState the saved instance state bundle.
     */
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
        String[] orderOptions = {"Select pizza style", "Chicago Pizza", "New York Pizza"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                orderOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        pizzaSpinner = findViewById(R.id.choosePizzaType);
        String[] pizzaTypeOptions = {"Select pizza type", "Deluxe", "BBQ Chicken", "Meatzza", "Build your own"};
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
        ImageButton shoppingCart = findViewById(R.id.shoppingCart);
        shoppingCart.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        });
        onCreate2();
        onCreate3();
    }

}