package com.example.projrcte;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistraFragment extends Fragment {

    NavController navController;
    ViewModel viewModel;
    EditText emaileditText, passedittext;


    public RegistraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registra, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        viewModel = ViewModelProviders.of(requireActivity()).get(ViewModel.class);

        emaileditText = view.findViewById(R.id.regis_user);
        passedittext = view.findViewById(R.id.regis_pass);

        view.findViewById(R.id.button_registra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaileditText.getText().toString();
                String pass = passedittext.getText().toString();
                try {
                    if (viewModel.comprobar(email,pass).equals(email)){
                        Navigation.findNavController(v).navigate(R.id.registraFragment);
                    }
                }catch (NullPointerException e){
                    viewModel.registraUser(email,pass);
                    Navigation.findNavController(v).navigate(R.id.homefragment);
                }

            }
        });
    }
}
