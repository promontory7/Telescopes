/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.xuhai.telescopes.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.xuhai.telescopes.R;
import com.xuhai.telescopes.widget.MetaballView;


public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private int mState = STATE_NORMAL;
	private MetaballView metaballView;
	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
	
	private final int ROTATE_ANIM_DURATION = 180;
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);
		metaballView = (MetaballView) mContainer.findViewById(R.id.metaball);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	public void setState(int state) {
		if (state == mState) return ;

		if (state == STATE_REFRESHING) {
			metaballView.clearAnimation();
		} else {
			metaballView.clearAnimation();
		}
		
		switch(state){
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				metaballView.clearAnimation();
			}
			if (mState == STATE_REFRESHING) {
				metaballView.clearAnimation();

			}

			break;
		case STATE_READY:
			if (mState != STATE_READY){
				metaballView.clearAnimation();
			}
			break;
		case STATE_REFRESHING:
			metaballView.clearAnimation();
			metaballView.startAnimation();
				break;
			default:
		}
		
		mState = state;
	}
	
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getLayoutParams().height;
	}

}
