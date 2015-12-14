package com.xuhai.telescopes.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.TextView;


import com.xuhai.telescopes.R;

import java.util.ArrayList;

/**
 * Created by admin on 2015/11/16.
 */
public class TextWithImage extends TextView {
    public static String MATCH_IMAGE = "[photo]";
    private int matchLength = 0;
    private Context context;
    private int scaleWidth = 0;
    private ArrayList<Integer> photoPositionList;
    private SpannableString spannableString;
    public TextWithImage(Context context) {
        super(context);
        init(context);
    }

    public TextWithImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextWithImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init( Context context){
        this.context = context;
        photoPositionList = new ArrayList<Integer>();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        scaleWidth = wm.getDefaultDisplay().getWidth();
        matchLength = MATCH_IMAGE.length();
    }

    public void setTextContent(String content,ArrayList<String> imageList){
        int startIndex = 0;
        int endIndex = 0;
        spannableString = new SpannableString(content);
        while ((startIndex = content.indexOf(MATCH_IMAGE,startIndex)) != -1){
            photoPositionList.add(startIndex);
            startIndex = startIndex + matchLength;
        }

        for(int i = 0; i< photoPositionList.size();i++){
            Bitmap bitmap = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.bg_test)).getBitmap();
            InsertImage(spannableString, bitmap, photoPositionList.get(i));
        }
        this.setText(spannableString);

    }

    /**
     * @param bitmap
     * @return
     */
    public ImageSpan getImageSpan(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scale = (float)scaleWidth/width;
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        Bitmap temp = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        ImageSpan imageSpan = new ImageSpan(context, temp);;
        return imageSpan;
    };

    public void InsertImage (SpannableString spannable,Bitmap bitmap,int position){
        spannable.setSpan(getImageSpan(bitmap),position,position+matchLength, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
