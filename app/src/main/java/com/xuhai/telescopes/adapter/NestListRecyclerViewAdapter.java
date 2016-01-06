package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.xuhai.telescopes.R;
import com.xuhai.telescopes.domain.Net;

import java.util.List;

/**
 * Created by chudong on 2015/11/30.
 */
public class NestListRecyclerViewAdapter extends RecyclerView.Adapter<NestListRecyclerViewAdapter.ViewHolder> {

    private List<Net> nets;
    private Context context;

    public NestListRecyclerViewAdapter(Context context, List<Net> nets) {
        this.context = context;
        this.nets = nets;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_net, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nest_title.setText(nets.get(position).getTask());
        holder.net_role.setText("我的角色："+nets.get(position).getSeaman_role());
        holder.time.setText(nets.get(position).getTime());


        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return nets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nest_title;
        private TextView net_role;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);

            nest_title = (TextView) itemView.findViewById(R.id.nest_title);
            net_role = (TextView) itemView.findViewById(R.id.net_role);
            time = (TextView) itemView.findViewById(R.id.net_time);
        }
    }

    //------------------------点击事件处理------------------------

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
