package com.xuhai.telescopes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.activity.OceanCommentActivity;
import com.xuhai.telescopes.activity.UserCardActivity;
import com.xuhai.telescopes.model.OceanCommentModel;
import com.xuhai.telescopes.model.OceanSecondCommentModel;
import com.xuhai.telescopes.widget.listview.ExpandListView;

import java.util.ArrayList;


/**
 * 大海评论适配器
 * @author LHB
 * @date 2015/10/24 0024.
 */
public class OceanCommentAdapter extends BaseAdapter{


    private LayoutInflater inflater;
    private Context context;
    private ArrayList<OceanCommentModel> list = new ArrayList<OceanCommentModel>();
    public OceanCommentAdapter(Context context){
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
        return position;
    }

    public void setList(ArrayList<OceanCommentModel> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_list_comment,null);
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
        private ImageView headImage;
        private TextView timeText;
        private TextView nameText;
        private TextView contentText;
        private ExpandListView replyList;
        private View commentLayout;
        private ReplyAdapter adapter;
        public TextView oceanCommentCount;
        private OceanCommentModel model;


        public void findViews(View view){
            timeText = (TextView)view.findViewById(R.id.tv_time);
            headImage = (ImageView)view.findViewById(R.id.iv_head);
            nameText = (TextView)view.findViewById(R.id.tv_name);
            contentText = (TextView)view.findViewById(R.id.tv_content);
            replyList = (ExpandListView)view.findViewById(R.id.lv_reply);
            commentLayout = (View)view.findViewById(R.id.ll_comment);
            oceanCommentCount = (TextView)view.findViewById(R.id.tv_ocean_comment);
        }

        public void setContext(int position){
            model = list.get(position);
            timeText.setText(model.created_at);
            nameText.setText(model.user.nickname);
            contentText.setText(model.content);
            adapter = new ReplyAdapter(model.secondList);
            replyList.setAdapter(adapter);

        }

        public void setListener(){
            headImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,UserCardActivity.class);
                   context.startActivity(intent);
                }
            });
            commentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OceanCommentActivity.class);
                    intent.putExtra("oceanId",model.question_id);
                    intent.putExtra("userId",model.user.userid);
                    context.startActivity(intent);
                }
            });
        }

        class ReplyAdapter extends BaseAdapter{

            private ArrayList<OceanSecondCommentModel> secondList ;
            public ReplyAdapter(ArrayList<OceanSecondCommentModel> secondList){
                this.secondList = secondList;
            }
            @Override
            public int getCount() {
                return secondList.size();
            }

            @Override
            public Object getItem(int position) {
                return secondList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            public void setList(ArrayList<OceanSecondCommentModel> secondList){
                this.secondList =secondList;
                this.notifyDataSetChanged();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                OceanSecondCommentModel model = secondList.get(position);
                TextView textView = new TextView(context);
                int paddingVe = context.getResources().getDimensionPixelOffset(R.dimen.reply_padding_vertical);
                int paddingHo = context.getResources().getDimensionPixelOffset(R.dimen.reply_padding_horizonl);
                float textSize = context.getResources().getDimensionPixelOffset(R.dimen.reply_text);
                textView.setPadding(paddingHo, paddingVe, paddingHo, paddingVe);
                textView.setTextSize(14);
                textView.setTextColor(0xff999999);
                String nick = model.user.nickname+":";
                String content = model.content;
                SpannableString spannableString = new SpannableString(nick+content);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),0,nick.length()-1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(spannableString);
                return textView;

            }
        }
    }
}
