package com.xuhai.telescopes.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.adapter.ImagePickerShowAdapter;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by chudong on 2015/12/17.
 */
public class OceanPublishActivityNew extends BaseActivity implements View.OnClickListener {

    public ImageView leftImage;
    public TextView titleText;
    public Button publicBtn;
    public EditText edit;
    public Button pictureImage;
    public RecyclerView recyclerView;
    public ArrayList<PhotoInfo> imageList;
    public ImagePickerShowAdapter imagePickerShowAdapter;


    public void onCreate(Bundle onsavedInstanceState) {
        super.onCreate(onsavedInstanceState);
        setContentView(R.layout.activity_ocean_publish);
        findViews();
        setContent();
        setListener();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        imagePickerShowAdapter = new ImagePickerShowAdapter(this, imageList);
        recyclerView.setAdapter(imagePickerShowAdapter);

//        int horizontalSpacing = 10;
//        int verticalSpacing = 20;
//        SpacingDecoration decoration = new SpacingDecoration(horizontalSpacing, verticalSpacing, true);
//        recyclerView.addItemDecoration(decoration);
    }


    public void findViews() {
        leftImage = (ImageView) findViewById(R.id.iv_left);
        titleText = (TextView) findViewById(R.id.tv_title);
        publicBtn = (Button) findViewById(R.id.btn_publish);
        edit = (EditText) findViewById(R.id.et_ocean_publish);
        pictureImage = (Button) findViewById(R.id.iv_picture);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
    }

    public void setContent() {
        titleText.setText("大海主题");
        publicBtn.setVisibility(View.VISIBLE);
        imageList = new ArrayList<PhotoInfo>();
    }

    public void setListener() {
        leftImage.setOnClickListener(this);
        publicBtn.setOnClickListener(this);
        pictureImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_publish:
                publicBtn.setEnabled(false);
                HttpUtil.getInstance().publishOceanTopic(this, edit.getText().toString(), null, publishOceanListener);
                break;
            case R.id.iv_picture:
                GalleryFinal.open(config);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryFinal.GALLERY_REQUEST_CODE) {
            if (resultCode == GalleryFinal.GALLERY_RESULT_SUCCESS) {
                List<PhotoInfo> photoInfoList = (List<PhotoInfo>) data.getSerializableExtra(GalleryFinal.GALLERY_RESULT_LIST_DATA);
                if (photoInfoList != null) {
                    Log.e("photo", photoInfoList.toString());
                    imageList.clear();
                    imageList.addAll(photoInfoList);
                    imagePickerShowAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private JsonHttpResponseHandler publishOceanListener = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            if (statusCode == 200) {
                Log.i("getlist",response.toString());
                ToastUtils.show(OceanPublishActivityNew.this, "发布成功");
                finish();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            publicBtn.setEnabled(true);
        }
    };


    public class SpacingDecoration extends RecyclerView.ItemDecoration {

        private int mHorizontalSpacing = 0;
        private int mVerticalSpacing = 0;
        private boolean mIncludeEdge = false;

        public SpacingDecoration(int hSpacing, int vSpacing, boolean includeEdge) {
            mHorizontalSpacing = hSpacing;
            mVerticalSpacing = vSpacing;
            mIncludeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            // Only handle the vertical situation
            int position = parent.getChildAdapterPosition(view);
            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
                int spanCount = layoutManager.getSpanCount();
                int column = position % spanCount;
                getGridItemOffsets(outRect, position, column, spanCount);
            } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
                int spanCount = layoutManager.getSpanCount();
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                int column = lp.getSpanIndex();
                getGridItemOffsets(outRect, position, column, spanCount);
            } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                outRect.left = mHorizontalSpacing;
                outRect.right = mHorizontalSpacing;
                if (mIncludeEdge) {
                    if (position == 0) {
                        outRect.top = mVerticalSpacing;
                    }
                    outRect.bottom = mVerticalSpacing;
                } else {
                    if (position > 0) {
                        outRect.top = mVerticalSpacing;
                    }
                }
            }
        }

        private void getGridItemOffsets(Rect outRect, int position, int column, int spanCount) {
            if (mIncludeEdge) {
                outRect.left = mHorizontalSpacing * (spanCount - column) / spanCount;
                outRect.right = mHorizontalSpacing * (column + 1) / spanCount;
                if (position < spanCount) {
                    outRect.top = mVerticalSpacing;
                }
                outRect.bottom = mVerticalSpacing;
            } else {
                outRect.left = mHorizontalSpacing * column / spanCount;
                outRect.right = mHorizontalSpacing * (spanCount - 1 - column) / spanCount;
                if (position >= spanCount) {
                    outRect.top = mVerticalSpacing;
                }
            }
        }
    }


//----------------------------------------图片选择器-----------------------------------------

    //选择图片加载器
    public class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
            Glide.with(activity)
                    .load("file://" + path)
                    .placeholder(cn.finalteam.galleryfinal.R.drawable.ic_gf_default_photo)
                    .error(cn.finalteam.galleryfinal.R.drawable.ic_gf_default_photo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
                    .skipMemoryCache(true)
                            //.centerCrop()
                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
        }
    }


    //选择器配置
    GalleryConfig config = new GalleryConfig.Builder(OceanPublishActivityNew.this)
            .mutiSelect()
            .mutiSelectMaxSize(9)
            .enableEdit()
            .enableCrop()
            .enableRotate()
            .showCamera()
            .imageloader(new GlideImageLoader())
            .cropSquare()
            .cropWidth(400)
            .cropHeight(400)
                    //.setTakePhotoFolter(new File(...)) //自定义拍照存储目录
//            .setEditPhotoCacheFolder(getExternalCacheDir()) //自定义编辑产生的图片缓存目录
                    //.filter(mPhotoList)
            .selected(imageList)
            .build();
}
