package com.xuhai.telescopes.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactItemView extends LinearLayout {

    private TextView unreadMsgView;

    public ContactItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ContactItemView(Context context) {
        super(context);
        init(context, null);
    }
    
    private void init(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, com.xuhai.telescopes.R.styleable.ContactItemView);
        String name = ta.getString(com.xuhai.telescopes.R.styleable.ContactItemView_contactItemName);
        Drawable image = ta.getDrawable(com.xuhai.telescopes.R.styleable.ContactItemView_contactItemImage);
        ta.recycle();
        
        LayoutInflater.from(context).inflate(com.xuhai.telescopes.R.layout.em_widget_contact_item, this);
        ImageView avatar = (ImageView) findViewById(com.xuhai.telescopes.R.id.avatar);
        unreadMsgView = (TextView) findViewById(com.xuhai.telescopes.R.id.unread_msg_number);
        TextView nameView = (TextView) findViewById(com.xuhai.telescopes.R.id.name);
        if(image != null){
            avatar.setImageDrawable(image);
        }
        nameView.setText(name);
    }
    
    public void setUnreadCount(int unreadCount){
        unreadMsgView.setText(String.valueOf(unreadCount));
    }
    
    public void showUnreadMsgView(){
        unreadMsgView.setVisibility(View.VISIBLE);
    }
    public void hideUnreadMsgView(){
        unreadMsgView.setVisibility(View.INVISIBLE);
    }
    
}
