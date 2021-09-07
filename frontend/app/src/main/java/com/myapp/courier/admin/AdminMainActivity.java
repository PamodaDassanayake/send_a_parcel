package com.myapp.courier.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.myapp.courier.R;
import com.myapp.courier.adapters.DriverOrderDetailsAdapter;
import com.myapp.courier.adapters.OrderDetailsAdapter;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.api.ApiServices;
import com.myapp.courier.api.AuthType;
import com.myapp.courier.driver.DriverHomeActivity;
import com.myapp.courier.dto.LoadIrdersByStatusResDTO;
import com.myapp.courier.dto.LoadOrderByStatusDTO;
import com.myapp.courier.dto.OrderDetailsDto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminMainActivity extends AppCompatActivity implements OrderDetailsAdapter.OrderDetailsViewHolder.RecyclerViewClickListener {
    RecyclerView rv_orders;
    DriverOrderDetailsAdapter orderDetailsAdapter;
    ArrayList<OrderDetailsDto> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fragment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rv_orders = findViewById(R.id.rv_orders);
//        LoadOrders();
        loadReqOrder();

    }

    private void loadReqOrder() {
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

        orderDetailsAdapter = new DriverOrderDetailsAdapter(this,mData, AdminMainActivity.this::onClickListener);
        rv_orders.setAdapter(orderDetailsAdapter);
        rv_orders.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClickListener(int position) {

    }
}