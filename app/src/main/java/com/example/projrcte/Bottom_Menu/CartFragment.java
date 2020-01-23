package com.example.projrcte.Bottom_Menu;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projrcte.R;
import com.example.projrcte.ViewModel;
import com.example.projrcte.model.Restaurante;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    ViewModel viewModel1;
    NavController navController;
    ElementosCartAdapter elementosCartAdapter;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel1 = ViewModelProviders.of(requireActivity()).get(ViewModel.class);
        navController = Navigation.findNavController(view);

        RecyclerView elementosRecyclerView = view.findViewById(R.id.item_list1);
        elementosRecyclerView.addItemDecoration(new DividerItemDecoration(elementosRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        elementosCartAdapter = new ElementosCartAdapter();
        elementosRecyclerView.setAdapter(elementosCartAdapter);

        viewModel1.listaCart.observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> restaurantes) {
                elementosCartAdapter.establecerListCart(restaurantes);
            }
        });

    }


    class ElementosCartAdapter extends RecyclerView.Adapter<ElementosCartAdapter.ElementoCartViewHolder>{

        List<String> restaurantes;

        @NonNull
        @Override
        public ElementoCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementoCartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElementoCartViewHolder holder, final int position) {


            holder.nombre.setText(restaurantes.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel1.establecerElementoCart(restaurantes.get(position));
                    navController.navigate(R.id.detalleCartFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return restaurantes == null ? 0 : restaurantes.size();
        }

        public void establecerListCart(List<String> restaurantes){
            this.restaurantes = restaurantes;
            notifyDataSetChanged();
        }

        class ElementoCartViewHolder extends RecyclerView.ViewHolder {
            TextView nombre;

            public ElementoCartViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.nombre_tienda);
            }
        }
    }

}


