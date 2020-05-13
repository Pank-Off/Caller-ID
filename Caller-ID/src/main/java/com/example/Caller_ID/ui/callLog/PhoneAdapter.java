package com.example.Caller_ID.ui.callLog;

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

import java.util.List;


public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {
    private List<PhoneBook> contacts;
    private Context context;
    private OnItemClickListener onItemClickListener;

    PhoneAdapter(List<PhoneBook> contacts, OnItemClickListener onItemClickListener) {
        this.contacts = contacts;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PhoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_third_activity, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.ViewHolder holder, int position) {
        holder.flagView.setImageResource(contacts.get(position).getIcon());
        holder.nameView.setText(contacts.get(position).getName());
        holder.numberView.setText(contacts.get(position).getNumber());
        holder.oneItemView.setOnClickListener(v -> holder.listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
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