package com.example.rupizzeriaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnShowMenu = findViewById(R.id.menuButton);
        btnShowMenu.setOnClickListener(v -> {
            MenuPopup dialogFragment = new MenuPopup();
            dialogFragment.show(getSupportFragmentManager(), "menuDialog");
        });

        Button btnShowTop = findViewById(R.id.test);
        btnShowTop.setOnClickListener(v -> {
            ToppingsPopup toppingsV = new ToppingsPopup();
            toppingsV.show(getSupportFragmentManager(), "toppingView");
        });

        Button btnShowOrder = findViewById(R.id.orderButton);
        btnShowOrder.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        });

    }

}