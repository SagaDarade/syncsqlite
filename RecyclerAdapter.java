package com.raintree.syncdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList <Contact> arrayList = new  ArrayList<>();

    public RecyclerAdapter(ArrayList<Contact>arrayList)
    {
        this.arrayList=arrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Name.setText(arrayList.get(position).getName());
        holder.Adsress.setText(arrayList.get(position).getAddrs());
        holder.Designation.setText(arrayList.get(position).getDesignation());
        int Sync_ststus =arrayList.get(position).getSync_status();
        if(Sync_ststus==Database.SYNC_STATUS_OK)
        {
            holder.Sync_ststus.setImageResource(R.drawable.ok);
        }
        else
        {
            holder.Sync_ststus.setImageResource(R.drawable.ic_baseline_sync_24);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView Sync_ststus;
        TextView Name,Adsress,Designation;

        public MyViewHolder(View itemView) {
            super(itemView);
            Sync_ststus=(ImageView)itemView.findViewById(R.id.imgsync);
            Name=(TextView)itemView.findViewById(R.id.name);
           Adsress=(TextView)itemView.findViewById(R.id.address);
            Designation=(TextView)itemView.findViewById(R.id.desg);
        }
        }
    }
