package com.example.halimaapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.halimaapp.databinding.FragmentMensajesBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MensajesFragment extends Fragment {

    private FragmentMensajesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMensajesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //View.OnClickListener listener = v -> showMaterialDatePicker();

        binding.fSeleccionada.setOnClickListener(v -> showMaterialDatePicker());


    }
    private void showMaterialDatePicker(){
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
            binding.fSeleccionada.setText(fechaSeleccionada);

        });

    }
}