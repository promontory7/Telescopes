package com.xuhai.telescopes.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Framgent
 * @author LHB
 * @date 2015/10/22 0022.
 */
public class BaseFragment extends Fragment {
    public static final String TAG = "BaseFramgent";
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        findView(getView());
        bindData();
        setListener();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public  void findView(View layout){
    }
    public void bindData(){
    }
    public void setListener( ){
    }



}
