package com.galgame.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils
{

	public static void setMode(Context context, boolean isNight)
	{
		SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		sp.edit().putBoolean("isNight", isNight).commit();
	}

	public static boolean isNight(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		boolean isNight = sp.getBoolean("isNight", false);
		return isNight;
	}

	public static void setNoPic(Context context, boolean isNoPic)
	{
		SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		sp.edit().putBoolean("isNoPic", isNoPic).commit();
	}

	public static boolean isNoPic(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		boolean isNoPic = sp.getBoolean("isNoPic", false);
		return isNoPic;
	}

	public static void setBigText(Context context, boolean isBigText)
	{
		SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		sp.edit().putBoolean("isBigText", isBigText).commit();
	}

	public static boolean isBigText(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		boolean isBigText = sp.getBoolean("isBigText", false);
		return isBigText;
	}

	public static void setVote(Context context, boolean isVote)
	{
		SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		sp.edit().putBoolean("isVote", isVote).commit();
	}

	public static boolean isVote(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		boolean isVote = sp.getBoolean("isVote", false);
		return isVote;
	}
}
