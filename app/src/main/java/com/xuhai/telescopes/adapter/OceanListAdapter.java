package com.xuhai.telescopes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.activity.MostEffectActivity;
import com.xuhai.telescopes.activity.OceanDetailActivity;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.model.OceanModel;
import com.xuhai.telescopes.utils.DialogUtil;
import com.xuhai.telescopes.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 大海列表适配器
 * @author LHB
 * @date 2015/10/24 0024.
 */
public class OceanListAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<OceanModel> list;
    private int deletePosition = 0;//用于本地删除item
    public OceanListAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<OceanModel>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void setList(ArrayList<OceanModel> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void addList(ArrayList<OceanModel> list){
        this.list.addAll(list);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_list_ocean_simple,null);
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
        /**========================公共部分===================================**/
        public View oceanLayout;
        public TextView oceanContent;
        public ImageView oceanStateImage;
        public TextView oceanTimeText;
        public TextView oceanCommentCount;
        public GridView gridView;
        public OceanGridAdapter gridAdapter;
        public OceanModel model;

        public void findViews(View view){
            oceanLayout = view.findViewById(R.id.rl_ocean);
            oceanContent = (TextView)view.findViewById(R.id.tv_ocean_word);
            oceanStateImage = (ImageView)view.findViewById(R.id.iv_ocean_state);
            oceanTimeText = (TextView)view.findViewById(R.id.tv_ocean_time);
            oceanCommentCount = (TextView)view.findViewById(R.id.tv_ocean_comment);
            gridView = (GridView)view.findViewById(R.id.gv_ocean);

        }

        public void setContext(int position){
            model = list.get(position);
            gridView.setVisibility(View.VISIBLE);
            gridView.setNumColumns(3);
            gridAdapter = new OceanGridAdapter(context,model.imageList);
            gridView.setAdapter(gridAdapter);
            oceanContent.setText(model.summary);
            oceanCommentCount.setText(model.comment_size+"");

        }

        public void setListener(){
            oceanLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(context, OceanDetailActivity.class);
                    intent.putExtra("oceanId",model.id);
                    context.startActivity(intent);
                }
            });
            oceanLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogUtil.dimissDialog();
                    DialogUtil.setOceanOpreateDialog((Activity)context, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()){
                                case R.id.btn_01:
                                    Intent intent =  new Intent(context,MostEffectActivity.class);
                                    intent.putExtra("oceanId",model.id);
                                    context.startActivity(intent);
                                    DialogUtil.dimissDialog();
                                    break;
                                case R.id.btn_06:
                                    DialogUtil.dimissDialog();
                                    HttpUtil.getInstance().deleteOceanTopicDetail(context, model.id, deleteOceanListener);

                                    break;
                                case R.id.btn_07:
                                    DialogUtil.dimissDialog();
                                    break;
                            }
                        }
                    });
                    return true;
                }
            });
        }
    }

    private JsonHttpResponseHandler deleteOceanListener = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            if (statusCode == 200) {
                ToastUtils.show(context, "删除成功");

            }
        }
        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };
}
