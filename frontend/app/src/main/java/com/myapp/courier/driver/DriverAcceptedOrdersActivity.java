package com.myapp.courier.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.myapp.courier.R;
import com.myapp.courier.adapters.DriverOrderDetailsAdapter;
import com.myapp.courier.adapters.OrderDetailsAdapter;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.api.ApiServices;
import com.myapp.courier.api.AuthType;
import com.myapp.courier.dto.LoadIrdersByStatusResDTO;
import com.myapp.courier.dto.LoadOrderByStatusDTO;
import com.myapp.courier.dto.OrderDetailsDto;
import com.myapp.courier.login.LoginActivity;
import com.myapp.courier.login.RegisterActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAcceptedOrdersActivity extends AppCompatActivity implements OrderDetailsAdapter.OrderDetailsViewHolder.RecyclerViewClickListener {
    RecyclerView rv_orders;
    DriverOrderDetailsAdapter orderDetailsAdapter;
    ArrayList<OrderDetailsDto> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_accepted_orders);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rv_orders = findViewById(R.id.rv_accepted_orders);

        LoadOrders();
        LoadAcceptedOrders();
    }

    private void LoadAcceptedOrders() {
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

        orderDetailsAdapter = new DriverOrderDetailsAdapter(this,mData, DriverAcceptedOrdersActivity.this::onClickListener);
        rv_orders.setAdapter(orderDetailsAdapter);
        rv_orders.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClickListener(int position) {
        startActivity(new Intent(DriverAcceptedOrdersActivity.this, OrderStatusActivity.class));

    }
}