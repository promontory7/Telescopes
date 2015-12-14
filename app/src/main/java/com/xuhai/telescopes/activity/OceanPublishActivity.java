package com.xuhai.telescopes.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.xuhai.telescopes.R;
import com.xuhai.telescopes.httpclient.HttpUtil;
import com.xuhai.telescopes.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * 发布主题
 * @author LHB
 * @date 2015/11/13 0013.
 */
public class OceanPublishActivity extends BaseActivity implements View.OnClickListener{
    public ImageView leftImage;
    public TextView titleText;
    public Button publicBtn;
    public EditText edit;
    public Button pictureImage;
    public ArrayList<String> imageList;


    public void onCreate(Bundle onsavedInstanceState) {
        super.onCreate(onsavedInstanceState);
        setContentView(R.layout.activity_ocean_publish);
        findViews();
        setContent();
        setListener();
    }


    public void findViews() {
        leftImage = (ImageView) findViewById(R.id.iv_left);
        titleText = (TextView) findViewById(R.id.tv_title);
        publicBtn = (Button) findViewById(R.id.btn_publish);
        edit = (EditText) findViewById(R.id.et_ocean_publish);
        pictureImage = (Button)findViewById(R.id.iv_picture);
    }

    public void setContent() {
        titleText.setText("大海主题");
        publicBtn.setVisibility(View.VISIBLE);

        imageList = new ArrayList<String>();
    }

    public void setListener(){
        leftImage.setOnClickListener(this);
        publicBtn.setOnClickListener(this);
        pictureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicFromLocal();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 3) {
                Bitmap pic = null;
                Uri uri = data.getData();
                String[] proj = { MediaStore.Images.Media.DATA };
                Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                String img_path = actualimagecursor.getString(actual_image_column_index);
                imageList.add(img_path);
                pic = getBitmap(data.getData());
                String temp = "[photo]";
                insertIntoEditText(getBitmapMime(pic, temp));
            }
        }
    }
    /**
     * 本地相册
     */
    public void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, 3);
    }

    /**
     *
     * @param uri
     * @return
     */
    private Bitmap getBitmap(Uri uri) {
        Bitmap pic = null;
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        Display display = getWindowManager().getDefaultDisplay();
        int dw = display.getWidth();
        int dh = display.getHeight();
        try {
            pic = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri), null, op);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int wRatio = (int) Math.ceil(op.outWidth / (float) dw);
        int hRatio = (int) Math.ceil(op.outHeight / (float) dh);
        if (wRatio > 1 && hRatio > 1) {
            op.inSampleSize = wRatio + hRatio;
        }
        op.inJustDecodeBounds = false;
        try {
            pic = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri), null, op);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return pic;
    }

    /**
     *
     *
     * @param pic
     * @param pictureTag
     * @return
     */
    private SpannableString getBitmapMime(Bitmap pic, String pictureTag) {
        int imgWidth = pic.getWidth();
        int imgHeight = pic.getHeight();
        float scalew = (float) 40 / imgWidth;
        float scaleh = (float) 40 / imgHeight;
        Display display = getWindowManager().getDefaultDisplay();
        int dw = display.getWidth()-this.getResources().getDimensionPixelOffset(R.dimen.ocean_publish_margin);
        int dh = dw*imgHeight/imgWidth;
        float scale = (float)dw/imgWidth;
        Matrix mx = new Matrix();
        mx.postScale(scale,scale);
        pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true);
        SpannableString ss = new SpannableString(pictureTag);
        ImageSpan span = new ImageSpan(this, pic);
        ss.setSpan(span, 0, pictureTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private void insertIntoEditText(SpannableString ss) {
        Editable et = edit.getText();
        int start = edit.getSelectionStart();
        et.insert(start, ss);
        edit.setText(et);
        edit.setSelection(start + ss.length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_publish:
                publicBtn.setEnabled(false);
                HttpUtil.getInstance().publishOceanTopic(this, edit.getText().toString(), imageList, publishOceanListener);

                break;
        }
    }

    private JsonHttpResponseHandler publishOceanListener = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            if (statusCode == 200) {
                Log.i("getlist",response.toString());
                ToastUtils.show(OceanPublishActivity.this, "发布成功");
                finish();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            publicBtn.setEnabled(true);
        }
    };
}
