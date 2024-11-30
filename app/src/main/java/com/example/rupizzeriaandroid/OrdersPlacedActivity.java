package com.example.rupizzeriaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;


public class OrdersPlacedActivity extends AppCompatActivity {
    private ListView ordersListView;
    private OrderAdaptor ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders_placed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ordersListView = findViewById(R.id.orderListView);
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrdersPlacedActivity.this, MainActivity.class);
            startActivity(intent);
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrdersPlacedActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        });

        ArrayList<Order> orders = OrderManager.getInstance().getOrders();
        if (orders.isEmpty()) {
            Toast.makeText(this, "No orders placed yet!", Toast.LENGTH_SHORT).show();
        } else {
            // Set the adapter to display the orders
            ordersAdapter = new OrderAdaptor(this, orders);
            ordersListView.setAdapter(ordersAdapter);
        }
    }
}