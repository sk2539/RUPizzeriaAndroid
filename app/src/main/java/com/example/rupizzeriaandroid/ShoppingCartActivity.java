package com.example.rupizzeriaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Activity class for managing the shopping cart in the application.
 * Handles pizza-related operations such as viewing, removing, clearing,
 * and placing orders, while updating UI components dynamically.
 * @author Nithya Konduru, Dhyanashri Raman
 */
public class ShoppingCartActivity extends AppCompatActivity {
    private RecyclerView pizzasList;
    private EditText subtotalText, taxText, totalText, orderNumberText, numberOfPizzasText;
    private Button removePizzaButton, clearOrderButton, placeOrderButton, ordersPlacedButton;
    private int selectedPizzaPosition = -1;
    private static final double NJ_SALES_TAX = 0.06625;

    private int orderNumber;

    /**
     * Configures navigation buttons and their actions.
     * Includes Home, Back, and Orders Placed buttons.
     */
    private void setButtons() {
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
            startActivity(intent);
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, OrderActivity.class);
            startActivity(intent);
        });
        Button ordersPlacedButton = findViewById(R.id.ordersPlacedButton);
        ordersPlacedButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, OrdersPlacedActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Updates the shopping cart by refreshing the list of pizzas and recalculating totals.
     */
    private void updateCart() {
        ArrayList<Pizza> pizzas = PizzaOrderManager.getInstance().getPizzas();
        PizzaAdapter adapter;
        if (pizzasList.getAdapter() != null) {
            adapter = (PizzaAdapter) pizzasList.getAdapter();
            adapter.updatePizzas(pizzas);
        } else {
            adapter = new PizzaAdapter(pizzas);
            pizzasList.setAdapter(adapter);
        }
        updateTotals(pizzas);
        numberOfPizzasText.setText(String.valueOf(pizzas.size()));
    }

    /**
     * Removes the currently selected pizza from the cart.
     * Updates the UI and displays a confirmation message.
     */
    private void removeSelectedPizza() {
        PizzaAdapter adapter = (PizzaAdapter) pizzasList.getAdapter();
        int selectedPosition = adapter.getSelectedPosition();
        if (selectedPosition == RecyclerView.NO_POSITION) {
            Toast.makeText(this, "No pizza selected!", Toast.LENGTH_SHORT).show();
            return;
        }
        Pizza removedPizza = adapter.getSelectedPizza();
        PizzaOrderManager.getInstance().removePizza(removedPizza);
        numberOfPizzasText.setText(String.valueOf(PizzaOrderManager.getInstance().getPizzas().size()));
        updateCart();
        Toast.makeText(this, "Pizza removed successfully!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Clears all pizzas from the current order.
     * Updates the UI and displays a confirmation message.
     */
    private void clearOrder() {
        PizzaOrderManager.getInstance().clearPizzas();
        selectedPizzaPosition = -1;
        numberOfPizzasText.setText(String.valueOf(PizzaOrderManager.getInstance().getPizzas().size()));
        updateCart();
        Toast.makeText(this, "Order cleared successfully!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Places the current order by assigning it an order number
     * and clearing the cart after adding it to the order list.
     * Displays a confirmation message.
     */
    private void placeOrder() {
        ArrayList<Pizza> pizzas = PizzaOrderManager.getInstance().getPizzas();
        if (pizzas.isEmpty()) {
            Toast.makeText(this, "No pizzas in the cart!", Toast.LENGTH_SHORT).show();
            return;
        }
        orderNumber = PizzaOrderManager.getInstance().generateOrderNumber();
        orderNumberText.setText(String.valueOf(orderNumber));
        numberOfPizzasText = findViewById(R.id.numberOfPizzasText);
        Order order = new Order(orderNumber, pizzas);
        PizzaOrderManager.getInstance().addOrder(order);
        PizzaOrderManager.getInstance().clearPizzas();
        selectedPizzaPosition = -1;
        updateCart();
        Toast.makeText(this, "Order #" + orderNumber + " placed successfully!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates the subtotal, tax, and total amounts for the current order.
     *
     * @param pizzas The list of pizzas in the cart.
     */
    private void updateTotals(ArrayList<Pizza> pizzas) {
        double subtotal = calculateSubtotal(pizzas);
        double salesTax = subtotal * NJ_SALES_TAX;
        double total = subtotal + salesTax;

        subtotalText.setText(String.format("%.2f", subtotal));
        taxText.setText(String.format("%.2f", salesTax));
        totalText.setText(String.format("%.2f", total));
    }

    /**
     * Calculates the subtotal for the given list of pizzas.
     *
     * @param pizzas The list of pizzas to calculate the subtotal for.
     * @return The calculated subtotal.
     */
    private double calculateSubtotal(ArrayList<Pizza> pizzas) {
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        return subtotal;
    }

    /**
     * Called when the activity is created.
     * Initializes UI components, sets up RecyclerView and buttons, and populates initial data.
     *
     * @param savedInstanceState If non-null, contains the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pizzasList = findViewById(R.id.pizzasList);
        subtotalText = findViewById(R.id.subtotalText);
        taxText = findViewById(R.id.taxText);
        totalText = findViewById(R.id.totalText);
        orderNumberText = findViewById(R.id.orderNumberText);
        orderNumberText.setText(String.valueOf(PizzaOrderManager.getInstance().getCurrOrderNumber()));
        numberOfPizzasText = findViewById(R.id.numberOfPizzasText);
        removePizzaButton = findViewById(R.id.removePizzaButton);
        clearOrderButton = findViewById(R.id.clearOrderButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        ordersPlacedButton = findViewById(R.id.ordersPlacedButton);
        pizzasList.setLayoutManager(new LinearLayoutManager(this));
        setButtons();
        removePizzaButton.setOnClickListener(v -> removeSelectedPizza());
        clearOrderButton.setOnClickListener(v -> clearOrder());
        placeOrderButton.setOnClickListener(v -> placeOrder());
        numberOfPizzasText.setText(String.valueOf(PizzaOrderManager.getInstance().getPizzas().size()));
    }

    /**
     * Called when the activity resumes.
     * Updates the cart data and UI components.
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateCart();
    }

}