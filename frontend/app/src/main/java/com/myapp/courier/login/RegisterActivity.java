package com.myapp.courier.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.courier.R;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.dto.UserRequestDTO;
import com.myapp.courier.dto.UserResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView tv_login;
    EditText edt_user_name,edt_password,edt_phone,edt_address;
    ConstraintLayout btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_login = findViewById(R.id.tv_login);
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_password = findViewById(R.id.edt_password);
        edt_address = findViewById(R.id.edt_address);
        btn_register = findViewById(R.id.btn_register);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name,password,address,user_type;
                user_name = edt_user_name.getText().toString().trim();
                password = edt_password.getText().toString().trim();
                address = edt_address.getText().toString().trim();
                user_type = "customer";
                callApi(user_name,password,address,user_type);
            }
        });
    }

    private void callApi(String userType, String user_name, String password, String address) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername(user_name);
        userRequestDTO.setPassword(password);
        userRequestDTO.setAddress(address);
        userRequestDTO.setUserType(userType);

        Call<UserResponseDTO> userResponseCall = ApiClient.getUserService().saveUser(userRequestDTO);
        userResponseCall.enqueue(new Callback<UserResponseDTO>() {
            @Override
            public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
                if (response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"saved successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                }else {
                    Toast.makeText(RegisterActivity.this,"User name Exists",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"saved failed"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}