package com.raintree.syncdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Network_BroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if(checkNetworkConnection(context))
        {
            final DbHelper dbHelper=new DbHelper(context);
            final SQLiteDatabase database=dbHelper.getWritableDatabase();

            Cursor cursor=dbHelper.readFromLocalDatabase(database);

            while (cursor.moveToNext())
            {
                int sync_status=cursor.getInt(cursor.getColumnIndex(Database.SYNC_STATUS));
                if(sync_status==Database.SYNC_STATUS_FAILED)
                {
                    final String Name = cursor.getString(cursor.getColumnIndex(Database.NAME));
                    final String Address = cursor.getString(cursor.getColumnIndex(Database.ADDRESS));
                    final String Designation = cursor.getString(cursor.getColumnIndex(Database.DESIGNATION));
                    StringRequest stringRequest=new StringRequest(Request.Method.GET,Database.BASE_URL, new
                            Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        String Response=jsonObject.getString("response");
                                        if(Response.equals("ok"))
                                        {
                                            dbHelper.updatLocalDatabase(Name,Address,Designation,Database.SYNC_STATUS_OK,database);
                                            context.sendBroadcast(new Intent(Database.UI_UPDATE_BROADCAST));
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String ,String> Params =new HashMap<>();
                            Params.put("name",Name);
                            Params.put("name",Address);
                            Params.put("name",Designation);
                            return super.getParams();

                        }
                    };

                    MySinglton.getInstance(context).addToRequestQueue(stringRequest);
                }
            }

            dbHelper.close();
        }

    }

    public boolean checkNetworkConnection(@org.jetbrains.annotations.NotNull Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());

    }
}
