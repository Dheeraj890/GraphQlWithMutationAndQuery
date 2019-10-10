package com.example.graphqlapiimplementation;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApolloClient {

    public static final String baseUrl = "http://192.168.0.108:8080/graphql/schema.json";

    private static ApolloClient apolloClient;

    private MyApolloClient(){}

    public static ApolloClient getMyApolloClient() {
        HttpLoggingInterceptor  httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        apolloClient = ApolloClient.builder().serverUrl(baseUrl).okHttpClient(okHttpClient).build();


        return apolloClient;
    }
}
