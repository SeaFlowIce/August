package com.xinbo.utils;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public interface ResponseListener extends ErrorListener, Listener<String> {
}