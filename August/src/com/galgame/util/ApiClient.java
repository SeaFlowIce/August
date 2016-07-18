package com.galgame.util;

import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.ResponseListener;

import android.content.Context;

public class ApiClient
{

	public static void getDetail(Context context, int id, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.detail + id, listener);
	}

	public static void getBeforeDummy(Context context, int position, ResponseListener listener)
	{
		// 调整position
		position -= 1;// 第一个页面是热门
		if (position >= ZHConstants.url.before.length)
		{
			position = 0;
		}
		HTTPUtils.get(context, ZHConstants.url.before[position], listener);
	}

	public static void getHot(Context context, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.hot, listener);
	}

	public static void getBefore(Context context, String date, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.before_real + date, listener);
	}

	public static void getLatest(Context context, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.latest, listener);
	}

	public static void getThemes(Context context, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.themes, listener);
	}

	public static void getThemesContent(Context context, int id, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.themes_content + id, listener);
	}

	public static void getCommentLong(Context context, int id, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.comments_long + id + "/long-comments", listener);
	}

	public static void getCommentShort(Context context, int id, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.comments_short + id + "/short-comments", listener);
	}

	public static void getExtra(Context context, int id, ResponseListener listener)
	{
		HTTPUtils.get(context, ZHConstants.url.extra + id, listener);
	}

	// 获取启动界面数据
	public static void getStart(Context content, ResponseListener listener)
	{
		HTTPUtils.get(content, ZHConstants.url.start, listener);
	}

}
