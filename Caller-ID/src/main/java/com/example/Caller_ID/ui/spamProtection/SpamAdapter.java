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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpamAdapter extends RecyclerView.Adapter<SpamAdapter.ViewHolder> {

    private HashMap<String, String> spamerMap;
    private ArrayList<String> numbers = new ArrayList<>();
    private ArrayList<String> comments = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    SpamAdapter(HashMap<String, String> spamerMap, OnItemClickListener onItemClickListener) {
        this.spamerMap = spamerMap;
        this.onItemClickListener = onItemClickListener;
        for (Map.Entry<String, String> entry : spamerMap.entrySet()) {
            numbers.add(entry.getKey());
            comments.add(entry.getValue());
        }
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
        holder.nameView.setText(comments.get(position));
        holder.numberView.setText(numbers.get(position));
        holder.oneItemView.setOnClickListener(v -> holder.listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return numbers == null ? 0 : numbers.size();
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