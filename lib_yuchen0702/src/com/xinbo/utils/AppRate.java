package com.xinbo.utils;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

public class AppRate {
	/**
	 * 启动软件商店，同时定位到包名对应的页面
	 * @param context
	 * @return true 如果当前设备中存在软件商店软件，否则返回false
	 */
	public static boolean rate(Context context) {
		Uri uri = Uri.parse("market://details?id=" + 
			context.getPackageName());
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		List<ResolveInfo> activities = 
			context.getPackageManager().queryIntentActivities(
				goToMarket, 0);
		if (activities != null && activities.size() > 0){
			context.startActivity(goToMarket);
			return true;
		}else{
			return false;
		}
	}
}
