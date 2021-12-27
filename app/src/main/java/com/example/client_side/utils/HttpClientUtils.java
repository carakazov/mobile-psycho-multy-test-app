package com.example.client_side.utils;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@UtilityClass
public class HttpClientUtils {
    @Setter
    @Getter
    private String token;

    @Getter
    @Setter
    private String role;

    public static OkHttpClient getClientWithHeader() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + token).build();
                        return chain.proceed(request);
                    }
                }).build();

        return client;
    }
}
