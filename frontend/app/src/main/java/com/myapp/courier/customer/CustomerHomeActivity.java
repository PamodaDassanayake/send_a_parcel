package com.myapp.courier.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.myapp.courier.R;
import com.myapp.courier.login.LoginActivity;

public class CustomerHomeActivity extends AppCompatActivity {
    ConstraintLayout btn_go,btn_req_orders,btn_pending_orders,btn_sent_orders;
    TextView tv_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_go = findViewById(R.id.btn_go);
        btn_req_orders = findViewById(R.id.btn_req_orders);
        btn_pending_orders = findViewById(R.id.btn_pending_orders);
        btn_sent_orders = findViewById(R.id.btn_sent_orders);
        tv_profile = findViewById(R.id.tv_profile);

        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerHomeActivity.this, ProfileActivity.class));
            }
        });

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerHomeActivity.this, SendOrderActivity.class));

            }
        });
        btn_req_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this,OrderDetailsActivity.class);
                intent.putExtra("OrderStatus","Requested");
                startActivity(intent);
            }
        });
        btn_pending_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this,OrderDetailsActivity.class);
                intent.putExtra("OrderStatus","Pending");
                startActivity(intent);
            }
        });
        btn_sent_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this,OrderDetailsActivity.class);
                intent.putExtra("OrderStatus","Sent");
                startActivity(intent);
            }
        });
    }
}