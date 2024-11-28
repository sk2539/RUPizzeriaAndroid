package com.example.rupizzeriaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        pizzasList = findViewById(R.id.pizzasList); // Initialize RecyclerView
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
    }

}