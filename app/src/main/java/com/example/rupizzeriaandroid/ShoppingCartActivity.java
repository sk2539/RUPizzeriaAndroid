package com.example.rupizzeriaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

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
    private EditText subtotalText, taxText, totalText;
    private static final double NJ_SALES_TAX = 0.06625;

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
        if (pizzasList == null) {
            Log.e("ShoppingCartActivity", "RecyclerView is null. Check your XML layout ID.");
        }
        pizzasList.setLayoutManager(new LinearLayoutManager(this)); // Set a layout manager

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCart();
    }

    private void updateCart() {
        ArrayList<Pizza> pizzas = PizzaManager.getInstance().getPizzas();
        if (pizzasList.getAdapter() != null) {
            ((PizzaAdapter) pizzasList.getAdapter()).updatePizzas(pizzas);
        } else {
            PizzaAdapter adapter = new PizzaAdapter(pizzas);
            pizzasList.setAdapter(adapter);
        }

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


}