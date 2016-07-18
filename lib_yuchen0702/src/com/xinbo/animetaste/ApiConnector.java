package com.xinbo.animetaste;

import java.util.TreeMap;

import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.ResponseListener;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("DefaultLocale")
public class ApiConnector {

	private static ApiConnector mInstance = new ApiConnector();

	private static Context mContext;

	private static final String INIT_REQUEST_URL = "http://i.animetaste.net/api/setup/?api_key=%s&timestamp=%s&anime=%d&feature=%d&advert=%d&access_token=%s";
	private static final String ANIMATION_REQUEST_URL = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%s&page=%d&access_token=%s";
	private static final String ANIMATION_RANDOM_URL = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&order=random&limit=%d&access_token=%s";
	private static final String ANIMATION_DETAIL_URL = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&vid=%d&access_token=%s";
	private static final String CATEGORY_REQUEST_URL = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&page=%d&category=%d&limit=%d&access_token=%s";

	private static final String API_KEY = "android";
	private static final String API_SECRET = "7763079ba6abf342a99ab5a1dfa87ba8";

	public static enum RequestType {
		INIT, ALL, RANDOM, DETAIL, CATEGORY
	}

	private ApiConnector() {
	}

	public static ApiConnector getInstance(Context context) {
		mContext = context;
		return mInstance;
	}

	private void get(String request, ResponseListener listener) {
		HTTPUtils.get(mContext, request, listener);
	}

	public void getInitData(int animeCount, int featureCount, int advertiseCount, ResponseListener listener) {
		long timeStamp = System.currentTimeMillis() / 1000L;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("api_key", API_KEY);
		params.put("timestamp", String.valueOf(timeStamp));
		params.put("anime", String.valueOf(animeCount));
		params.put("feature", String.valueOf(featureCount));
		params.put("advert", String.valueOf(advertiseCount));
		String access_token = ApiUtils.getAccessToken(params, API_SECRET);
		String request = String.format(INIT_REQUEST_URL, API_KEY, timeStamp, animeCount, featureCount, advertiseCount,
				access_token);
		get(request, listener);
	}

	public void getList(int page, ResponseListener listener) {
		long timeStamp = System.currentTimeMillis() / 1000L;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("api_key", API_KEY);
		params.put("timestamp", String.valueOf(timeStamp));
		params.put("page", String.valueOf(page));
		String access_token = ApiUtils.getAccessToken(params, API_SECRET);
		String request = String.format(ANIMATION_REQUEST_URL, API_KEY, timeStamp, page, access_token);
		get(request, listener);
	}

	public void getRandom(int count, ResponseListener listener) {
		long timeStamp = System.currentTimeMillis() / 1000L;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("api_key", API_KEY);
		params.put("timestamp", String.valueOf(timeStamp));
		params.put("order", "random");
		params.put("limit", String.valueOf(count));
		String access_token = ApiUtils.getAccessToken(params, API_SECRET);
		String request = String.format(ANIMATION_RANDOM_URL, API_KEY, timeStamp, count, access_token);
		get(request, listener);
	}

	public void getDetail(int vid, ResponseListener listener) {
		long timestamp = System.currentTimeMillis() / 1000L;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("api_key", API_KEY);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("vid", String.valueOf(vid));
		String access_token = ApiUtils.getAccessToken(params, API_SECRET);
		String request = String.format(ANIMATION_DETAIL_URL, API_KEY, timestamp, vid, access_token);
		get(request, listener);
	}

	public void getCategory(int categoryId, int page, int count, ResponseListener listener) {
		long timeStamp = System.currentTimeMillis() / 1000L;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("api_key", API_KEY);
		params.put("timestamp", String.valueOf(timeStamp));
		params.put("page", String.valueOf(page));
		params.put("category", String.valueOf(categoryId));
		params.put("limit", String.valueOf(count));
		String access_token = ApiUtils.getAccessToken(params, API_SECRET);
		String request = String.format(CATEGORY_REQUEST_URL, API_KEY, timeStamp, page, categoryId, count, access_token);
		get(request, listener);
	}

	private static final String RECOMMEND_ALL_REQUEST = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&feature=1&limit=%d&access_token=%s";
	private static final String RECOMMEND_CATEGORY_REQUEST = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&category=%d&feature=1&limit=%d&access_token=%s";

	public void getALLRecommend(int count, ResponseListener listener) {
		long timestamp = System.currentTimeMillis() / 1000L;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("api_key", API_KEY);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("limit", String.valueOf(count));
		params.put("feature", String.valueOf(1));
		String access_token = ApiUtils.getAccessToken(params, API_SECRET);
		String request = String.format(RECOMMEND_ALL_REQUEST, API_KEY, timestamp, count, access_token);
		get(request, listener);
	}

	public void getCategoryRecommend(int categoryId, int count, ResponseListener listener) {
		long timestamp = System.currentTimeMillis() / 1000L;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("api_key", API_KEY);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("limit", String.valueOf(count));
		params.put("feature", String.valueOf(1));
		params.put("category", String.valueOf(categoryId));
		String access_token = ApiUtils.getAccessToken(params, API_SECRET);
		String request = String.format(RECOMMEND_CATEGORY_REQUEST, API_KEY, timestamp, categoryId, count, access_token);
		get(request, listener);
	}
}
