package com.example.halimaapp;

import static com.example.halimaapp.R.id.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.halimaapp.databinding.ActivityMainBinding;
import com.example.halimaapp.databinding.ActivityMenuBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    /*
       Uso de ViewBinding:
       Se usa para reemplazar el clasico findViewById, el cual generar automáticamente una clase por
       cada layout XML. Una forma segura para acceder a las vistas dentro del Layout. Por lo que
       representa el activity_menu.xml
     */
    private ActivityMenuBinding binding;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Metodo inflate: crea en memoria el layout activity_menu.xml y devuelve la instancia
        de la clase generada que contiene todas las vistas layout (mapeamos las vistas en el objeto
        binding).
         */
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //getRoot(): Obtiene la vista

        //Recuperamos el token
        token = getIntent().getStringExtra("token_key");
        Toast.makeText(getApplicationContext(),"El token es: " + token, Toast.LENGTH_SHORT).show();

        //Style barra navegacion
        BottomNavigationView barra = binding.navView;
        barra.setItemActiveIndicatorEnabled(false); //eliminar indicador

        //Barra superior del activity
        MaterialToolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        /*  NavController: Maneja toda la navegación.
            Primero obtener el host navHostFragment con getSupportFragmentManager() del XML,nreferenciado con findFragmentById()
            para representar los distintos fragments.
            getNavController(): Devuelve el navController asociado a NavHostFragment.
        */
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        NavController navController = navHostFragment.getNavController();

        /* AppBarConfiguration
        Define cuales son los destinos principales y muestra el título correcto del fragmento actual.
         */
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.usuarioFragment, R.id.reservaFragment, R.id.mensajesFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
