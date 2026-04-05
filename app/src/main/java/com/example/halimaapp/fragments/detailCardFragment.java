package com.example.halimaapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.halimaapp.R;
import com.example.halimaapp.databinding.DetailCardViewBinding;
import com.example.halimaapp.models.ShareViewModel;

public class detailCardFragment extends Fragment {

        DetailCardViewBinding binding;
        ShareViewModel svm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DetailCardViewBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

       // Recoge la instancia del activity
       svm = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

       // Procede cambiar los textView del fragment
       svm.getRs().observe(getViewLifecycleOwner(), reserva -> {
           // Nombre
           binding.textNom.setText(reserva.getNombre());
           binding.textApe.setText(reserva.getApellidos());
           binding.textFechaEntrada.setText(reserva.getFecha_entrada());
           binding.textFechaSalida.setText(reserva.getFecha_salida());
           binding.numAdultos.setText(reserva.getNumero_adultos());
           binding.numNinos.setText(reserva.getNumero_ninos());
           binding.telefono.setText(reserva.getTelefono());
           binding.correo.setText(reserva.getCorreo());
           binding.precioReserva.setText(reserva.getPrecio_reserva());

       });


    }
}
