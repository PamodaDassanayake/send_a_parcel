package com.myapp.courier.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.myapp.courier.usermanager.UserManager;
import com.myapp.courier.usermanager.UserManagerImpl;
import com.myapp.courier.utils.AppConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final int CONNECTION_TIMEOUT = 120000;
    private static final int DATA_TIMEOUT = 120000;

    static String toastMessage = "";

    private UserManager userManager;
    private static String token = "";
    private String header = "";
    private static Retrofit retrofit = null;


    public ApiClient(Context context, AuthType authType) {
        switch (authType) {
            case AUTHENTICATED: {
                this.userManager = new UserManagerImpl(context);
                this.token = userManager.getToken();
                break;
            }
        }
    }

    public static Retrofit getInstance() {

        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DATA_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DATA_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            if (!token.isEmpty()) {
                                System.out.println("========== TOKEN NOT EMPTY -------> " + token);
                                request = chain.request().newBuilder().addHeader(AppConstants.HEADER, token).build();
                            }

                            System.out.println("HEADER =========== " + request.header(AppConstants.HEADER) + "- ");

                            Response response = chain.proceed(request);

                            switch (response.code()) {
                                case 500:
                                    toastMessage = "Server Error.. Please Try Again Later";
                                    break;
                                case 404:
                                    toastMessage = "Error finding resource.. Please inform the App support about this. Thank You!";
                                    break;
                                default:
                                    toastMessage = "Unknown Error.. Please try again later ";
                            }
                            response.header(toastMessage);
                            return response;
                        }
                    })

                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL_ANDROID)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

    public static void backgroundThreadShortToast(final Context context) {
        if (context != null && toastMessage != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public static ApiServices getUserService(){
        ApiServices userService = getInstance().create(ApiServices.class);
        return userService;
    }
}
