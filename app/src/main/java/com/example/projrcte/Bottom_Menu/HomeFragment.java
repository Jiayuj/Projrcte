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

import com.example.projrcte.model.Restaurante;
import com.example.projrcte.R;
import com.example.projrcte.ViewModel;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    ViewModel viewModel1;
    NavController navController;
    ElementosAdapter elementosAdapter;


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

        viewModel1 = ViewModelProviders.of(requireActivity()).get(ViewModel.class);
        navController = Navigation.findNavController(view);

        RecyclerView elementosRecyclerView = view.findViewById(R.id.item_list);
        elementosRecyclerView.addItemDecoration(new DividerItemDecoration(elementosRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        elementosAdapter = new ElementosAdapter();
        elementosRecyclerView.setAdapter(elementosAdapter);

        viewModel1.listaElementos.observe(getViewLifecycleOwner(), new Observer<List<Restaurante>>() {
            @Override
            public void onChanged(List<Restaurante> restaurantes) {
                elementosAdapter.establecerListaElementos(restaurantes);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loca: Navigation.findNavController(v).navigate(R.id.locationFragment); break;
        }

    }


    class ElementosAdapter extends RecyclerView.Adapter<ElementosAdapter.ElementoViewHolder>{

        List<Restaurante> restaurantes;

        @NonNull
        @Override
        public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_elemento, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElementoViewHolder holder, final int position) {

            final Restaurante restaurante = restaurantes.get(position);

            holder.nombreTextView.setText(restaurante.nombre);
            holder.descripcionTextView.setText(restaurante.descripcion);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel1.establecerElementoSeleccionado(restaurante);
                    navController.navigate(R.id.tiendaFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return restaurantes == null ? 0 : restaurantes.size();
        }

        public void establecerListaElementos(List<Restaurante> restaurantes){
            this.restaurantes = restaurantes;
            notifyDataSetChanged();
        }

        class ElementoViewHolder extends RecyclerView.ViewHolder {
            TextView nombreTextView, descripcionTextView;

            public ElementoViewHolder(@NonNull View itemView) {
                super(itemView);
                nombreTextView = itemView.findViewById(R.id.textview_nombre);
                descripcionTextView = itemView.findViewById(R.id.textview_descripcion);
            }
        }
    }
}
