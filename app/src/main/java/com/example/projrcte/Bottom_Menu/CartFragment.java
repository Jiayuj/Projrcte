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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projrcte.R;
import com.example.projrcte.TiendaFragment;
import com.example.projrcte.ViewModel;
import com.example.projrcte.model.Protucto;
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
    ListaCartProductAdapter listaCartProductAdapter;
    TextView totalPrecio;
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

        totalPrecio = view.findViewById(R.id.cart_total_precio);
        try {
            double cant = 10 * viewModel1.listaCartProtuct.getValue().size()* viewModel1.listaCart.getValue().size();
            totalPrecio.setText(Double.toString(cant));
        }catch (NullPointerException e ){

        }

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

                RecyclerView protuctoCartRecyclerView = itemView.findViewById(R.id.item_cart_producto);
                protuctoCartRecyclerView.addItemDecoration(new DividerItemDecoration(protuctoCartRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

                listaCartProductAdapter = new ListaCartProductAdapter();
                protuctoCartRecyclerView.setAdapter(listaCartProductAdapter);

                viewModel1.listaCartProtuct.observe(getViewLifecycleOwner(), new Observer<List<Protucto>>() {
                    @Override
                    public void onChanged(List<Protucto> protuctos) {
                        listaCartProductAdapter.establecerListCart(protuctos);
                    }
                });
            }
        }
    }

    class ListaCartProductAdapter extends RecyclerView.Adapter<ListaCartProductAdapter.ListaCartProductViewHolder>{

        List<Protucto> protuctos;

        @NonNull
        @Override
        public ListaCartProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListaCartProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cartproduct, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListaCartProductViewHolder holder, final int position) {

            final Protucto protucto = protuctos.get(position);

            holder.nombrecartProtucto.setText(protucto.nombre);
            holder.descripcioncartProtuto.setText(protucto.descripcion);
            holder.preciocartProtucto.setText(protucto.precio);

        }

        @Override
        public int getItemCount() {
            return protuctos == null ? 0 : protuctos.size();
        }

        public void establecerListCart(List<Protucto> protuctos){
            this.protuctos = protuctos;
            notifyDataSetChanged();
        }

        class ListaCartProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView nombrecartProtucto, descripcioncartProtuto, preciocartProtucto;

            ImageView mas, meno;
            TextView cant;

            public ListaCartProductViewHolder(@NonNull View itemView) {
                super(itemView);
                nombrecartProtucto = itemView.findViewById(R.id.nombre_cart_protucto);
                descripcioncartProtuto = itemView.findViewById(R.id.des_cart_protucto);
                preciocartProtucto = itemView.findViewById(R.id.precio_cart_protucto);

                mas = itemView.findViewById(R.id.image_mas);
                mas.setOnClickListener(this);
                meno = itemView.findViewById(R.id.image_meno);
                meno.setOnClickListener(this);
                cant = itemView.findViewById(R.id.text_cant);
                cant.setText("1");
            }

            @Override
            public void onClick(View v) {
                ImageView b = (ImageView) v;
                int quant= Integer.parseInt(cant.getText().toString());
                double quantTotal= Double.parseDouble(totalPrecio.getText().toString());
                switch(b.getId()) {
                    case R.id.image_mas:
                        quant++;
                        quantTotal+=10;
                        cant.setText(Integer.toString(quant));
                        totalPrecio.setText(Double.toString(quantTotal));
                        break;
                    case R.id.image_meno:
                        if (quant !=0){
                            quant--;
                            quantTotal-=10;
                            cant.setText(Integer.toString(quant));
                            totalPrecio.setText(Double.toString(quantTotal));
                        }
                        break;
                }
            }
        }
    }

}


