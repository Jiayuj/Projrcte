package com.example.projrcte.Bottom_Menu;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projrcte.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    ImageView imageView;


    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.loca).setOnClickListener(this);
        view.findViewById(R.id.tienda_icon).setOnClickListener(this);
        view.findViewById(R.id.tienda_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tienda_icon: Navigation.findNavController(v).navigate(R.id.tienda); break;
            case R.id.tienda_text: Navigation.findNavController(v).navigate(R.id.tienda); break;
            case R.id.loca: Navigation.findNavController(v).navigate(R.id.location); break;
        }

    }
}
