package com.example.rupizzeriaandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class ShoppingCartActivity extends AppCompatActivity {
    private RecyclerView pizzasList;
    private EditText subtotalText, taxText, totalText, orderNumberText, numberOfPizzasText;
    private Button removePizzaButton, clearOrderButton, placeOrderButton, ordersPlacedButton;
    private int selectedPizzaPosition = -1; // Tracks the selected pizza
    private static final double NJ_SALES_TAX = 0.06625;
    private int orderNumber = 0;

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
        SharedPreferences preferences = getSharedPreferences("OrderPrefs", MODE_PRIVATE);
        boolean isAppRunning = preferences.getBoolean("isAppRunning", false);

        if (!isAppRunning) {
            orderNumber = 0;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("orderNumber", orderNumber);
            editor.putBoolean("isAppRunning", true);
            editor.apply();

            Log.d("App restart", "App restarted, order number reset to 0.");
        } else {
            orderNumber = preferences.getInt("orderNumber", 0);
            Log.d("App resumed", "App resumed, order number is " + orderNumber);
        }
        orderNumberText = findViewById(R.id.orderNumberText);
        orderNumberText.setText(String.valueOf(orderNumber));
        numberOfPizzasText = findViewById(R.id.numberOfPizzasText);
        removePizzaButton = findViewById(R.id.removePizzaButton);
        clearOrderButton = findViewById(R.id.clearOrderButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        ordersPlacedButton = findViewById(R.id.ordersPlacedButton);
        pizzasList.setLayoutManager(new LinearLayoutManager(this));
        removePizzaButton.setOnClickListener(v -> removeSelectedPizza());
        clearOrderButton.setOnClickListener(v -> clearOrder());
        placeOrderButton.setOnClickListener(v -> placeOrder());
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
        numberOfPizzasText.setText(String.valueOf(PizzaManager.getInstance().getPizzas().size()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCart();
    }

    private void updateCart() {
        ArrayList<Pizza> pizzas = PizzaManager.getInstance().getPizzas();
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

    private void removeSelectedPizza() {
        PizzaAdapter adapter = (PizzaAdapter) pizzasList.getAdapter();
        int selectedPosition = adapter.getSelectedPosition();
        if (selectedPosition == RecyclerView.NO_POSITION) {
            Toast.makeText(this, "No pizza selected!", Toast.LENGTH_SHORT).show();
            return;
        }
        Pizza removedPizza = adapter.getSelectedPizza();
        PizzaManager.getInstance().removePizza(removedPizza);
        numberOfPizzasText.setText(String.valueOf(PizzaManager.getInstance().getPizzas().size()));
        updateCart();
        Toast.makeText(this, "Pizza removed successfully!", Toast.LENGTH_SHORT).show();
    }


    private void clearOrder() {
        PizzaManager.getInstance().clearPizzas();
        selectedPizzaPosition = -1;
        numberOfPizzasText.setText(String.valueOf(PizzaManager.getInstance().getPizzas().size()));
        updateCart();
        Toast.makeText(this, "Order cleared successfully!", Toast.LENGTH_SHORT).show();
    }

    private void placeOrder() {
        ArrayList<Pizza> pizzas = PizzaManager.getInstance().getPizzas();
        if (pizzas.isEmpty()) {
            Toast.makeText(this, "No pizzas in the cart!", Toast.LENGTH_SHORT).show();
            return;
        }
        orderNumber+=1;
        SharedPreferences preferences = getSharedPreferences("OrderPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("orderNumber", orderNumber);
        editor.apply();
        orderNumberText.setText(String.valueOf(orderNumber));
        Order order = new Order(orderNumber, pizzas);
        OrderManager.getInstance().addOrder(order);
        PizzaManager.getInstance().clearPizzas();
        selectedPizzaPosition = -1;
        updateCart();
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
    }

    private void updateTotals(ArrayList<Pizza> pizzas) {
        double subtotal = calculateSubtotal(pizzas);
        double salesTax = subtotal * NJ_SALES_TAX;
        double total = subtotal + salesTax;

        subtotalText.setText(String.format("%.2f", subtotal));
        taxText.setText(String.format("%.2f", salesTax));
        totalText.setText(String.format("%.2f", total));
    }

    private double calculateSubtotal(ArrayList<Pizza> pizzas) {
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        return subtotal;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getSharedPreferences("OrderPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAppRunning", false); // Mark app as not running
        editor.apply();
        Log.d("ShoppingCartActivity", "onDestroy called, isAppRunning set to false.");
    }
}