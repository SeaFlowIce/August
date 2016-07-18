package com.xinbo.utils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Map;
import java.util.Set;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.text.TextUtils;
import okhttp3.OkHttpClient;

/**
 * Helper class that is used to provide references to initialized
 * RequestQueue(s) and ImageLoader(s)
 * 
 * @author Ognyan Bankov
 * 
 */
public class HTTPUtils {
	private static RequestQueue mRequestQueue;
	private static final boolean USE_OKHTTP = true;

	private HTTPUtils() {
	}

	private static void init(Context context) {
		if (USE_OKHTTP){
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			mRequestQueue = Volley.newRequestQueue(context, new OkHttp3Stack(client));
		}else{
			// 使用默认的HTTPURLConnection
			// 2.3之前HTTPClient
			mRequestQueue = Volley.newRequestQueue(context);
		}

	}

	public static void post(Context context, String url, final Map<String, String> params,
			final ResponseListener listener) {
		StringRequest myReq = new UTFStringRequest(Method.POST, url, listener, listener) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		if (mRequestQueue == null) {
			init(context);
		}

		// 请用缓存
		myReq.setShouldCache(true);
		// 设置缓存时间10分钟
		// myReq.setCacheTime(10*60);
		mRequestQueue.add(myReq);
	}

	public static void get(Context context, String url, Map<String, String> params, final ResponseListener listener) {
		url = buildParams(url, params);
		get(context, url, listener);
	}

	public static String buildParams(String url, Map<String, String> params) {
		if (TextUtils.isEmpty(url)) {
			return url;
		}
		if (params == null || params.size() == 0) {
			return url;
		}
		url += "?";
		Set<String> keySet = params.keySet();
		Iterator<String> itr = keySet.iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			url += key + "=" + params.get(key) + "&";
		}
		url = url.substring(0, url.length() - 1);
		return url;
	}

	public static void get(Context context, String url, final ResponseListener listener) {
		// 判断当前如果没有网络，则返回null
		if (!ConnectionUtils.isConnected(context)) {
			VolleyError error = new VolleyError("no connection");
			listener.onErrorResponse(error);
			return;
		}

		StringRequest myReq = new UTFStringRequest(Method.GET, url, listener, listener);
		if (mRequestQueue == null) {
			init(context);
		}
		mRequestQueue.add(myReq);
	}

	private static RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public static void cancelAll(Context context) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(context);
		}
	}

	/**
	 * Returns instance of ImageLoader initialized with {@see FakeImageCache}
	 * which effectively means that no memory caching is used. This is useful
	 * for images that you know that will be show only once.
	 * 
	 * @return
	 */
	// public static ImageLoader getImageLoader() {
	// if (mImageLoader != null) {
	// return mImageLoader;
	// } else {
	// throw new IllegalStateException("ImageLoader not initialized");
	// }
	// }
	public static class UTFStringRequest extends StringRequest {

		@Override
		protected Response<String> parseNetworkResponse(NetworkResponse response) {
			String parsed;
			try {
				parsed = new String(response.data, "utf-8");
			} catch (UnsupportedEncodingException e) {
				parsed = new String(response.data);
			}
			return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
		}

		public UTFStringRequest(int method, String url, Listener<String> listener, ErrorListener errorListener) {
			super(method, url, listener, errorListener);
		}

	}
}
