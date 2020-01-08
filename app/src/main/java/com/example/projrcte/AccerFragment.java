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

import com.example.projrcte.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccerFragment extends Fragment {

    NavController navController;
    ViewModel viewModel;
    EditText emaileditText, passedittext;

    public AccerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        viewModel = ViewModelProviders.of(requireActivity()).get(ViewModel.class);

        emaileditText = view.findViewById(R.id.accer_user);
        passedittext = view.findViewById(R.id.accer_pass);

        view.findViewById(R.id.button_accer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaileditText.getText().toString();
                String pass = passedittext.getText().toString();
                User i = viewModel.comprobarAccer(email,pass);
                try {
                    if (i.getEmail().equals(email)&& i.getPass().equals(pass)){
                        Navigation.findNavController(v).navigate(R.id.homefragment);
                    }
                }catch (NullPointerException e){
                    Navigation.findNavController(v).navigate(R.id.accerFragment);
                }

            }
        });

        view.findViewById(R.id.text_view_registra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.registraFragment);
            }
        });


    }
}
