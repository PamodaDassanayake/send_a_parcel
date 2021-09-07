package com.myapp.courier.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.myapp.courier.R;

public class AssignDriverActivity extends AppCompatActivity {
    RecyclerView rv_drivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_driver);

        rv_drivers = findViewById(R.id.rv_drivers);
        LoadDriverList();
    }

    private void LoadDriverList() {
    }
}