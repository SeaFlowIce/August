package com.xinbo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionUtils {
	public static boolean isWIFI(Context context){
		if (isConnected(context))
		{
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			int type = info.getType();
			if (ConnectivityManager.TYPE_WIFI == type)
			{
				return true; 
			}
		}
		return false;
	}
	public static boolean isMobile(Context context){
		if (isConnected(context))
		{
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			int type = info.getType();
			if (ConnectivityManager.TYPE_MOBILE == type)
			{
				return true; 
			}
		}
		return false;
	}
	
	public static boolean isConnected(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null){
			return false;
		}
		boolean available = info.isAvailable();
		return available;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
