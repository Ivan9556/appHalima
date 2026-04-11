package com.example.halimaapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.halimaapp.R;
import com.example.halimaapp.activities.MenuActivity;
import com.example.halimaapp.databinding.NewReservaBinding;
import com.example.halimaapp.network.Cliente;
import com.example.halimaapp.network.Servicios;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newReserva extends Fragment {

    private NewReservaBinding binding;
    private Servicios servicios;

    private NavController navController;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NewReservaBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Cliente cliente = new Cliente();
        servicios = cliente.getCliente().create(Servicios.class);
        Button bt = binding.button;
        navController = Navigation.findNavController(view);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recuperamos el token del activity
                if (getActivity() instanceof MenuActivity){
                    String token = ((((MenuActivity) getActivity()).getToken()));
                    String fecha_entrada = binding.fecha1.getText().toString();
                    String fecha_salida = binding.fecha2.getText().toString();
                    String[] fechas = {fecha_entrada, fecha_salida};

                    if(!fecha_entrada.isEmpty() && !fecha_salida.isEmpty()){
                        añadir_reserva("Bearer " + token, fechas);
                        navController.navigate(R.id.reservaFragment);

                    }else {
                        Toast.makeText(getContext(),"No has introducido ninguna fecha",
                                Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getContext(),"No hay token",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void añadir_reserva(String token, String[] fechas){
        Call<ResponseBody> call = servicios.nueva_reserva(token, fechas);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(),"Fechas añadidas",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"No se puedo añadir fecha",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}


