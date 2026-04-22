package com.example.halimaapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.halimaapp.R;
import com.example.halimaapp.activities.MenuActivity;
import com.example.halimaapp.databinding.DetailCardViewBinding;
import com.example.halimaapp.models.ShareViewModel;
import com.example.halimaapp.network.Cliente;
import com.example.halimaapp.network.Servicios;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detailCardFragment extends Fragment {

        private DetailCardViewBinding binding;
        private ShareViewModel svm;
        private Servicios sv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DetailCardViewBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Cliente
        Cliente cliente = new Cliente();
        sv = cliente.getCliente().create(Servicios.class);

        // Recoge la instancia del activity
       svm = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

       // Procede cambiar los textView del fragment
       svm.getRs().observe(getViewLifecycleOwner(), reserva -> {
           int id = reserva.getId();
           binding.idReserva.setText(String.valueOf(id));
           binding.textNom.setText(reserva.getNombre());
           binding.textApe.setText(reserva.getApellidos());
           binding.textFechaEntrada.setText(reserva.getFecha_entrada());
           binding.textFechaSalida.setText(reserva.getFecha_salida());
           binding.numAdultos.setText(reserva.getNumero_adultos());
           binding.numNinos.setText(reserva.getNumero_ninos());
           binding.telefono.setText(reserva.getTelefono());
           binding.correo.setText(reserva.getCorreo());
           binding.precioReserva.setText(reserva.getPrecio_reserva());

           binding.botonBorrar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (getActivity() instanceof MenuActivity){
                       String token = ((((MenuActivity) getActivity()).getToken()));
                       delete("Bearer " + token, id);
                       Toast.makeText(getContext(), "TODO OKEY",
                               Toast.LENGTH_SHORT).show();

                   }
               }
           });

       });


    }
    public void delete(String token, int id){
        Call<ResponseBody> call = sv.borrar_reserva(token, id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(), "Reserva eliminada correctamente",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Reserva NO eliminada correctamente",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
