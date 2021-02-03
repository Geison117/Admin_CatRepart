package com.example.admincatrepart.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincatrepart.Interface.ItemClickListener;
import com.example.admincatrepart.R;


public class ComentarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtComentario, txtNombre, txtRespuesta, txtComida;
    public EditText edtRespuesta;
    public Button btnResponder;
    public LinearLayout lyPendiente, lyHecho;
    private ItemClickListener itemClickListener;

    public ComentarioViewHolder(@NonNull View itemView) {
        super(itemView);
        txtComentario = (TextView) itemView.findViewById(R.id.coment);
        txtNombre = (TextView) itemView.findViewById(R.id.nombre_usuario);
        txtRespuesta = (TextView) itemView.findViewById(R.id.respuesta);
        lyHecho = (LinearLayout) itemView.findViewById(R.id.respuesta_hecha);
        lyPendiente = (LinearLayout) itemView.findViewById(R.id.respuesta_pendiente);
        edtRespuesta = (EditText) itemView.findViewById(R.id.edt_respuesta);
        btnResponder = (Button) itemView.findViewById(R.id.btn_comentario);
        txtComida = (TextView) itemView.findViewById(R.id.nombre_c);
    }

    @Override
    public void onClick(View v) {

    }
}
