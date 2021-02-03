package com.example.admincatrepart.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincatrepart.Interface.ItemClickListener;
import com.example.admincatrepart.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_comida, txt_precio;
    public ImageView img;


    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_comida = (TextView) itemView.findViewById(R.id.carrito_item_nombre);
        txt_precio = (TextView) itemView.findViewById(R.id.carrito_item_precio);
        img = (ImageView) itemView.findViewById(R.id.carrito_item_cantidad);
    }

    @Override
    public void onClick(View v) {

    }
}
