package com.zeros.GesCoB.Activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.zeros.GesCoB.Contract.Contract;
import com.zeros.GesCoB.Presenter.VisitPresenter;
import com.zeros.GesCoB.R;
import java.util.ArrayList;
import java.util.List;

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.ViewHolder>  implements  Filterable{
    private List<VisitPresenter> visitaPresenters;
    private Context context;

    private Contract.OnVisitListener onVisitListener;
    private List<VisitPresenter> visitAdaptersFiltered;
    private VisitAdapterListener listener;

    public VisitAdapter(List<VisitPresenter> visitaPresenters, Contract.OnVisitListener onVisitListener, VisitAdapterListener listener) {
        this.visitaPresenters = visitaPresenters;
        this.visitAdaptersFiltered = visitaPresenters;
        this.onVisitListener = onVisitListener;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rows, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,onVisitListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String document = holder.view.getResources().getString(R.string.document)+": "+visitAdaptersFiltered.get(position).getCedula();
        holder.document.setText(document);
        String client = holder.view.getResources().getString(R.string.client)+": "+visitAdaptersFiltered.get(position).getCliente();
        holder.client.setText(client);
        String address = holder.view.getResources().getString(R.string.address)+": "+visitAdaptersFiltered.get(position).getDireccion();
        holder.anddress.setText(address);
        String status = holder.view.getResources().getString(R.string.estado_suministro)+": "+visitAdaptersFiltered.get(position).getEstado_suministro();
        holder.status.setText(status);
        String deparment = holder.view.getResources().getString(R.string.deparment)+": "+visitAdaptersFiltered.get(position).getDepartamento();
        holder.deparment.setText(deparment);
        String city = holder.view.getResources().getString(R.string.city)+" : "+visitAdaptersFiltered.get(position).getMunicipio();
        holder.city.setText(city);
        String district = holder.view.getResources().getString(R.string.distric)+" : "+visitAdaptersFiltered.get(position).getBarrio();
        holder.district.setText(district);
    }

    @Override
    public int getItemCount() {
        return visitAdaptersFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    visitAdaptersFiltered = visitaPresenters;
                } else {
                    List<VisitPresenter> filteredList = new ArrayList<>();
                    for (VisitPresenter row : visitaPresenters) {
                        if (row.getCedula().toLowerCase().contains(charString) ||
                                row.getCliente().toLowerCase().contains(charString) || row.getDepartamento().toLowerCase().contains(charString) ||
                                row.getDireccion().toLowerCase().contains(charString) || row.getEstado_suministro().toLowerCase().contains(charString) ||
                                row.getBarrio().toLowerCase().contains(charSequence) || row.getMunicipio().toLowerCase().contains(charString)) {
                            filteredList.add(row);
                        }
                    }
                    visitAdaptersFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = visitAdaptersFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                visitAdaptersFiltered = (ArrayList<VisitPresenter>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       Contract.OnVisitListener onVisitListener;
        private TextView document,client,anddress, status, deparment, city, district;
        private Button buttonView;
        private View view;


        public ViewHolder(View v,Contract.OnVisitListener onVisitListener) {
            super(v);
            document = (TextView) v.findViewById(R.id.textDocument);
            client = (TextView) v.findViewById(R.id.textCLient);
            anddress = (TextView) v.findViewById(R.id.textAddress);
            status= (TextView) v.findViewById(R.id.textEstatus);
            deparment = (TextView) v.findViewById(R.id.textDeparment);
            city = (TextView) v.findViewById(R.id.textCity);
            district = (TextView) v.findViewById(R.id.textDistrict);
            buttonView = v.findViewById(R.id.buttonView);
            view = v;
            this.onVisitListener  = onVisitListener;
            buttonView.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(visitAdaptersFiltered.get(getAdapterPosition()));
                }
            });

        }

        @Override
        public void onClick(View view) {
            String d = document.getText().toString().split(":")[1].trim();
            onVisitListener.onClick(view,d);
        }
    }


    public interface VisitAdapterListener {
        void onContactSelected(VisitPresenter visitPresenter);
    }

}