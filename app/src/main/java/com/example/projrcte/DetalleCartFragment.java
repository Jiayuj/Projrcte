package com.example.projrcte;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.projrcte.model.Restaurante;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleCartFragment extends Fragment {

    ViewModel principalViewModel;

    TextView nombreTextView;

    public DetalleCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        principalViewModel = ViewModelProviders.of(requireActivity()).get(ViewModel.class);

        nombreTextView = view.findViewById(R.id.nombre_tienda1);

        principalViewModel.elementoCart.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(final String restaurante) {
                if (restaurante == null) return;
                nombreTextView.setText(restaurante);
            }
        });
    }
}
