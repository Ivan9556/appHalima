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
import retrofit2.http.POST;


public class MainActivity extends AppCompatActivity {

    //Clases
    public class Usuario{
        private String user;
        private String userpass;

        public Usuario(String user, String userpass) {
            this.user = user;
            this.userpass = userpass;
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
        @POST("login")
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

        //Variable Boton inico
        Button inicioSesion = findViewById(R.id.incio);

        //Hacer login
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Formulario
                EditText etcorreo = findViewById(R.id.correo);
                EditText etpass = findViewById(R.id.pass);

                /*Obtenemos el texto de las varibles del form:
                .getText() devuelve un Editable.
                .toString() lo convierte en String.
                .trim() elimina espacios en blanco al inicio/final
                */

                //Variables
                String correo = etcorreo.getText().toString();
                String pass = etpass.getText().toString();

                //Comprobamos si el formulario tiene algun valor
                if(!correo.isEmpty() && !pass.isEmpty()){

                    //Instancia Usuarioqx
                    Usuario usuario = new Usuario(correo, pass);

                    //Instancia Retrofit
                    Retrofit retrofit = new Retrofit.Builder()
                            //URL Windows
                            //.baseUrl("http://192.168.1.141:8000/")
                            //URL Linux
                            .baseUrl("http://192.168.1.145:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    //Instancia interface
                    Servicios servicio = retrofit.create(Servicios.class);

                    //Servicios
                    servicio.login(usuario).enqueue(new Callback<Certificado>() {
                        @Override
                        public void onResponse(Call<Certificado> call, Response<Certificado> response) {

                            if(response.isSuccessful() && response.body() != null){
                                Toast.makeText(getApplicationContext(), "Has iniciado sesion", Toast.LENGTH_SHORT).show();
                                String token = response.body().getToken();
                                Toast.makeText(getApplicationContext(),"El token es: " + token, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void onFailure(Call<Certificado> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error de inicio", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    Toast.makeText(getApplicationContext(),"No has introducido datos", Toast.LENGTH_SHORT).show();
                }

            }


        });

    }

}

