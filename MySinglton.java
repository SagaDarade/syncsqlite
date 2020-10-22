package com.raintree.syncdemo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySinglton {

    private static MySinglton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private MySinglton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        return mRequestQueue;
    }

    public static synchronized MySinglton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySinglton(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}