package com.example.halimaapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.halimaapp.databinding.DetailCardViewBinding;

public class detailCardFragment extends Fragment {

        DetailCardViewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DetailCardViewBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

}
