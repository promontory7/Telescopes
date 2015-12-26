package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.model.OceanUserModel;
import com.xuhai.telescopes.utils.ImageUtils;

import java.util.ArrayList;

/**
 * 影响力列表适配器
 *
 * @author LHB
 * @date 2015/11/12 0012.
 */
public class EffectListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<OceanUserModel> list = new ArrayList<OceanUserModel>();
    private int selectedPosition;//

    public EffectListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public int getselectedUserId() {
        return list.get(selectedPosition).userid;
    }

    public void setList(ArrayList<OceanUserModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_most_effect, null);
            holder = new ViewHolder();
            holder.findViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setContent(position);
        return convertView;
    }

    class ViewHolder {
        ImageView headImage;
        TextView nameText;
        TextView timeText;
        TextView commentText;
        ImageView cursorImage;

        public void findViews(View view) {
            headImage = (ImageView) view.findViewById(R.id.iv_head);
            nameText = (TextView) view.findViewById(R.id.tv_name);
            timeText = (TextView) view.findViewById(R.id.tv_effect_time);
            commentText = (TextView) view.findViewById(R.id.tv_effect_comment);
            cursorImage = (ImageView) view.findViewById(R.id.iv_cursor);
        }

        public void setContent(int position) {


            if (position == selectedPosition) {

                cursorImage.setVisibility(View.VISIBLE);
                Log.e("position", position + "");
                Log.e("selectposition", selectedPosition + "");
            } else {
                cursorImage.setVisibility(View.INVISIBLE);
            }

            Drawable drawable = context.getResources().getDrawable(R.drawable.bg_test);
            headImage.setImageBitmap(ImageUtils.getRoundedCornerBitmap(drawable, 80));
            OceanUserModel model = list.get(position);
            nameText.setText(model.name);
//            timeText.setText(model.time+"");
//            commentText.setText(model.commentNum+"");

        }
    }
}
