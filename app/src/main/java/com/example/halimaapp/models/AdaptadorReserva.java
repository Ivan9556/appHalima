package com.example.halimaapp.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.halimaapp.R;
import java.util.List;

// 1. Heredamos de RecyclerView.Adapter para que Android sepa que esta clase controla una lista
public class AdaptadorReserva extends RecyclerView.Adapter<AdaptadorReserva.ViewHolder> {

    // 2. Variable de la clase, lista de objetos tipo "Cliente" que sacamos del JSON
    private List<Reserva> listaReservas;
    // 3. El constructor, lo usamos para recibir la lista del Fragment
    public AdaptadorReserva(List<Reserva> lista) {
        this.listaReservas = lista;
    }
    // 4. onCreateViewHolder, se llama cuando el RecyclerView necesita una capsula nueva
    @NonNull
    @Override
    public AdaptadorReserva.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el diseño con el XML definido
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }
    //5. onBindViewHolder, se llama para llenar lo datos de la capsula
    @Override
    public void onBindViewHolder(@NonNull AdaptadorReserva.ViewHolder holder, int position) {
        // Obtenemos la reserva

        Reserva n_reserva = listaReservas.get(position);

        // Usamos metodo get para obtener la info en los TextViews
        holder.textNom.setText(n_reserva.getNom());
        holder.textApe.setText(n_reserva.getApe());

        // 6. Añadimos un evento Click para abrir la reserva
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Abriendo reserva de: "+ n_reserva.getNom(),
                    Toast.LENGTH_SHORT).show();
            // Bloque para saltar al Fragment "detalle"
        });
    }

    // 7. Le dice al RecyclerView cuantos elementos hay en total
    @Override
    public int getItemCount() {
        return listaReservas.size();
    }

    // 8. Class ViewHolder, es el contenedor de las vistas de cada capsula
    // Evita que Android tenga que buscar por IDs por cada fila, mejorando el rendimiento.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNom, textApe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNom = itemView.findViewById(R.id.textNom);
            textApe = itemView.findViewById(R.id.textApe);
        }
    }
}
