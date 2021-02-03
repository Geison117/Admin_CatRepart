package com.example.admincatrepart.ui.Mensajes;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.admincatrepart.Interface.ItemClickListener;
import com.example.admincatrepart.ListaComidaEspera;
import com.example.admincatrepart.Models.Comentario;
import com.example.admincatrepart.Models.Solicitud;
import com.example.admincatrepart.Models.Usuario;
import com.example.admincatrepart.R;
import com.example.admincatrepart.ViewHolder.ComentarioViewHolder;
import com.example.admincatrepart.ViewHolder.OrderViewHolder;
import com.example.admincatrepart.ui.pedido_entregado.PedidoEntregadoViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mensajes extends Fragment {
    public RecyclerView recyclerView;
    public LinearLayoutManager layoutManager;

    FirebaseRecyclerAdapter<Comentario, ComentarioViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;
    DatabaseReference requestComida;

    Usuario persona;

    private MensajesViewModel mViewModel;

    public static Mensajes newInstance() {
        return new Mensajes();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(MensajesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_pedido_pendiente, container, false);

        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Comentarios");
        requestComida = database.getReference("Comidas");


        recyclerView = (RecyclerView) root.findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        loadMensajes();

        return root;
    }

    private void loadMensajes() {
        adapter = new FirebaseRecyclerAdapter<Comentario, ComentarioViewHolder>(
                Comentario.class,
                R.layout.comentario_individuo,
                ComentarioViewHolder.class,
                requests.orderByKey()
        ){

            @Override
            protected void populateViewHolder(ComentarioViewHolder comentarioViewHolder, Comentario comentario, int i) {
                DatabaseReference personas = database.getReference("Usuario");
                String idUsuario =comentario.getIdusuario();
                String idComida = comentario.getIdcomida();


                requestComida.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(idComida).exists()){
                            String nc = (String) snapshot.child(idComida).child("nombre").getValue();
                            comentarioViewHolder.txtComida.setText(nc);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                personas.child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            persona = snapshot.getValue(Usuario.class);
                            comentarioViewHolder.txtNombre.setText(persona.getNombre());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                comentarioViewHolder.txtComentario.setText(comentario.getMensaje());
                if (comentario.getRespuesta() == null){
                    comentarioViewHolder.lyHecho.setVisibility(View.GONE);

                    comentarioViewHolder.btnResponder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!comentarioViewHolder.edtRespuesta.getText().toString().equals("")){
                                requests.child(adapter.getRef(i).getKey()).child("respuesta").setValue(comentarioViewHolder.edtRespuesta.getText().toString());
                            }
                        }
                    });
                }
                else
                {
                    comentarioViewHolder.lyPendiente.setVisibility(View.GONE);
                    comentarioViewHolder.txtRespuesta.setText(comentario.getRespuesta());
                }
            }
        };
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MensajesViewModel.class);
        // TODO: Use the ViewModel
    }
}