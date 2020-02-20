package com.example.projrcte;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.projrcte.model.Protucto;
import com.example.projrcte.model.Restaurante;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import static com.example.projrcte.R.id.button_add;
import static com.example.projrcte.R.id.login;
import static com.example.projrcte.R.id.nombre_protucto;


/**
 * A simple {@link Fragment} subclass.
 */
public class TiendaFragment extends Fragment {

    ViewModel viewModel;
    ProtuctoAdapter protuctoAdapter;

    TextView nombreTextView , descripcionTextView;

    Protucto protucto11;

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

        final RecyclerView protuctoRecyclerView = view.findViewById(R.id.item_list_product);

        viewModel.elementoSeleccionado.observe(getViewLifecycleOwner(), new Observer<Restaurante>() {
            @Override
            public void onChanged(final Restaurante restaurante) {
                if(restaurante == null) return;

                nombreTextView.setText(restaurante.nombre);
                descripcionTextView.setText(restaurante.descripcion);

                consultarProductos(restaurante.id, protuctoRecyclerView);
            }
        });





//        protuctoRecyclerView.addItemDecoration(new DividerItemDecoration(protuctoRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

//        protuctoAdapter = new ProtuctoAdapter();
//        protuctoRecyclerView.setAdapter(protuctoAdapter);
//
//        viewModel.listaProtuct.observe(getViewLifecycleOwner(), new Observer<List<Protucto>>() {
//            @Override
//            public void onChanged(List<Protucto> protuctos) {
//                protuctoAdapter.establecerListaProtuto(protuctos);
//
//            }
//        });


    }

    void consultarProductos(String restauranteId , RecyclerView protuctoRecyclerView){
        Query query = FirebaseFirestore.getInstance().collection("Restaurantes").document(restauranteId).collection("Productos");
        FirestoreRecyclerOptions<Protucto> options = new FirestoreRecyclerOptions.Builder<Protucto>()
                .setQuery(query, Protucto.class)
                .setLifecycleOwner(this)
                .build();

        Log.d("qqqqq", query.toString());
        protuctoRecyclerView.setAdapter(new ProtuctoAdapter(options));

    }

    class ProtuctoAdapter extends FirestoreRecyclerAdapter<Protucto, ProtuctoAdapter.ProtuctoViewHolder> implements Filterable {
        public ProtuctoAdapter(@NonNull FirestoreRecyclerOptions<Protucto> options) {super(options);}

        @NonNull
        @Override
        public ProtuctoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProtuctoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product, parent, false));
        }


        @Override
        protected void onBindViewHolder(@NonNull ProtuctoViewHolder holder, int position, @NonNull final Protucto protucto) {

            holder.nombreProtucto.setText(protucto.nombre);
            holder.descripcionProtuto.setText(protucto.descripcion);
            holder.precioProtucto.setText(protucto.precio);

            holder.itemView.findViewById(button_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    viewModel.establecerProtucto(protucto);
                    final androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(requireContext())
                            .setMessage("Añadir al carrito?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String nombreTienda = nombreTextView.getText().toString();
                                    viewModel.productoSeleccionado.observe(getViewLifecycleOwner(), new Observer<Protucto>() {
                                        @Override
                                        public void onChanged(final Protucto protucto) {
                                            if(protucto == null) return;
//                                            viewModel.rellenarListaCartProtucto(protucto);
                                        }
                                    });
//                                    viewModel.rellenarListaCart(nombreTienda);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            });

        }


        @Override
        public Filter getFilter() {
            return null;
        }

        class ProtuctoViewHolder extends RecyclerView.ViewHolder {
            TextView nombreProtucto, descripcionProtuto, precioProtucto;
            Button buttonAdd;

            public ProtuctoViewHolder(@NonNull final View itemView) {
                super(itemView);
                nombreProtucto = itemView.findViewById(R.id.nombre_protucto);
                descripcionProtuto = itemView.findViewById(R.id.des_protucto);
                precioProtucto = itemView.findViewById(R.id.precio_protucto);

            }
        }
    }
}
