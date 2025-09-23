package com.example.halimaapp;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.halimaapp.databinding.FragmentReservaBinding;

public class ReservaFragment extends Fragment {

    private FragmentReservaBinding binding;
    private String token;
    MainActivity.Servicios sv;

    public ReservaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReservaBinding.inflate(inflater, container, false);

        //Token
        token = "Bearer " + ((MenuActivity) getActivity()).getToken();



        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // evitar fugas de memoria
    }
}
