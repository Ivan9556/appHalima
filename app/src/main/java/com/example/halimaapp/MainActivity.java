package com.example.halimaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
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
    /* Interfaces
    Definimos las solicitudes HTTP, con los endpoint y el tipo de request (tipo de petición)
    Añadimos como párameto el objeto usuario el cual se envia en formato JSON
    El metodo devuelve un Call<Certificado>, representa una llamada HTTP (respuesta) la cual se
    deserializa en formato JSON a un objeto Certificado
     */

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

                //Variables EdiText
                EditText etcorreo = findViewById(R.id.correo);
                EditText etpass = findViewById(R.id.pass);

                /*Obtenemos el texto de las varibles del form:
                .getText() devuelve un Editable.
                .toString() lo convierte en String.
                .trim() elimina espacios en blanco al inicio/final
                */

                //Variables String
                String correo = etcorreo.getText().toString().trim();
                String pass = etpass.getText().toString().trim();

                //Comprobamos si el formulario tiene algun valor
                if(!correo.isEmpty() && !pass.isEmpty()){

                    //Instancia Usuarioqx
                    Usuario usuario = new Usuario(correo, pass);

                    //Instancia Retrofit
                    Retrofit retrofit = new Retrofit.Builder()
                            //URL Windows
                            //.baseUrl("http://192.168.1.141:8000/")
                            //URL Linux
                            //.baseUrl("http://192.168.1.145:8000/")
                            //Url Movil
                            .baseUrl("http://10.96.183.31:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    //Instancia interface para realizar llamadas
                    Servicios servicio = retrofit.create(Servicios.class);

                    /*
                    Llamamos al Servicio y utilizamos ".enqueue" para ejecutar la llamada de forma
                    asíncrona (un hilo separado) para no bloquear la interfaz del usuario
                     */

                    servicio.login(usuario).enqueue(new Callback<Certificado>() {
                        @Override //Respuesta del servidor
                        public void onResponse(Call<Certificado> call, Response<Certificado> response) {
                            /*
                            response.isSuccessful(), devuelve true si el código HTTP está en el rango 200–299.
                            response.body(), contiene el objeto Certificado deserializado desde JSON.
                             */
                            if(response.isSuccessful() && response.body() != null){
                                String token = response.body().getToken();
                                if(token != null){
                                   // Toast.makeText(getApplicationContext(),"Has iniciado sesion", Toast.LENGTH_SHORT).show();
                                   // Toast.makeText(getApplicationContext(),"El token es: " + token, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                    intent.putExtra("token_key", token);
                                    startActivity(intent);
                                    finish(); //Metodo para no retroceder tras el cambio de Activity
                                }else {
                                    Toast.makeText(getApplicationContext(), "No se ha establecido conexion", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        @Override //Si no obtenemos respuesta del servidor
                        public void onFailure(Call<Certificado> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    Toast.makeText(getApplicationContext(),"No has introducido datos", Toast.LENGTH_SHORT).show();
                }

            }


        });

    }

}

