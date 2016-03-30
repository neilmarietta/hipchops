package com.neilmarietta.hipchops.data.repository.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {

    private static String HTTPS_START_URI = "https://";
    private static String BASE_API_URL = "api.hipchat.com";

    /**
     * Personal Access Token.<p>
     * For Integration, Obtain token via OAuth 2.0 flow (https://www.hipchat.com/docs/apiv2/auth).
     */
    private static String AUTH_TOKEN = "HmCbgMNqEMslnQK0XkBx0dXnQDwz5FlCBCqJqtLI";

    public static Retrofit createAdapter() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + AUTH_TOKEN)
                                .build();
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(HTTPS_START_URI + BASE_API_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
