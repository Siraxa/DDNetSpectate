package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {

    private static List<Server> serverList;
    private OnItemClickListener listener;

    public ServerAdapter(List<Server> serverList, OnItemClickListener listener) {
        this.serverList = serverList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.server_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Server server = serverList.get(position);
        Server.ServerInfo info = server.getInfo();
        holder.nameTextView.setText(info.getName());
        holder.gameTypeTextView.setText(info.getGame_type());
        // Set other views based on your layout and data
    }

    @Override
    public int getItemCount() {
        return serverList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView gameTypeTextView;
        public TextView mapNameTextView;
        public TextView locationTextView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvServerName);
            gameTypeTextView = itemView.findViewById(R.id.tvGameType);
            mapNameTextView = itemView.findViewById(R.id.tvMapName);
            locationTextView = itemView.findViewById(R.id.tvLocation);
            // Initialize other views...

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(serverList.get(position));
                        }
                    }
                }
            });
        }
    }
}