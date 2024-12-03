package com.example.rupizzeriaandroid;

import android.content.Intent;
import android.net.Uri;
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

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;


import android.util.Log;

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
        ordersListView.setAdapter(ordersAdapter);
        ordersListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        handleAllButtons();
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

    private void handleAllButtons() {
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
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            handleCancelClick();
        });
        Button exportButton = findViewById(R.id.exportButton);
        exportButton.setOnClickListener(v -> handleExportClick());
    }

    private void handleBrowseButtonClick() {
        int selectedPosition = ordersAdapter.getSelectedPosition();
        Log.d("ListView", "Selected Position: " + selectedPosition);
        if (selectedPosition == ListView.INVALID_POSITION) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Order Selected")
                    .setMessage("Please select an order to browse.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }
        Order selectedOrder = (Order) ordersAdapter.getItem(selectedPosition);
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order Number: ").append(selectedOrder.getOrderNum()).append("\n");
        double totalPrice = selectedOrder.calculatePrice();
        orderDetails.append("Total Price: $").append(String.format("%.2f", totalPrice)).append("\n");
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
    private void handleCancelClick() {
        int selectedPosition = ordersAdapter.getSelectedPosition();
        if (selectedPosition == -1) {
            new AlertDialog.Builder(this)
                    .setTitle("No Order Selected")
                    .setMessage("Please select an order to cancel.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }
        Order selectedOrder = (Order) ordersAdapter.getItem(selectedPosition);
        if (selectedOrder != null) {
            OrderManager.getInstance().removeOrder(selectedOrder);
            ordersAdapter.removeOrder(selectedOrder);
            ordersAdapter.setSelectedPosition(-1);
        }
    }
    private void handleExportClick() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "orders.txt");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri uri = data.getData();
            if (uri != null) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(getContentResolver().openOutputStream(uri)))) {
                    writer.write("Order Number\tTotal Price\tPizzas\n");
                    writer.write("-------------------------------------------------\n");
                    for (Order order : OrderManager.getInstance().getOrders()) {
                        writer.write("Order #" + order.getOrderNum() + "\t");
                        double total = order.getOrder().stream().mapToDouble(Pizza::price).sum();
                        writer.write(String.format("$%.2f\t", total));
                        String pizzas = order.getOrder().stream()
                                .map(pizza -> pizza.getClass().getSimpleName() + " - " + pizza.toString())
                                .reduce((a, b) -> a + ", " + b)
                                .orElse("No pizzas");
                        writer.write(pizzas + "\n\n");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Export Successful")
                            .setMessage("Orders successfully exported to: " + uri.getPath())
                            .setPositiveButton("OK", null)
                            .show();

                } catch (IOException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Export Failed")
                            .setMessage("Error exporting orders: " + e.getMessage())
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        }
    }
}