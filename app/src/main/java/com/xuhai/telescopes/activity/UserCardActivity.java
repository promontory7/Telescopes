package com.xuhai.telescopes.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.utils.ImageUtils;


/**
 * 用户名片
 * @author LHB
 * @date 2015/11/12 0012.
 */
public class UserCardActivity extends BaseActivity implements View.OnClickListener{

    public ImageView leftImage;
    public TextView titleText;
    public TextView rightText;

    public ImageView headImage;

    public void onCreate(Bundle onsavedInstanceState){
        super.onCreate(onsavedInstanceState);
        setContentView(R.layout.activity_user_card);
        findViews();
        setContent();
        setListener();
    }

    public void findViews(){
        leftImage = (ImageView)findViewById(R.id.iv_left);
        titleText = (TextView)findViewById(R.id.tv_title);
        headImage = (ImageView)findViewById(R.id.iv_head);
    }

    public void setContent(){
        Drawable drawable = this.getResources().getDrawable(R.drawable.bg_test);
        headImage.setImageBitmap(ImageUtils.getRoundedCornerBitmap(drawable, 80));
    }
    public void setListener(){
        leftImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
