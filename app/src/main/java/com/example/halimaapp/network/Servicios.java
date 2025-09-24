package com.example.halimaapp.network;

import com.example.halimaapp.models.Certificado;
import com.example.halimaapp.models.Usuario;

import okhttp3.ResponseBody;
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

ResponseBody recibes eel contenido crudo de la respuesta HTTP,
tal como lo envía el servidor, en formato byte[] o InputStream.
El endpoint Flask devuelve una lista JSON de reservas, no compatible con
la estrucutra de Certificado por lo que response.body() devuelve null
*/
public interface Servicios {


    @POST("login")
    Call<Certificado> login(@Body Usuario usuario);

    @GET("consultar_reservas")
    Call<ResponseBody> reservas(@Header("Authorization") String token);
}
