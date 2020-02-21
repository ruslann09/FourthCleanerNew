package com.cacheclean.cleanapp.cacheappclean;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cacheclean.cleanapp.cacheappclean.Clas.Items;

import java.util.List;

public class PowerModeAdapt extends RecyclerView.Adapter<PowerModeAdapt.MyViewHolder> {

    public List<Items> apps;

    public PowerModeAdapt(List<Items> apps)
    {
        this.apps = apps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.powe_itemlist, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Items app= apps.get(position);
        holder.size.setText(app.getText());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView size;


        public MyViewHolder(View view) {
            super(view);
            size = (TextView) view.findViewById(R.id.items);
        }
    }
}
