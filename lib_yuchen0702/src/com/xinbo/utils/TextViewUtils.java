package com.xinbo.utils;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

public final class TextViewUtils {

	public static void setTextDrawable(Context context, int drawableRes,
			TextView tvName) {
		Drawable drawableTop = context.getResources().getDrawable(drawableRes);
		// 必须设置图片大小，否则不显示
		drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
				drawableTop.getMinimumHeight());
		tvName.setCompoundDrawables(null, drawableTop, null, null);
	}

	public static void setTextDrawable(Context context, Bitmap bitmap,
			TextView tvName) {
		Drawable drawableTop = BitmapUtils.bitmap2Drawable(bitmap);
		// 必须设置图片大小，否则不显示
		drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
				drawableTop.getMinimumHeight());
		tvName.setCompoundDrawables(null, drawableTop, null, null);
	}

	public static void setTextDrawable(Context context, String url,
			final TextView textView) {
		ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
			public void onLoadingStarted(String arg0, View arg1) {
			}

			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			}

			public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
				Drawable drawableTop = BitmapUtils.bitmap2Drawable(bitmap);
				// 必须设置图片大小，否则不显示
				drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
						drawableTop.getMinimumHeight());
				textView.setCompoundDrawables(null, drawableTop, null, null);
			}

			public void onLoadingCancelled(String arg0, View arg1) {
			}
		});
	}

}
