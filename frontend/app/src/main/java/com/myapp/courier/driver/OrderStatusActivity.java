package com.myapp.courier.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.myapp.courier.R;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.api.ApiServices;
import com.myapp.courier.api.AuthType;
import com.myapp.courier.dto.OrderDetailsByIdDTO;
import com.myapp.courier.dto.UpdateOrderStatusDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusActivity extends AppCompatActivity {
    ConstraintLayout btn_get_order,btn_on_the_way,btn_sent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn_get_order = findViewById(R.id.btn_get_order);
        btn_on_the_way = findViewById(R.id.btn_on_the_way);
        btn_sent = findViewById(R.id.btn_sent);

        btn_get_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_get_order.setBackground(getDrawable(R.drawable.button_red_bg));
                btn_on_the_way.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                btn_sent.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                UpdateOrderStatusDTO updateOrderStatusDTO = new UpdateOrderStatusDTO("PICKING_ORDER");
                ApiServices apiServices = new ApiClient(OrderStatusActivity.this, AuthType.AUTHENTICATED).getInstance().create(ApiServices.class);
                Call<UpdateOrderStatusDTO> call = apiServices.callForUpdateOrder(updateOrderStatusDTO);
                call.enqueue(new Callback<UpdateOrderStatusDTO>() {
                    @Override
                    public void onResponse(Call<UpdateOrderStatusDTO> call, Response<UpdateOrderStatusDTO> response) {
                        if (response.isSuccessful()){
                            Log.d("body-------------------", "onResponse: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateOrderStatusDTO> call, Throwable t) {

                    }
                });
            }
        });

        btn_on_the_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_get_order.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                btn_on_the_way.setBackground(getDrawable(R.drawable.button_red_bg));
                btn_sent.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                UpdateOrderStatusDTO updateOrderStatusDTO = new UpdateOrderStatusDTO("ON_THE_WAY");
                ApiServices apiServices = new ApiClient(OrderStatusActivity.this, AuthType.AUTHENTICATED).getInstance().create(ApiServices.class);
                Call<UpdateOrderStatusDTO> call = apiServices.callForUpdateOrder(updateOrderStatusDTO);
                call.enqueue(new Callback<UpdateOrderStatusDTO>() {
                    @Override
                    public void onResponse(Call<UpdateOrderStatusDTO> call, Response<UpdateOrderStatusDTO> response) {
                        if (response.isSuccessful()){
                            Log.d("body-------------------", "onResponse: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateOrderStatusDTO> call, Throwable t) {

                    }
                });
            }
        });

        btn_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_get_order.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                btn_on_the_way.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                btn_sent.setBackground(getDrawable(R.drawable.button_red_bg));
                UpdateOrderStatusDTO updateOrderStatusDTO = new UpdateOrderStatusDTO("DELIVERED");
                ApiServices apiServices = new ApiClient(OrderStatusActivity.this, AuthType.AUTHENTICATED).getInstance().create(ApiServices.class);
                Call<UpdateOrderStatusDTO> call = apiServices.callForUpdateOrder(updateOrderStatusDTO);
                call.enqueue(new Callback<UpdateOrderStatusDTO>() {
                    @Override
                    public void onResponse(Call<UpdateOrderStatusDTO> call, Response<UpdateOrderStatusDTO> response) {
                        if (response.isSuccessful()){
                            Log.d("body-------------------", "onResponse: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateOrderStatusDTO> call, Throwable t) {

                    }
                });
            }
        });
        LoadOrderDetails();
    }

    private void LoadOrderDetails() {
        String order_id = "1";
        ApiServices apiServices = new ApiClient(this, AuthType.AUTHENTICATED).getInstance().create(ApiServices.class);
        Call<OrderDetailsByIdDTO> call = apiServices.callForOrderDetails(order_id);
        call.enqueue(new Callback<OrderDetailsByIdDTO>() {
            @Override
            public void onResponse(Call<OrderDetailsByIdDTO> call, Response<OrderDetailsByIdDTO> response) {
                if (response.isSuccessful()){
                    Log.d("body-------------------", "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsByIdDTO> call, Throwable t) {

            }
        });
    }
}