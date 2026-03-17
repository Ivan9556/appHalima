package com.example.halimaapp.fragments;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.halimaapp.activities.MenuActivity;
import com.example.halimaapp.databinding.FragmentReservaBinding;
import com.example.halimaapp.models.AdaptadorReserva;
import com.example.halimaapp.models.Reserva;
import com.example.halimaapp.network.Cliente;
import com.example.halimaapp.network.Servicios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaFragment extends Fragment {

    private FragmentReservaBinding binding;
    private Servicios servicios;

    public ReservaFragment() {
        // Required empty public constructor
    }

    // onCreateView solo sirve para inflar la vista
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflamos la vista del fragment usando ViewBinding; inflate es un traductor que existe
        entre el archivo de diseño (XML) y la lógica (Java/Kotlin).
           Lectura: Le decimos a Android que lea el archivo XML
           Instancicacion: Por cada etiqueta que haya (button, TextView, etc) Android
           crea un objeto Java real en la memoria.
           Jerarquía: Los añade todos dentro de una "caja" principal (el Root)
        */
        binding = FragmentReservaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // onViewCreate sirve para el resto acciones (llamadasa a la API, configuración, lógica)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        Instanciamos la interfaz de servicios de Retrofit
        cliente.getCliente()' devuelve el Retrofit configurado con la base URL y Gson
        create(Servicios.class)' devuelve un objeto que implementa la interfaz Servicios
        */
        Cliente cliente = new Cliente();
        servicios = cliente.getCliente().create(Servicios.class);

        /*
         Preparamos el token de autorización
         Obtenemos el token guardado en la Activity principal (MenuActivity)
         y le añadimos "Bearer " como requiere el estándar HTTP Authorization
        */
        if (getActivity() instanceof MenuActivity) {
            String token = (((MenuActivity) getActivity()).getToken());
            // "Bearer " es el esquema de autentificación (estándar HTTP Authentication)
            cargaReservas("Bearer " + token);
        }
    }

    //Metodo realizar peticiones
    private void cargaReservas(String token) {
        Call<ResponseBody> call = servicios.reservas(token);

        // Ejecutamos la llamada de forma asíncrona
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Verificar si el fragment sigue "vivo" antes de tocar la UI
                //Nos ayuda a que mientras las API carga, la app no crashee
                if (!isAdded() || binding == null) return;

                // Se llama si la petición HTTP responde correctamente
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Obtenemos el JSON como String
                        String datos = response.body().string();
                        // Convertimos el JSON en una LISTA de tipo "Reserva"
                        Gson gson = new Gson();
                        Type tipoLista = new TypeToken<ArrayList<Reserva>>(){}.getType();
                        ArrayList<Reserva> listaReservas = gson.fromJson(datos, tipoLista);

                        //2. Le pasamos esa lista al AdaptadorReservas
                        //El adaptador es el que se encarga de usar los getter de la clase Reserva
                        AdaptadorReserva adaptador = new AdaptadorReserva(listaReservas);

                        // 3. Le indicamos al RecyclerView del XML que use el adaptador
                        binding.recyclerViewReservas.setAdapter(adaptador);

                        // Por ultimo indicamos que se comporte como un lista vertical
                        binding.recyclerViewReservas.setLayoutManager(new LinearLayoutManager
                                (getContext()));

                        // Imprimimos en Logcat para depuración
                        Log.d("Reservas:", datos);
                    } catch (IOException e) {
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

            ;
        });
    }
    @Override
    public void onDestroyView() {
        /*
        Evitamos qu el Fragemnt siga vivo aunque su "vista" haya sido destruida. Ya que
        si no se limpia el binding, que queda guardado en memoria.
        */
        super.onDestroyView();
        binding = null; // evitar fugas de memoria
    }
}
