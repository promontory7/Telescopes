package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xuhai.telescopes.R;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by chudong on 2015/12/17.
 */
public class ImagePickerShowAdapter extends RecyclerView.Adapter<ImagePickerShowAdapter.ViewHolder>{
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<PhotoInfo> result;
    private final static String TAG = "Adapter";

    public ImagePickerShowAdapter(Context context, List<PhotoInfo> result) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_ocean_imagepicker_image, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(result.get(position).getPhotoPath())
                .centerCrop()
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

    }

}
