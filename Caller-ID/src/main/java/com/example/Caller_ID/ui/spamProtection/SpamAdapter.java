package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Caller_ID.R;
import com.example.Caller_ID.ui.callLog.OnItemClickListener;

import java.util.List;

public class SpamAdapter extends RecyclerView.Adapter<SpamAdapter.ViewHolder> {

    private List<String> spamers;
    private Context context;
    private OnItemClickListener onItemClickListener;

    SpamAdapter(List<String> spamers, OnItemClickListener onItemClickListener) {
        this.spamers = spamers;
        this.onItemClickListener = onItemClickListener;
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
        holder.flagView.setImageResource(R.drawable.bancircle);
        holder.nameView.setText("Spamer " + position);
        holder.numberView.setText(spamers.get(position));
        holder.oneItemView.setOnClickListener(v -> holder.listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return spamers == null ? 0 : spamers.size();
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