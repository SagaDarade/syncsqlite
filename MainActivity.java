package com.raintree.syncdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    EditText Name,Address,Designation;
    Button submit;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Contact> arrayList=new ArrayList<>();

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.recyclerView);
        Name=findViewById(R.id.edit_name);
        Address=findViewById(R.id.edit_adrs);
        Designation=findViewById(R.id.edit_desg);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);
        readFromLocalStorage();

        broadcastReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                readFromLocalStorage();

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(Database.UI_UPDATE_BROADCAST));
    }
    @Override
    public void onClick(View v)
    {
        if (v.findViewById(R.id.submit) == submit) {
            String name = Name.getText().toString();
            String addrs = Address.getText().toString();
            String desg = Designation.getText().toString();

          //  saveToAppServer(name);
            sendToServer(name,addrs,desg);
            //saveToLocalStorage(name);
            Name.setText("");
            Address.setText("");
            Designation.setText("");

        }
    }
    private void readFromLocalStorage()
    {
        arrayList.clear();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database=dbHelper.getReadableDatabase();

        Cursor cursor=dbHelper.readFromLocalDatabase(database);

        while (cursor.moveToNext())
        {

            String name = cursor.getString(cursor.getColumnIndex(Database.NAME));
            String addrs = cursor.getString(cursor.getColumnIndex(Database.ADDRESS));
            String desg = cursor.getString(cursor.getColumnIndex(Database.DESIGNATION));
            int sync_status = cursor.getInt(cursor.getColumnIndex(Database.SYNC_STATUS));


            arrayList.add(new Contact(name,addrs,desg,sync_status));

        }

        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();

    }
    private void sendToServer(final String name, final String addrs, final String desg) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, "http://169.254.179.76/syncdemo/syncinfo.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String Response=jsonObject.getString("response");
                            if(Response.equals("ok"))
                                saveToLocalStorage(name,addrs,desg,Database.SYNC_STATUS_OK);
                            else
                            {
                                saveToLocalStorage(name,addrs,desg,Database.SYNC_STATUS_FAILED);
                            }
                        } catch (JSONException e) {


                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error",error.toString());
                        saveToLocalStorage(name,addrs,desg,Database.SYNC_STATUS_FAILED);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("addrs",addrs);
                params.put("desg",desg);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean checkNetworkConnection()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isConnected());

    }

    private void saveToLocalStorage(String name,String addrs,String desg,int sync)
    {
        DbHelper dbHelper=new DbHelper(this);
//      SQLiteDatabase database=dbHelper.getWritableDatabase();
        SQLiteDatabase database1=dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(name,addrs,desg,sync,database1);
        readFromLocalStorage();
        dbHelper.close();
    }

        @Override
        protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(Database.UI_UPDATE_BROADCAST));
        }

     @Override
        protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        }
        }