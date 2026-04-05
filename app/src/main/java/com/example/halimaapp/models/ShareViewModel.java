package com.example.halimaapp.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {

    //Se usa MutableLiveData para que los fragments puedan relacionar los cambios
    private final MutableLiveData<Reserva> rs = new MutableLiveData<>();

    public void objectSelect(Reserva reserva){
        rs.setValue(reserva);
    }
    public MutableLiveData<Reserva> getRs() {
        return rs;
    }
}
