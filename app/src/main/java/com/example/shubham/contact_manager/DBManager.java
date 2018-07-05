package com.example.shubham.contact_manager;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

public class DBManager {

    public SQLiteDatabase sqldb;
    static final  String DBname = "contact_manager";
    static final  String Table_name = "contacts";
    static final  String ID= "ID";
    static final  String username= "username";
    static final  String  password= "password";
    static final int DBversion=1;
    static final String Create_table = "create table IF NOT EXISTS "+Table_name+ "( ID integer PRIMARY KEY AUTOINCREMENT,"+username+" text ,"+password+" text);";

    static class DatabaseHelper extends SQLiteOpenHelper {

        public Context context;
        DatabaseHelper(Context context){

            super(context,DBname,null,DBversion);
            this.context= context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(Create_table);
            Toast.makeText(context , "table created" , Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("Drop table IF EXISTS "+Table_name);
            onCreate(sqLiteDatabase);
        }
    }


    public    DBManager(Context context){

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        sqldb=databaseHelper.getWritableDatabase();

    }


    public long insert(ContentValues values){
        long ID=sqldb.insert(Table_name,null,values);
        return ID;

    }



    public Cursor query(String[] Projections,String Selection , String[] SelectionArgs , String SortoOrder){

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Table_name);

        Cursor cursor = qb.query(sqldb,Projections,Selection,SelectionArgs,null,null,SortoOrder);

        return cursor;
    }


    public int delete(String Selection , String[] SelectionArgs ){
        int count = sqldb.delete(Table_name,Selection,SelectionArgs);

        return count;



    }



}



   





