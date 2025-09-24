package com.example.halimaapp.fragments;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.halimaapp.activities.MainActivity;
import com.example.halimaapp.activities.MenuActivity;
import com.example.halimaapp.databinding.FragmentReservaBinding;
import com.example.halimaapp.models.Certificado;
import com.example.halimaapp.network.Cliente;
import com.example.halimaapp.network.Servicios;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaFragment extends Fragment {

    private FragmentReservaBinding binding;
    private String token;
    private Cliente cliente = new Cliente();
    private Servicios servicios;

    public ReservaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos la vista del fragment usando ViewBinding
        binding = FragmentReservaBinding.inflate(inflater, container, false);

        /*
        Instanciamos la interfaz de servicios de Retrofit
        cliente.getCliente()' devuelve el Retrofit configurado con la base URL y Gson
        create(Servicios.class)' devuelve un objeto que implementa la interfaz Servicios
        */

        servicios = cliente.getCliente().create(Servicios.class);

        /*
         Preparamos el token de autorización
         Obtenemos el token guardado en la Activity principal (MenuActivity)
         y le añadimos "Bearer " como requiere el estándar HTTP Authorization
        */

        token = "Bearer " + ((MenuActivity) getActivity()).getToken();

        // Creamos la llamada Retrofit

        Call<ResponseBody> call = servicios.reservas(token);

        // Ejecutamos la llamada de forma asíncrona
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Se llama si la petición HTTP responde correctamente
                if(response.isSuccessful() && response.body() != null){
                    try {
                        // Obtenemos el JSON como String
                        String datos = response.body().string();
                        // Imprimimos en Logcat para depuración
                        Log.d("Reservas:", datos);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else {
                    // Si la respuesta no es exitosa o el body es null
                    Toast.makeText(getContext(), "No hay datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Si no pudo completar la llamada
                Toast.makeText(getContext(), "No hay respuesta", Toast.LENGTH_SHORT).show();
            }
        });

        // Devolvemos la vista del fragment
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // evitar fugas de memoria
    }
}
