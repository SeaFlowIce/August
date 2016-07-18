package com.xinbo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 增加了滚动监听事件
 * 
 * @author Administrator
 *
 */
public class ScrollViewExtend extends ScrollView {
	public interface OnScrollChangedListener {
		void onScrollChange(int offset);
	}

	private OnScrollChangedListener mListener;

	public void setOnScrollChangedListener(OnScrollChangedListener l) {
		this.mListener = l;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mListener != null) {
			mListener.onScrollChange(t);
		}
	}

	public ScrollViewExtend(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

}
