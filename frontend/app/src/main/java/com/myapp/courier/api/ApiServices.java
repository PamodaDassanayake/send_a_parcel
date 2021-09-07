package com.myapp.courier.api;

import com.myapp.courier.dto.OrderDetailsByIdDTO;
import com.myapp.courier.dto.UpdateOrderStatusDTO;
import com.myapp.courier.dto.UserDetailsDTO;
import com.myapp.courier.dto.UserRequestDTO;
import com.myapp.courier.dto.LoadIrdersByStatusResDTO;
import com.myapp.courier.dto.LoadOrderByStatusDTO;
import com.myapp.courier.dto.LoginRequestDTO;
import com.myapp.courier.dto.LoginResponseDTO;
import com.myapp.courier.dto.SaveOrderDTO;
import com.myapp.courier.dto.UserResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    @POST("auth/register")
    Call<UserResponseDTO> saveUser(@Body UserRequestDTO userRequestDTO);

    @POST("auth/login")
    Call<LoginResponseDTO> logUser(@Body LoginRequestDTO loginRequestDTO);

    @POST("order")
    Call<SaveOrderDTO> saveOrder(@Body SaveOrderDTO saveOrderDTO);

    @GET("order/status")
    Call<LoadIrdersByStatusResDTO> laodOrdersByStatus(@Body LoadOrderByStatusDTO loadOrderByStatusDTO);

    @GET("order/{id}")
    Call<OrderDetailsByIdDTO> callForOrderDetails(@Path("id") String OrderId);

    @PUT("app-user/updateOrder")
    Call<UpdateOrderStatusDTO> callForUpdateOrder(@Body UpdateOrderStatusDTO updateOrderStatusDTO);

    @GET("user/{id}")
    Call<UserDetailsDTO> callForUserDetails(@Path("id") String UserId);
}
