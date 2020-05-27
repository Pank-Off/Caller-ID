package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Caller_ID.R;
import com.example.Caller_ID.ui.callLog.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpamAdapter extends RecyclerView.Adapter<SpamAdapter.ViewHolder> implements Filterable {

    private HashMap<String, String> spamerMap;
    private ArrayList<Spamers> spamersList = new ArrayList<>();
    private ArrayList<Spamers> spamersListFiltered;
    private Context context;
    private OnItemClickListener onItemClickListener;

    SpamAdapter(HashMap<String, String> spamerMap, OnItemClickListener onItemClickListener) {
        this.spamerMap = spamerMap;
        this.onItemClickListener = onItemClickListener;
        for (Map.Entry<String, String> entry : spamerMap.entrySet()) {
            spamersList.add(new Spamers(entry.getValue(), entry.getKey()));
        }
        spamersListFiltered = spamersList;
    }

    @NonNull
    @Override
    public SpamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_third_activity, parent, false);
        return new SpamAdapter.ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpamAdapter.ViewHolder holder, int position) {
        final Spamers spamers = spamersListFiltered.get(position);
        holder.flagView.setImageResource(R.drawable.bancircle);
        Log.d("Position", position + "");
        holder.nameView.setText(spamers.getComment());
        holder.numberView.setText(spamers.getNumber());
        holder.oneItemView.setOnClickListener(v -> holder.listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return spamersListFiltered == null ? 0 : spamersListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        Log.d("getFilter", "fff");
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    spamersListFiltered = spamersList;

                } else {
                    ArrayList<Spamers> filteredSpamers = new ArrayList<>();
                    for (Map.Entry<String, String> entry : spamerMap.entrySet()) {
                        if (entry.getKey().contains(charSequence) ||
                                entry.getValue().toLowerCase().contains(charString.toLowerCase())) {
                            filteredSpamers.add(new Spamers(entry.getValue(), entry.getKey()));
                        }
                    }
                    spamersListFiltered = filteredSpamers;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = spamersListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                spamersListFiltered = (ArrayList<Spamers>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView flagView;
        TextView nameView;
        TextView numberView;
        LinearLayout oneItemView;
        OnItemClickListener listener;

        ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            flagView = itemView.findViewById(R.id.phone);
            nameView = itemView.findViewById(R.id.name);
            numberView = itemView.findViewById(R.id.number);
            oneItemView = itemView.findViewById(R.id.item);
            this.listener = listener;
        }
    }
}