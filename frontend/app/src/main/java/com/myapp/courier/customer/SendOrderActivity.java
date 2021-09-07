package com.myapp.courier.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.courier.R;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.api.ApiServices;
import com.myapp.courier.api.AuthType;
import com.myapp.courier.dto.SaveOrderDTO;
import com.myapp.courier.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOrderActivity extends AppCompatActivity {
    EditText edt_rec_name,edt_rec_address,edt_pick_address,edt_desc,edt_pick_phone;
    ConstraintLayout btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edt_rec_name = findViewById(R.id.edt_rec_name);
        edt_rec_address = findViewById(R.id.edt_rec_address);
        edt_pick_address = findViewById(R.id.edt_pick_address);
        edt_desc = findViewById(R.id.edt_desc);
        edt_pick_phone = findViewById(R.id.edt_pick_phone);
        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec_name,rec_address,pick_address,rec_phone,description;
                rec_name = edt_rec_name.getText().toString();
                rec_address = edt_rec_address.getText().toString();
                pick_address = edt_pick_address.getText().toString();
                description = edt_desc.getText().toString();
                rec_phone = edt_pick_phone.getText().toString();
                if (rec_name.isEmpty() || rec_address.isEmpty() || pick_address.isEmpty() || description.isEmpty() || rec_phone.isEmpty()){
                    Toast.makeText(SendOrderActivity.this,"Please fill all field...", Toast.LENGTH_LONG).show();
                }else {
                    saveOrder(rec_name,rec_address,pick_address,description,rec_phone);
                }
            }
        });

    }

    private void saveOrder(String rec_name, String rec_address, String pick_address, String description, String rec_phone) {
        SaveOrderDTO saveOrderDTO = new SaveOrderDTO(rec_name,rec_address,rec_phone,pick_address,description);
        ApiServices apiServices = new ApiClient(this, AuthType.AUTHENTICATED).getInstance().create(ApiServices.class);
        Call<SaveOrderDTO> call = apiServices.saveOrder(saveOrderDTO);
        call.enqueue(new Callback<SaveOrderDTO>() {
            @Override
            public void onResponse(Call<SaveOrderDTO> call, Response<SaveOrderDTO> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SendOrderActivity.this,"Order Saved Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SendOrderActivity.this, CustomerHomeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<SaveOrderDTO> call, Throwable t) {
                Toast.makeText(SendOrderActivity.this,"Order not saved"+t, Toast.LENGTH_LONG).show();
            }
        });
    }
}