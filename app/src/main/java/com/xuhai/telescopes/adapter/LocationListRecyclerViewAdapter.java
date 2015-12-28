package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.domain.Location;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/5.
 */
public class LocationListRecyclerViewAdapter extends RecyclerView.Adapter<LocationListRecyclerViewAdapter.ViewHolder> {

    ArrayList<Location> location;
    Context context;

    public LocationListRecyclerViewAdapter(Context context, ArrayList<Location> location) {
        this.context = context;
        this.location = location;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String  str =location.get(position).getProvince()+
                location.get(position).getCity()+location.get(position).getSchool_id();
        Log.e("location",str);
        holder.location_name.setText(str);

    }

    @Override
    public int getItemCount() {
        return location.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView location_name;

        public ViewHolder(View itemView) {
            super(itemView);
            location_name = (TextView) itemView.findViewById(R.id.location_name);
        }
    }
}
