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
        binding = FragmentReservaBinding.inflate(inflater, container, false);


        //Instanciamos servicios sobre el cliente
        servicios = cliente.getCliente().create(Servicios.class);

        //Token
        token = "Bearer " + ((MenuActivity) getActivity()).getToken();

        //Se añade el token a la llamada
        Call<Certificado> call = servicios.reservas(token);

        //Llamamos al servicio
        call.enqueue(new Callback<Certificado>() {
            @Override
            public void onResponse(Call<Certificado> call, Response<Certificado> response) {
                if (response.isSuccessful() && response.body() != null){
                    Certificado cer = response.body();
                    Log.d("Los datos son ", cer.getToken());
                    Toast.makeText(getContext(), "Todo correcto" + cer.getToken() , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "No hay datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Certificado> call, Throwable t) {
                Toast.makeText(getContext(), "No hay respuesta", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // evitar fugas de memoria
    }
}
