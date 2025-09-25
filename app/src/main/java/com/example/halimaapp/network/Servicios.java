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

Definimos las solicitudes HTTP con sus endpoints y el tipo de petición (request).
Añadimos como parámetro el objeto Usuario, el cual se envía en formato JSON.
El metodo devuelve un Call<Certificado>, que representa una llamada HTTP cuya
respuesta se deserializa automáticamente a un objeto Certificado en formato JSON.

ResponseBody permite recibir el contenido de la respuesta HTTP tal como lo envía
el servidor, en formato byte[] o InputStream.
En este caso, el endpoint de Flask devuelve una lista JSON de reservas, lo cual
no es compatible con la estructura de Certificado; por ello, response.body()
devuelve null si intentamos mapearlo a ese tipo.
*/
public interface Servicios {

    @POST("login")
    Call<Certificado> login(@Body Usuario usuario);

    @GET("consultar_reservas")
    Call<ResponseBody> reservas(@Header("Authorization") String token);
}

