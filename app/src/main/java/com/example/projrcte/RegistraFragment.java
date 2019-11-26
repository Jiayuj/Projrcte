package com.example.projrcte;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


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

        emaileditText.findViewById(R.id.edit_text_user);
        passedittext.findViewById(R.id.edit_text_pass);

        view.findViewById(R.id.button_registra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaileditText.getText().toString();
                String pass = passedittext.getText().toString();

                viewModel.registraUser(email,pass);
            }
        });
    }
}
