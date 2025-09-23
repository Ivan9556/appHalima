package com.example.halimaapp.network;

import com.example.halimaapp.models.Certificado;
import com.example.halimaapp.models.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/*
Interfaces

Definimos las solicitudes HTTP, con los endpoint y el tipo de request (tipo de petición)
Añadimos como párameto el objeto usuario el cual se envia en formato JSON
El metodo devuelve un Call<Certificado>, representa una llamada HTTP (respuesta) la cual se
deserializa en formato JSON a un objeto Certificado

*/
public interface Servicios {


    @POST("login")
    Call<Certificado> login(@Body Usuario usuario);

    @GET("consultar_reservas")
    Call<Certificado>reservas(@Header("Authorizacion") String token);
}
