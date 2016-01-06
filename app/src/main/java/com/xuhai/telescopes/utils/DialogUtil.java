package com.xuhai.telescopes.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuhai.telescopes.R;

/**
 * 单例弹出框
 */
public class DialogUtil {
    public static Dialog dialog;

    private static DialogInterface.OnDismissListener onDimissListener = new DialogInterface.OnDismissListener(){
        @Override
        public void onDismiss(DialogInterface temp) {
            if (temp == dialog){
                dialog = null;
            }
        }
    };
    private static void setOnDimissListener(  ){
        if (dialog == null)
            return;
        dialog.setOnDismissListener(onDimissListener);
    }

    public static View.OnClickListener onCancleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dimissDialog();
        }
    };

    public static void dimissDialog(){
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    public static Dialog showCenterDialog(Context context,View view){
        if (dialog != null)
            return null;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable());
        window.setAttributes(params);
        setOnDimissListener();
        dialog.show();
        return dialog;
    }

    public static Dialog showBottomDialog(Context context,View view){
        if (dialog != null)
            return null;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawable(new ColorDrawable());
        window.setWindowAnimations(R.style.style_animation);
        window.setAttributes(params);
        setOnDimissListener();
        dialog.show();
        return dialog;
    }

    public static Dialog showMenusDialog(Context context, View clickView,View.OnClickListener onClickListener){
        if (dialog != null)
            return null;
        LinearLayout linearLayout = new LinearLayout(context);
        View view = LayoutInflater.from(context).inflate(null,null);
        linearLayout.removeView(view);
        return  null;

    };

    //底部弹出
    public static void setOceanOpreateDialog(Activity activity,View.OnClickListener onClickListener){
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.bottom_dialog, null);
        //findView
        Button btn01 = (Button)dialogView.findViewById(R.id.btn_01);
        Button btn02 = (Button)dialogView.findViewById(R.id.btn_02);
        Button btn03 = (Button)dialogView.findViewById(R.id.btn_03);
        Button btn04 = (Button)dialogView.findViewById(R.id.btn_04);
        Button btn05 = (Button)dialogView.findViewById(R.id.btn_05);
        Button btn06 = (Button)dialogView.findViewById(R.id.btn_06);
        Button btn07 = (Button)dialogView.findViewById(R.id.btn_07);

        //setText
        btn01.setText("结题");
        btn02.setVisibility(View.GONE);
        btn03.setVisibility(View.GONE);
        btn04.setVisibility(View.GONE);
        btn05.setVisibility(View.GONE);
        btn06.setText("删除");
        btn07.setText("取消");


        btn01.setOnClickListener(onClickListener);
        btn06.setOnClickListener(onClickListener);
        btn07.setOnClickListener(onClickListener);

        showBottomDialog(activity, dialogView);
    }

    public static void setNetOperateDialog(Activity activity,View.OnClickListener onClickListener){
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.bottom_dialog, null);
        //findView
        Button btn01 = (Button)dialogView.findViewById(R.id.btn_01);
        Button btn02 = (Button)dialogView.findViewById(R.id.btn_02);
        Button btn03 = (Button)dialogView.findViewById(R.id.btn_03);
        Button btn04 = (Button)dialogView.findViewById(R.id.btn_04);
        Button btn05 = (Button)dialogView.findViewById(R.id.btn_05);
        Button btn06 = (Button)dialogView.findViewById(R.id.btn_06);
        Button btn07 = (Button)dialogView.findViewById(R.id.btn_07);

        //setText
        btn01.setText("收网");
        btn02.setVisibility(View.GONE);
        btn03.setVisibility(View.GONE);
        btn04.setVisibility(View.GONE);
        btn05.setVisibility(View.GONE);
        btn06.setText("删除");
        btn07.setText("取消");


        btn01.setOnClickListener(onClickListener);
        btn06.setOnClickListener(onClickListener);
        btn07.setOnClickListener(onClickListener);

        showBottomDialog(activity, dialogView);
    }

    //弹出提示
    public static void setTips(Activity activity,String text,String leftText,String rightText,View.OnClickListener onClickListener){
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_tips_two_button, null);
        TextView tipsText =(TextView) dialogView.findViewById(R.id.tv_tip);
        Button leftButton = (Button)dialogView.findViewById(R.id.button_left);
        Button rightButton = (Button)dialogView.findViewById(R.id.button_right);
        tipsText.setText(text);
        leftButton.setText(leftText);
        rightButton.setText(rightText);
        leftButton.setOnClickListener(onClickListener);
        rightButton.setOnClickListener(onClickListener);
        showCenterDialog(activity, dialogView);
    }

}
