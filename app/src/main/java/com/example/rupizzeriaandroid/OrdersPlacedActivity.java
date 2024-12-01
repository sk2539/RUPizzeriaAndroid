package com.example.rupizzeriaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
        Button browseButton = findViewById(R.id.browseButton);
        browseButton.setOnClickListener(v -> {
            handleBrowseButtonClick();
        });
        ArrayList<Order> orders = OrderManager.getInstance().getOrders();
        if (orders.isEmpty()) {
            Toast.makeText(this, "No orders placed yet!", Toast.LENGTH_SHORT).show();
        } else {
            ordersAdapter = new OrderAdaptor(this, orders);
            ordersListView.setAdapter(ordersAdapter);
        }
        ordersListView.setOnItemClickListener((parent, view, position, id) -> {
            if (ordersAdapter != null) {
                ordersAdapter.toggleSelection(position);
            }
        });
    }

    private void handleBrowseButtonClick() {
        // Get the selected position in the ListView
        int selectedPosition = ordersListView.getCheckedItemPosition();
        if (selectedPosition == ListView.INVALID_POSITION) {
            // No item is selected
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Order Selected")
                    .setMessage("Please select an order to browse.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        // Get the selected order from the adapter
        Order selectedOrder = (Order) ordersAdapter.getItem(selectedPosition);

        // Create a StringBuilder to format the order details
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order Number: ").append(selectedOrder.getOrderNum()).append("\n");

        // Calculate total price of the order
        double totalPrice = 0;
        for (Pizza pizza : selectedOrder.getOrder()) {
            totalPrice += pizza.price();
        }
        orderDetails.append("Total Price: $").append(String.format("%.2f", totalPrice)).append("\n");

        // Add pizza details
        orderDetails.append("Pizzas Ordered:\n");
        if (selectedOrder.getOrder().isEmpty()) {
            orderDetails.append("  No pizzas in this order.\n");
        } else {
            for (Pizza pizza : selectedOrder.getOrder()) {
                orderDetails.append("  - ")
                        .append(pizza.getClass().getSimpleName())
                        .append(" - ").append(pizza.toString())
                        .append(" ($").append(String.format("%.2f", pizza.price())).append(")\n");
            }
        }

        EditText outputArea = findViewById(R.id.outputArea);
        outputArea.setText(orderDetails.toString());
    }

}