package com.example.admincatrepart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.admincatrepart.Models.Order;
import com.example.admincatrepart.Models.Repartidor;
import com.example.admincatrepart.Models.Solicitud;
import com.example.admincatrepart.ViewHolder.CartAdapter;
import com.example.admincatrepart.ViewHolder.CartViewHolder;
import com.example.admincatrepart.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaComidaEspera extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;
    DatabaseReference request1;
    DatabaseReference request2;
    TextView txtTotalPrice;
    Button btnPlace;
    String idSolicitud;

    FirebaseRecyclerAdapter<Order, CartViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comida_espera);

        idSolicitud = getIntent().getStringExtra("idOrden");

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Solicitudes/" + idSolicitud + "/comidas");
        request1 = database.getReference("Solicitudes");
        request2 = database.getReference("Repartidor");
        recyclerView = (RecyclerView) findViewById(R.id.listaComidaEspera);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice  = (TextView) findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnAsignar);
        if( getIntent().getStringExtra("status").equals("Pendiente")){
            btnPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });
        }
        else{
            btnPlace.setVisibility(View.GONE);
        }


        cargarLista();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListaComidaEspera.this);
        alertDialog.setTitle("Pedido empaquetado");
        alertDialog.setMessage("Ahora debe asignar un repartidor");

        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        alertDialog.setView(mView);

        final Spinner listaR = (Spinner) mView.findViewById(R.id.spinner);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );


        listaR.setLayoutParams(lp);
        alertDialog.setIcon(R.drawable.ic_baseline_face_24);

        final List<Repartidor> repartidor = new ArrayList<>();
        request2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snap: snapshot.getChildren()){
                        String id = snap.getKey();
                        String nombre = snap.child("nombre").getValue().toString();

                        repartidor.add(new Repartidor(id, nombre));
                    }
                    ArrayAdapter<Repartidor> arrayAdapter = new ArrayAdapter<>(
                            ListaComidaEspera.this,
                            R.layout.support_simple_spinner_dropdown_item,
                            repartidor);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    listaR.setAdapter(arrayAdapter);
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Repartidor r = (Repartidor) listaR.getSelectedItem();
                String id = r.getId();
                DatabaseReference request3  = database.getReference("Solicitudes/" + idSolicitud);;
                request3.child("idRepartidor").setValue(id);
                request3.child("status").setValue("1");
                Toast.makeText(ListaComidaEspera.this, "Repartidor asignado con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void cargarLista() {

        adapter = new FirebaseRecyclerAdapter<Order, CartViewHolder>(
                Order.class,
                R.layout.item_carrito,
                CartViewHolder.class,
                request.orderByKey()
        ) {

            @Override
            protected void populateViewHolder(CartViewHolder cartViewHolder, Order order, int i) {
                cartViewHolder.txt_comida.setText(order.getProductoNombre());

                Locale locale = new Locale("es", "CO");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                int precio = (Integer.parseInt(order.getPrecio()))*(Integer.parseInt(order.getCantidad()));

                cartViewHolder.txt_precio.setText(fmt.format(precio));

                TextDrawable drawable = TextDrawable.builder()
                        .buildRound("" + order.getCantidad(), Color.RED);
                cartViewHolder.img.setImageDrawable(drawable);
            }
        };

        Locale locale = new Locale("es", "CO");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        recyclerView.setAdapter(adapter);
        String total = getIntent().getStringExtra("total");
        txtTotalPrice.setText(fmt.format(Integer.parseInt(total)));
    }
}