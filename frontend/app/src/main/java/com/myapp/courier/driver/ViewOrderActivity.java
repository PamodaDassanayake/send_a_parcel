package com.myapp.courier.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import com.myapp.courier.R;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.api.ApiServices;
import com.myapp.courier.api.AuthType;
import com.myapp.courier.dto.OrderDetailsByIdDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderActivity extends AppCompatActivity {
    EditText edt_rec_name,edt_rec_address,edt_rec_phone,edt_desc;
    ConstraintLayout btn_accept,btn_decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edt_rec_name = findViewById(R.id.edt_rec_name);
        edt_rec_address = findViewById(R.id.edt_rec_address);
        edt_rec_phone = findViewById(R.id.edt_rec_phone);
        edt_desc = findViewById(R.id.edt_desc);
        btn_accept = findViewById(R.id.btn_accept);
        btn_decline = findViewById(R.id.btn_decline);
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