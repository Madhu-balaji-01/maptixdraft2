package com.example.maptixdraft2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    Context context;
    List<ListItem> user_HelperList;

    public TableAdapter(Context context,List<ListItem> user_HelperList) {
        this.context = context;
        this.user_HelperList = user_HelperList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grocerytable,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(user_HelperList != null && user_HelperList.size() > 0) {
            ListItem helper = user_HelperList.get(position);
            holder.items_tv.setText(helper.getItems());
            holder.quantity_tv.setText(helper.getQuantity());
        } else{
            return;
        }
    }


    @Override
    public int getItemCount() {
        return user_HelperList.size() ;
    }

    public void removeItem(int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView items_tv,quantity_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            items_tv = itemView.findViewById(R.id.item_tv);
            quantity_tv = itemView.findViewById(R.id.quantity_tv);
        }
    }
}
