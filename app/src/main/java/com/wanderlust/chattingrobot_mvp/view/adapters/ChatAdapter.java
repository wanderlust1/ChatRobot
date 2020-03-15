package com.wanderlust.chattingrobot_mvp.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanderlust.chattingrobot_mvp.R;
import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<TextBean> list;
    private Context context;

    public ChatAdapter(List<TextBean> list) {
        this.list = list;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView leftText;
        private TextView rightText;
        public ChatViewHolder(View itemView) {
            super(itemView);
            this.leftText = itemView.findViewById(R.id.bubble_left);
            this.rightText = itemView.findViewById(R.id.bubble_right);
        }
    }

    @NonNull @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null) {
            context = parent.getContext();
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        TextBean item = list.get(position);
        if (item.getType() == TextBean.TYPE_SEND) {
            holder.rightText.setVisibility(View.VISIBLE);
            holder.leftText.setVisibility(View.GONE);
            holder.rightText.setText(item.getText());
            holder.rightText.setTextColor(Color.parseColor("#ffffff"));
        } else if(item.getType() == TextBean.TYPE_RECEIVE) {
            holder.leftText.setVisibility(View.VISIBLE);
            holder.rightText.setVisibility(View.GONE);
            holder.leftText.setText(item.getText());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

