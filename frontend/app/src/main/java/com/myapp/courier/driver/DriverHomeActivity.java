package com.myapp.courier.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.myapp.courier.R;
import com.myapp.courier.adapters.DriverOrderDetailsAdapter;
import com.myapp.courier.adapters.OrderDetailsAdapter;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.api.ApiServices;
import com.myapp.courier.api.AuthType;
import com.myapp.courier.customer.OrderDetailsActivity;
import com.myapp.courier.dto.LoadIrdersByStatusResDTO;
import com.myapp.courier.dto.LoadOrderByStatusDTO;
import com.myapp.courier.dto.OrderDetailsDto;
import com.myapp.courier.login.LoginActivity;
import com.myapp.courier.login.RegisterActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverHomeActivity extends AppCompatActivity implements OrderDetailsAdapter.OrderDetailsViewHolder.RecyclerViewClickListener {
    ImageView img_orders,img_profile;
    RecyclerView rv_orders;
    DriverOrderDetailsAdapter orderDetailsAdapter;
    ArrayList<OrderDetailsDto> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        img_orders = findViewById(R.id.img_orders);
        img_profile = findViewById(R.id.img_profile);
        rv_orders = findViewById(R.id.rv_order);

        img_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverHomeActivity.this, DriverAcceptedOrdersActivity.class));
            }
        });

//        LoadOrders();
        LoadAssignedOrders();
    }

    private void LoadAssignedOrders() {
        LoadOrderByStatusDTO loadOrderByStatusDTO = new LoadOrderByStatusDTO("ASSIGNED_DRIVER");
        ApiServices apiServices = new ApiClient(this, AuthType.AUTHENTICATED).getInstance().create(ApiServices.class);
        Call<LoadIrdersByStatusResDTO> call = apiServices.laodOrdersByStatus(loadOrderByStatusDTO);
        call.enqueue(new Callback<LoadIrdersByStatusResDTO>() {
            @Override
            public void onResponse(Call<LoadIrdersByStatusResDTO> call, Response<LoadIrdersByStatusResDTO> response) {
                if (response.isSuccessful()){
                    Log.d("body-------------------", "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<LoadIrdersByStatusResDTO> call, Throwable t) {

            }
        });
    }

    private void LoadOrders() {
        mData = new ArrayList<>();
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));
        mData.add(new OrderDetailsDto("Jhone willey","32/1, galle rd, mt lavinia","0771234545","Birthday present"));

        orderDetailsAdapter = new DriverOrderDetailsAdapter(this,mData, DriverHomeActivity.this::onClickListener);
        rv_orders.setAdapter(orderDetailsAdapter);
        rv_orders.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClickListener(int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you reject this order?")
                .setMessage("This order has been assigned to you. you can reject this order or take this order")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNegativeButton("Update status", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //need get id of order and passed it with intent
                        startActivity(new Intent(DriverHomeActivity.this,ViewOrderActivity.class));
                    }
                })
                .show();


    }
}