package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.domain.Seaman;

import java.util.ArrayList;

/**
 * Created by chudong on 2015/12/26.
 */
public class CastNetRoleAdapter extends RecyclerView.Adapter<CastNetRoleAdapter.ViewHolder> {

    private ArrayList<Seaman> roles;
    private Context context;

    public CastNetRoleAdapter(Context context, ArrayList<Seaman> roles) {
        this.context = context;
        this.roles = roles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast_net_role, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.roleName.setText(roles.get(position).getSeaman_role_name());
        holder.roleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (position < roles.size()) {
                    roles.get(position).setSeaman_role_name(s.toString());
                    Log.e("afterTextChanged",position+s.toString());
                }

            }
        });

        holder.roleDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roles.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });

        if(position==0){
            holder.roleDeleteButton.setVisibility(View.GONE);
        }

//        if (onItemClickListener != null) {
//            holder.roleDeleteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onItemClick(holder.itemView, position);
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        Log.e("getItemCount", roles.size() + "");
        return roles.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText roleName;
        private ImageButton roleDeleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            roleName = (EditText) itemView.findViewById(R.id.item_ET_role);
            roleDeleteButton = (ImageButton) itemView.findViewById(R.id.item_BT_role);
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
