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
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.halimaapp.R;
import com.example.halimaapp.activities.MenuActivity;
import com.example.halimaapp.databinding.NewReservaBinding;
import com.example.halimaapp.network.Cliente;
import com.example.halimaapp.network.Servicios;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newReserva extends Fragment {

    private NewReservaBinding binding;
    private Servicios servicios;
    private NavController navController;
    private NavOptions navOptions;


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
        navOptions = new NavOptions.Builder().setPopUpTo(R.id.newReserva, true).build();
        binding.fecha1.setOnClickListener(v -> showMaterialDatePicker(binding.fecha1));
        binding.fecha2.setOnClickListener(v -> showMaterialDatePicker(binding.fecha2));

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
                        navController.navigate(R.id.reservaFragment, null, navOptions);
                        Toast.makeText(getContext(),"Reserva añadida",
                                Toast.LENGTH_SHORT).show();

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
                if(!isAdded() || binding == null || getContext() == null) return;
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"Fechas añadidas",
                            Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(!isAdded() || getContext() == null) return;
                Toast.makeText(getContext(),"No se puedo añadir fecha",
                            Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showMaterialDatePicker(TextInputEditText fecha){
        // 1. Crear el Builder del selector de fechas
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Selecciona una fecha");

        // Opcional: Puedes preseleccionar la fecha de hoy
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());

        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        // 2. Mostrar el selector (usando el FragmentManager del Fragment)
        materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");

        // 3. Configurar qué pasa cuando el usuario pulsa "Aceptar"
        materialDatePicker.addOnPositiveButtonClickListener(seleccion -> {

            // 'selection' es la fecha en milisegundos (Long)
            // Convertimos los milisegundos al formato "dd-MM-yyyy"
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Importante para evitar desfases de horas

            String fechaSeleccionada = sdf.format(new Date(seleccion));
            // Seteamos el String en el campo usando Binding
            fecha.setText(fechaSeleccionada);
        });

    }
}


