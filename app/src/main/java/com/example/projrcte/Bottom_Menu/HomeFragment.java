package com.example.projrcte.Bottom_Menu;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projrcte.model.Restaurante;
import com.example.projrcte.R;
import com.example.projrcte.ViewModel;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    ViewModel viewModel1;
    NavController navController;
    SearchView searchView;
    ElementosAdapter elementosAdapter;
    TapTargetSequence tapTargetSequence;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState)  {
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

        tapTargetSequence = new TapTargetSequence(requireActivity());
        tapTargetSequence.targets(
                TapTarget.forView(view.findViewById(R.id.loca), "Loacalizacion","Permite buscar restaurante cerca tuyo")
                        .id(1)
                        .cancelable(false)
                        .tintTarget(true),
                TapTarget.forView(view.findViewById(R.id.simpleSearchView), "Buscar", "Permite buscar restaurante, comida, lugar")
                        .id(2)
                        .targetCircleColor(R.color.A100)
                        .tintTarget(false)
        );
        tapTargetSequence.listener(new TapTargetSequence.Listener() {
            // This listener will tell us when interesting(tm) events happen in regards
            // to the sequence
            @Override
            public void onSequenceFinish() {
                viewModel1.setTutoriaStatus(true);
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

            }

            @Override
            public void onSequenceCanceled(final TapTarget lastTarget) {
                viewModel1.setTutoriaStatus(true);
            }
        });

        if (!viewModel1.getTutoriaStatus()) {
            Log.d("q", "onViewCreated: "+ viewModel1.tutoriaStatus);
            tapTargetSequence.start();
        }
        searchView = view.findViewById(R.id.simpleSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    elementosAdapter.getFilter().filter("");
                } else {
                    elementosAdapter.getFilter().filter(newText.toString());
                }
                return true;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loca:
                Navigation.findNavController(v).navigate(R.id.locationFragment);
                break;
        }

    }

    class ElementosAdapter extends RecyclerView.Adapter<ElementosAdapter.ElementoViewHolder> implements Filterable {

        List<Restaurante> restaurantes;
        private List<Restaurante> orig;

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

        public void establecerListaElementos(List<Restaurante> restaurantes) {
            this.restaurantes = restaurantes;
            notifyDataSetChanged();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults oReturn = new FilterResults();
                    final List<Restaurante> results = new ArrayList<>();
                    if (orig == null)
                        orig  = restaurantes;
                    if (constraint != null){
                        if(orig !=null & orig.size()>0 ){
                            for ( final Restaurante g :orig) {
                                if (g.getNombre().toLowerCase().contains(constraint.toString().toLowerCase()));
                            }
                        }
                        oReturn.values = results;
                    }
                    return oReturn;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    restaurantes= (ArrayList<Restaurante>)results.values;
                    notifyDataSetChanged();

                }
            };
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
