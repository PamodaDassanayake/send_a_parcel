package com.myapp.courier.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.myapp.courier.R;
import com.myapp.courier.adapters.OrderDetailsAdapter;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.api.ApiServices;
import com.myapp.courier.api.AuthType;
import com.myapp.courier.dto.LoadIrdersByStatusResDTO;
import com.myapp.courier.dto.LoadOrderByStatusDTO;
import com.myapp.courier.dto.OrderDetailsDto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailsAdapter.OrderDetailsViewHolder.RecyclerViewClickListener {
    TextView tv_order_type;
    RecyclerView rv_order;
    OrderDetailsAdapter orderDetailsAdapter;
    ArrayList<OrderDetailsDto> mData;
    String order_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_order_type = findViewById(R.id.tv_order_type);
        rv_order = findViewById(R.id.rv_order);
        Intent intent = getIntent();
        order_status = intent.getStringExtra("OrderStatus");
//        loadOrders();
        if (order_status.equals("Requested")){
            loadRequestedOrder();
        }else {
            if (order_status.equals("Pending")){
                loadPendingOrders();
            }else {
                loadSentOrders();
            }
        }
    }

    private void loadOrders() {
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

        orderDetailsAdapter = new OrderDetailsAdapter(this,mData, OrderDetailsActivity.this::onClickListener);
        rv_order.setAdapter(orderDetailsAdapter);
        rv_order.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadSentOrders() {
        LoadOrderByStatusDTO loadOrderByStatusDTO = new LoadOrderByStatusDTO("DELIVERED");
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

    private void loadPendingOrders() {
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

    private void loadRequestedOrder() {
        LoadOrderByStatusDTO loadOrderByStatusDTO = new LoadOrderByStatusDTO("REQUESTED");
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

    @Override
    public void onClickListener(int position) {

    }
}