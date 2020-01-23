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
import android.widget.Button;
import android.widget.TextView;

import com.example.projrcte.model.Restaurante;


/**
 * A simple {@link Fragment} subclass.
 */
public class TiendaFragment extends Fragment {

    ViewModel viewModel;

    TextView nombreTextView , descripcionTextView;
    Button button;

    public TiendaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tienda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(requireActivity()).get(ViewModel.class);

        nombreTextView = view.findViewById(R.id.nomd);
        descripcionTextView = view.findViewById(R.id.descd);


        viewModel.elementoSeleccionado.observe(getViewLifecycleOwner(), new Observer<Restaurante>() {
            @Override
            public void onChanged(final Restaurante restaurante) {
                if(restaurante == null) return;

                nombreTextView.setText(restaurante.nombre);
                descripcionTextView.setText(restaurante.descripcion);
            }
        });

        button = view.findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreTienda = nombreTextView.getText().toString();
                viewModel.rellenarListaCart(nombreTienda);
            }
        });
    }
}
