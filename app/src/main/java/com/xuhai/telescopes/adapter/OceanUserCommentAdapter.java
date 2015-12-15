package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.model.OceanCommentModel;
import com.xuhai.telescopes.utils.TimeUtil;

import java.util.ArrayList;


/**
 * 单个用户评论适配
 * @author LHB
 * @date 2015/10/24 0024.
 */
public class OceanUserCommentAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<OceanCommentModel> list = new ArrayList<OceanCommentModel>();
    public OceanUserCommentAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public void setList(ArrayList<OceanCommentModel> list){
        this.list = list;
        this.notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_list_user_comment,null);
            holder = new ListHolder();
            holder.findViews(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ListHolder)convertView.getTag();
        }
        holder.setContext(position);
        holder.setListener();
        return convertView;
    }

    class ListHolder{

        private TextView timeText;
        private TextView contentText;


        public void findViews(View view){
            timeText = (TextView)view.findViewById(R.id.tv_time);
            contentText = (TextView)view.findViewById(R.id.tv_content);
        }

        public void setContext(int position){
            OceanCommentModel model = list.get(position);
//            String reply = "回复";
//            String nick = "呃呃呃:";
            String content = model.content;
            SpannableString spannableString = new SpannableString(content);
//            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),reply.length(),reply.length()+nick.length()-1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            contentText.setText(spannableString);
            timeText.setText(TimeUtil.getStringFromStr(model.created_at));
        }

        public void setListener(){

        }
    }
}
