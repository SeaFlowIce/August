package com.galgame.util;

import java.io.File;

import com.galgame.august.R;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinbo.utils.ConnectionUtils;
import com.xinbo.utils.UILUtils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

public class ImageUtils
{
	public static void displayImage(Context context, String imageUrl, ImageView imageView)
	{
		// 无图模式
		boolean isNoPic = SPUtils.isNoPic(context);
		boolean isWIFI = ConnectionUtils.isWIFI(context);
		DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
		File imgCachefile = diskCache.get(imageUrl);
		boolean hasCache = imgCachefile != null;
		Log.e("", "isNoPic: " + isNoPic + " isWIFI: " + isWIFI + " hasCache: " + hasCache);
		if (!isNoPic || isWIFI || hasCache)
		{
			UILUtils.displayImage(imageUrl, imageView);
		} else
		{
			imageView.setImageResource(R.drawable.ic_launcher);
		}
	}

	public static void displayImageNoAnim(Context context, String imageUrl, ImageView imgView)
	{
		boolean isNoPic = SPUtils.isNoPic(context);
		boolean isWifi = ConnectionUtils.isWIFI(context);
		DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
		File file = diskCache.get(imageUrl);
		boolean hasCache = file != null;
		if (!isNoPic || isWifi || hasCache)
		{
			UILUtils.displayImageNoAnim(imageUrl, imgView);
		} else
		{
			imgView.setImageResource(R.drawable.ic_launcher);
		}
	}

	public static void displayCircleImage(Context context, String imageUrl, ImageView imgView)
	{
		boolean isNoPic = SPUtils.isNoPic(context);
		boolean isWifi = ConnectionUtils.isWIFI(context);
		DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
		File file = diskCache.get(imageUrl);
		boolean hasCache = file != null;
		if (!isNoPic || isWifi || hasCache)
		{
			UILUtils.displayCircleImage(imageUrl, imgView);
		} else
		{
			imgView.setImageResource(R.drawable.ic_launcher);
		}
	}
}
