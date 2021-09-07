package com.myapp.courier.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.myapp.courier.R;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.api.ApiServices;
import com.myapp.courier.api.AuthType;
import com.myapp.courier.dto.UserDetailsDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_user_name;
    EditText edt_name,edt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_user_name = findViewById(R.id.tv_name);
        edt_name = findViewById(R.id.et_name);
        edt_address = findViewById(R.id.et_address);

        LoadUserProfile();
    }

    private void LoadUserProfile() {
        String user_id = "1";
        ApiServices apiServices = new ApiClient(this, AuthType.AUTHENTICATED).getInstance().create(ApiServices.class);
        Call<UserDetailsDTO> call = apiServices.callForUserDetails(user_id);
        call.enqueue(new Callback<UserDetailsDTO>() {
            @Override
            public void onResponse(Call<UserDetailsDTO> call, Response<UserDetailsDTO> response) {
                if (response.isSuccessful()){
                    Log.d("body-------------------", "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<UserDetailsDTO> call, Throwable t) {

            }
        });
    }
}