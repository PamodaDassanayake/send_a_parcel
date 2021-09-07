package com.myapp.courier.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.courier.R;
import com.myapp.courier.api.ApiClient;
import com.myapp.courier.customer.CustomerHomeActivity;
import com.myapp.courier.dto.LoginRequestDTO;
import com.myapp.courier.dto.LoginResponseDTO;
import com.myapp.courier.usermanager.UserManager;
import com.myapp.courier.usermanager.UserManagerImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tv_register;
    EditText edt_user_name,edt_password;
    ConstraintLayout btn_login;
    private static String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_register = findViewById(R.id.tv_register);
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name,password;
                user_name = edt_user_name.getText().toString().trim();
                password = edt_password.getText().toString().trim();
                callApi(user_name,password);
//                startActivity(new Intent(LoginActivity.this, CustomerHomeActivity.class));
            }
        });
    }

    private void callApi(String user_name, String password) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername(user_name);
        loginRequestDTO.setPassword(password);
        Call<LoginResponseDTO> loginResponseDTOCall = ApiClient.getUserService().logUser(loginRequestDTO);
        loginResponseDTOCall.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if (response.isSuccessful()){
                    token = response.body().getToken();
                    UserManager userManager = new UserManagerImpl(LoginActivity.this);
                    userManager.setToken(token);
                    Log.d("TAG","You have successfully Logged in" +token);
                    startActivity(new Intent(LoginActivity.this, CustomerHomeActivity.class));

                }else {
                    Toast.makeText(LoginActivity.this,"Login Failed. Please contact admin", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Throwable" +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}