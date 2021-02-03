package com.example.admincatrepart.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincatrepart.Interface.ItemClickListener;
import com.example.admincatrepart.R;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAddress,txtUsuario;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderAddress = (TextView) itemView.findViewById(R.id.orden_direccion);
        txtOrderStatus =  (TextView) itemView.findViewById(R.id.status_orden);
        txtOrderId =  (TextView) itemView.findViewById(R.id.id_orden);
        txtOrderPhone =  (TextView) itemView.findViewById(R.id.orden_telefono);
        txtUsuario = (TextView) itemView.findViewById(R.id.orden_usuario);
        itemView.setOnClickListener(this);
    }
    public void setItemListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
