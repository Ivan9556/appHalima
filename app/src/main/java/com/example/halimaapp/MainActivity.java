package com.example.halimaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.GET;


public class MainActivity extends AppCompatActivity {
    //Clases
    public class Usuario{
        private String correoElectronico;
        private String password;

        public Usuario(String correoElectronico, String password) {
            this.correoElectronico = correoElectronico;
            this.password = password;
        }
    }

    public class Certificado{
        private String token;

        public String getToken() {
            return token;
        }
    }
    //Interfaces
    public interface Servicios{
        @POST("/login")
        Call<Certificado> login(@Body Usuario usuario);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        //Variables
        Button inicioSesion = findViewById(R.id.incio);
        //Formulario
        EditText etcorreo = findViewById(R.id.correo);
        EditText etpass = findViewById(R.id.pass);
        /*Obtenemos el texto de las varibles del form:
        .getText() devuelve un Editable.
        .toString() lo convierte en String.
        .trim() elimina espacios en blanco al inicio/final
        */
        String correo = etcorreo.getText().toString().trim();
        String pass = etpass.getText().toString().trim();

        //Instancia Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //URL Windows
               // .baseUrl("http://192.168.1.141:8000/")
                //URL Linux
                .baseUrl("http://192.168.1.145:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Instancia interface
        Servicios servicio = retrofit.create(Servicios.class);

        //Hacer login
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usurio = new Usuario(correo, pass);
                servicio.login(usurio).enqueue(new Callback<Certificado>() {
                    @Override
                    public void onResponse(Call<Certificado> call, Response<Certificado> response) {
                        if(response.isSuccessful() && response.body() != null){
                            String token = response.body().getToken();
                            Toast.makeText(getApplicationContext(), "Has iniciado sesion", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Certificado> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error de inicio", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





    }

}

