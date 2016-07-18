package com.galgame.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZHConstants
{
	public static final boolean DEBUG_MODE = true;

	public static final class url
	{
		public static final String latest = DEBUG_MODE ? url_real.latest : url_dummy.latest;
		public static final String detail = DEBUG_MODE ? url_real.detail : url_dummy.latest;
		public static final String hot = DEBUG_MODE ? url_real.hot : url_dummy.hot;
		public static final String before_real = url_real.before;
		public static final String css = DEBUG_MODE ? url_dummy.css : url_dummy.css;
		public static final String before0 = DEBUG_MODE ? url_real.hot : url_dummy.before0;
		public static final String before1 = DEBUG_MODE ? url_real.hot : url_dummy.before1;
		public static final String before2 = DEBUG_MODE ? url_real.hot : url_dummy.before2;
		public static final String[] before = new String[]
		{ latest, before0, before1, before2 };
		public static final String themes = DEBUG_MODE ? url_real.themes : url_dummy.before0;
		public static final String themes_content = DEBUG_MODE ? url_real.themes_content : url_dummy.before0;
		public static final String comments_long = DEBUG_MODE ? url_real.comment : url_dummy.comments_long;
		public static final String comments_short = DEBUG_MODE ? url_real.comment : url_dummy.comments_short;
		public static final String extra = DEBUG_MODE ? url_real.extra : url_dummy.extra;
		public static final String start = DEBUG_MODE ? url_real.start : url_dummy.start;
	}

	// 真实数据地址
	public static final class url_real
	{
		private static final String host = "http://news-at.zhihu.com/api/";
		public static final String hot = host + "3/news/hot";
		public static final String latest = host + "4/news/latest";
		public static final String before = host + "4/news/before/";
		public static final String themes = host + "4/themes";
		public static final String themes_content = host + "4/theme/";
		public static final String detail = host + "4/news/";
		public static final String css = host + "4/news/";
		public static final String comment = host + "4/story/";
		public static final String extra = host + "4/story-extra/";
		public static final String start = host + "4/start-image/1080*1776";
	}

	// 模拟数据地址
	public static final class url_dummy
	{
		private static final String host = "http://169.254.133.127:8080/zhihu_server/";
		public static final String latest = host + "latest.txt";
		public static final String hot = host + "hot.txt";
		public static final String before0 = host + "before0926.txt";
		public static final String before1 = host + "before0927.txt";
		public static final String before2 = host + "before0928.txt";
		public static final String themes = host + "themes.txt";
		public static final String themes_content = host + "themes_content.txt";
		public static final String css = host + "detail.css";
		public static final String comments_long = host + "comments_long.txt";
		public static final String comments_short = host + "comments_short.txt";
		public static final String extra = host + "4/story-extra/";
		public static final String start = "http://news-at.zhihu.com/api/4/start-image/1080*1776";
	}

	public static final class Dates
	{
		public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		@SuppressWarnings("deprecation")
		public static final Date birthday = new java.util.Date(113, 4, 19); // May
																			// 19th,
																			// 2013
	}

	public static final class key
	{

	}

}
