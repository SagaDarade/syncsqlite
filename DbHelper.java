package com.raintree.syncdemo;

import android.annotation.SuppressLint;
import android.app.admin.DelegatedAdminReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.w3c.dom.Text;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION =201;

    private static  final String CREATE_TABLE= "create table "+Database.TABLE_NAME+
     "(id integer primary key autoincrement,"
            +Database.NAME+" text,"
            +Database.ADDRESS+" text,"
            +Database.DESIGNATION+" text,"
            +Database.SYNC_STATUS+" integer);";

    private static  final String DROP_TABLE="drop table if exists " +Database.TABLE_NAME;

    public DbHelper(Context context)
    {
        super(context,Database.DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database)
        {
            /*try {
                String CREATE_TABLE = "CREATE TABLE " + Database.TABLE_NAME +
                        "( ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + Database.NAME + " TEXT, "
                        + Database.SYNC_STATUS + " INTEGER);";

                database.execSQL(CREATE_TABLE);
            }catch (Exception e)
            {
               e.toString();
            }*/
            database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {


        database.execSQL(DROP_TABLE);

        onCreate(database);
    }

    public void saveToLocalDatabase(String name,String addrs,String desg,int sync_status,SQLiteDatabase database)
    {

        ContentValues contentValues=new ContentValues();
        contentValues.put(Database.NAME,name);
        contentValues.put(Database.ADDRESS,addrs);
        contentValues.put(Database.DESIGNATION,desg);
        contentValues.put(Database.SYNC_STATUS,sync_status);
        database.insert(Database.TABLE_NAME,null,contentValues);

    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database)
    {
        String[] Colmuns={Database.NAME,Database.ADDRESS,Database.DESIGNATION,Database.SYNC_STATUS};
        return (database.query(Database.TABLE_NAME, Colmuns, null, null, null, null, null));
    }

    public void updatLocalDatabase(String name,String addrs,String desg,int sync_status,SQLiteDatabase database)
    {
        ContentValues contentValues=new ContentValues();

        contentValues.put(Database.SYNC_STATUS,sync_status);
        String selection=Database.NAME +"Like ?";
        String[] selection_args={name,addrs,desg};
        database.update(Database.TABLE_NAME,contentValues,selection,selection_args);
    }


}
