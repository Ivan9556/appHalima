package com.example.halimaapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cliente {

    public  Retrofit getCliente(){
        return new Retrofit.Builder()
                // URL Windows
                //.baseUrl("http://192.168.1.141:8000/")
                // URL Linux
                .baseUrl("http://192.168.1.145:8000/")
                // URL Móvil
                //.baseUrl("http://10.96.183.31:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
