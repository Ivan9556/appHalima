package com.example.halimaapp.models;

public class  Reserva {

    /* @SerializedName le dice a GSON: "nombre" del JSON va a esta variable
    @SerializedName("nombre")
    private String nom;
    @SerializedName("apellidos")
    private String ape;
    */


    private int id;
    private String nombre,
            apellidos,
            fecha_entrada,
            fecha_salida,
            numero_adultos,
            numero_ninos,
            telefono,
            correo,
            precio_reserva;

    public Reserva() {
    }

    public Reserva(int id, String nombre, String apellidos, String fecha_entrada, String fecha_salida,
                   String numero_adultos, String numero_ninos, String telefono, String corrio,
                   String precio_reserva) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
        this.numero_adultos = numero_adultos;
        this.numero_ninos = numero_ninos;
        this.telefono = telefono;
        this.correo = corrio;
        this.precio_reserva = precio_reserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_entrada() {
        return fecha_entrada;
    }

    public void setFecha_entrada(String fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getNumero_adultos() {
        return numero_adultos;
    }

    public void setNumero_adultos(String numero_adultos) {
        this.numero_adultos = numero_adultos;
    }

    public String getNumero_ninos() {
        return numero_ninos;
    }

    public void setNumero_ninos(String numero_ninos) {
        this.numero_ninos = numero_ninos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPrecio_reserva() {
        return precio_reserva;
    }

    public void setPrecio_reserva(String precio_reserva) {
        this.precio_reserva = precio_reserva;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


}
