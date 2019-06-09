package com.zeros.GesCoB.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zeros.GesCoB.Contract.Contract;
import com.zeros.GesCoB.Presenter.*;
import com.zeros.GesCoB.R;

import java.util.List;

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.ViewHolder> {
    private List<VisitPresenter> visitaPresenters;

    private Contract.OnNoteListener monNoteListener;


    public VisitAdapter(List<VisitPresenter> visitaPresenters, Contract.OnNoteListener onNoteListener) {
        this.visitaPresenters = visitaPresenters;
        this.monNoteListener = onNoteListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rows, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,monNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String document = holder.view.getResources().getString(R.string.document)+": "+visitaPresenters.get(position).getCedula();
        holder.document.setText(document);
        String client = holder.view.getResources().getString(R.string.client)+": "+visitaPresenters.get(position).getCliente();
        holder.client.setText(client);
        String address = holder.view.getResources().getString(R.string.address)+": "+visitaPresenters.get(position).getDireccion();
        holder.anddress.setText(address);
        String status = holder.view.getResources().getString(R.string.estado_suministro)+": "+visitaPresenters.get(position).getEstado_suministro();
        holder.status.setText(status);
        String deparment = holder.view.getResources().getString(R.string.deparment)+": "+visitaPresenters.get(position).getDepartamento();
        holder.deparment.setText(deparment);
        String city = holder.view.getResources().getString(R.string.city)+": "+visitaPresenters.get(position).getMunicipio();
        holder.city.setText(city);
    }

    @Override
    public int getItemCount() {
        return visitaPresenters .size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       Contract.OnNoteListener onNoteListener;
        private TextView document,client,anddress, status, deparment, city;
        private Button buttonView;
        private View view;

        public ViewHolder(View v,Contract.OnNoteListener onNoteListener) {
            super(v);
            document = (TextView) v.findViewById(R.id.textDocument);
            client = (TextView) v.findViewById(R.id.textCLient);
            anddress = (TextView) v.findViewById(R.id.textAddress);
            status= (TextView) v.findViewById(R.id.textEstatus);
            deparment = (TextView) v.findViewById(R.id.textDeparment);
            city = (TextView) v.findViewById(R.id.textCity);
            //district = (TextView) v.findViewById(R.id.textDistrict);
            buttonView = v.findViewById(R.id.buttonView);
            view = v;
            this.onNoteListener  = onNoteListener;
            buttonView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            String d = document.getText().toString().split(":")[1].trim();
            onNoteListener.onClick(view,d);
        }
    }
}