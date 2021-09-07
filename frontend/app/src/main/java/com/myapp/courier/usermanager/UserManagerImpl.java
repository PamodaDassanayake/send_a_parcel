package com.myapp.courier.usermanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.myapp.courier.dto.LoginResponseDTO;
import com.myapp.courier.utils.AppConstants;


public class UserManagerImpl implements UserManager {

    SharedPreferences userPreferences;
    private Context context;
    private Gson gson;

    public UserManagerImpl(Context context) {
        this.context = context;
        gson = new Gson();
        userPreferences = context.getSharedPreferences(AppConstants.USER_DATA, Context.MODE_PRIVATE);
    }

    public LoginResponseDTO getUser() {
        String string = userPreferences.getString(AppConstants.USER_DATA, null);
        if (null != string)
            return gson.fromJson(string, LoginResponseDTO.class);
        else
            throw new RuntimeException("------------------------ UserDTO object not made when Logging in");
    }


    public void logInUser(LoginResponseDTO  dto) {
        String sDto = gson.toJson(dto);
        SharedPreferences.Editor edit = userPreferences.edit();
        edit.putString(AppConstants.USER_DATA,sDto);
        edit.apply();
    }

    @Override
    public void setToken(String token) {
        SharedPreferences.Editor edit = userPreferences.edit();
        edit.putString(AppConstants.TOKEN, token);
        edit.apply();
    }

    @Override
    public String getToken() {
        return userPreferences.getString(AppConstants.TOKEN, "");
    }

    public void clearAllData() {
        SharedPreferences.Editor edit = userPreferences.edit();
        edit.clear();
        edit.apply();
    }

}
