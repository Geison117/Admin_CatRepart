package com.example.admincatrepart.ui.pedido_pendiente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincatrepart.Interface.ItemClickListener;
import com.example.admincatrepart.ListaComidaEspera;
import com.example.admincatrepart.MenuAdmin;
import com.example.admincatrepart.Models.Solicitud;
import com.example.admincatrepart.R;
import com.example.admincatrepart.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeFragment extends Fragment {

    public RecyclerView recyclerView;
    public LinearLayoutManager layoutManager;

    FirebaseRecyclerAdapter<Solicitud, OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pedido_pendiente, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Solicitudes");
        recyclerView = (RecyclerView) root.findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders();


        return root;
    }


    private void loadOrders(){

        Query pendientes = requests.orderByChild("status").equalTo("0");
        adapter = new FirebaseRecyclerAdapter<Solicitud, OrderViewHolder>(
                Solicitud.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                pendientes
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Solicitud solicitud, int i) {
                orderViewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
                orderViewHolder.txtOrderStatus.setText(conseguirStatus(solicitud.getStatus()));
                orderViewHolder.txtOrderAddress.setText(solicitud.getDireccion());
                orderViewHolder.txtOrderPhone.setText(solicitud.getTelefono());
                orderViewHolder.txtUsuario.setText(solicitud.getNombre());

                orderViewHolder.setItemListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent listaComida = new Intent(getContext(), ListaComidaEspera.class);
                        listaComida.putExtra("idOrden", adapter.getRef(position).getKey());
                        listaComida.putExtra("total", solicitud.getTotal());
                        listaComida.putExtra("status", "Pendiente");
                        startActivity(listaComida);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String conseguirStatus(String status) {
        if (status.equals("0")){
            return "En espera";
        }
        else if (status.equals("1")){
            return "Viene en camino";
        }
        else{
            return "Entregado";
        }
    }
}
