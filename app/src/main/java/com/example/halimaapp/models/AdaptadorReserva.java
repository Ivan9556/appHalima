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
// *AdaptadorReserva.ViewHolder* es genérico, idicanmos el tipo de ViewHolder que va a usar el
// adaptador. Siento ViewHolder el patrón de diseño en Android es un componente de diseño esencial
// para RecyclerView y optimizar el rendimiento y al almacenar referencias de vistas (findViewById)
public class AdaptadorReserva extends RecyclerView.Adapter<AdaptadorReserva.ViewHolder> {

    // 2. Variable de la clase, lista de objetos tipo "Cliente" que crea el JSON
    private List<Reserva> listaReservas;
    // 3. El constructor, lo usamos para recibir la lista del Fragment
    public AdaptadorReserva(List<Reserva> lista) {
        this.listaReservas = lista;
    }
    // 4. onCreateViewHolder: Crea una capsula física que se obtiene del XML (card_view)
    // la conviertes en un objeto View (se le conoce como inflar)
    @NonNull
    @Override
    public AdaptadorReserva.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()) // Inflamos la vista
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view); // Devolvemos la vista creada
    }
    /*5. onBindViewHolder: Toma el objeto de la lista en la posición (listaReservas.get(position)
     y usa el setText para "Bind" o vincular el nombre y apellido en los TextView que tiene el
     holder. Esta función recibe 2 parámetros:
      -holder: Es una instancia de la clase ViewHolder, esta instancia se usa como contenedor
              de la Views, su funcion principal el almacenamiento en caché de las referencias de
              los subcomponentes para evitar llamada repeditas y eliminando el alto nivel de cómputo
      -posión: La posición del objeto dentro de la lista.
     */
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

    /*
    8. Class ViewHolder, es el contenedor de las vistas de cada capsula. Evita que Android tenga
     que buscar por IDs por cada fila, mejorando el rendimiento. El atributo itemView es una
     propiedad heredada de la clase base RecyclerView.ViewHolder. Representa la instancia de la
     vista raíz (root view) del layout inflado para cada elemento de la lista. Se utiliza como
     el contexto de búsqueda para localizar los subcomponentes mediante findViewById y como
     el componente principal para gestionar los eventos de interacción (clics) a nivel de celda
     completa.
    */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNom, textApe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNom = itemView.findViewById(R.id.textNom);
            textApe = itemView.findViewById(R.id.textApe);
        }
    }
}
