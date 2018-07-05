package com.example.shubham.contact_manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBManager dbManager;
    EditText etusername , etpassword;
    Button Add ;

    Cursor cursor;

    ArrayList<AdapterItems>    listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       dbManager  =  new DBManager(this);
      etusername = findViewById(R.id.user_name);
       etpassword = findViewById(R.id.pass_word);
       Add = findViewById(R.id.Add);



    }

    public void Add_button_clicked(View view){

        ContentValues values = new ContentValues();
        values.put(DBManager.username, etusername.getText().toString());
        values.put(DBManager.password,etpassword.getText().toString());
        long ID=dbManager.insert(values);
        if(ID>0){
            Toast.makeText(getApplicationContext(),"data added with user id " +ID , Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"error occured" , Toast.LENGTH_LONG).show();
        }

    }





    public void Load_button_clicked(View view){
        load_logic();

    }


    public void load_logic(){
        //put colummn names in the projection pART;
        cursor = dbManager.query(null,null,null,DBManager.username);

        listnewsData.clear();

        //add data and view it

        if(cursor.moveToFirst()){

            do{
                listnewsData.add(new AdapterItems(cursor.getString(cursor.getColumnIndex(DBManager.ID)),cursor.getString(cursor.getColumnIndex(DBManager.username
                )),cursor.getString(cursor.getColumnIndex(DBManager.password
                ))));

            }while (cursor.moveToNext());


        }


        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=findViewById(R.id.list_data);
        lsNews.setAdapter(myadapter);



    }




    // search button logic

    public void srch_button_clicked(View view){


        String[] selectionArgs = {"%"+etusername.getText().toString()+"%"};
        cursor = dbManager.query(null,"username like ?",selectionArgs,DBManager.username);

        listnewsData.clear();

        //add data and view it

        if(cursor.moveToFirst()){

            do{
                listnewsData.add(new AdapterItems(cursor.getString(cursor.getColumnIndex(DBManager.ID)),cursor.getString(cursor.getColumnIndex(DBManager.username
                )),cursor.getString(cursor.getColumnIndex(DBManager.password
                ))));

            }while (cursor.moveToNext());


        }
        else{
            Toast.makeText(getApplicationContext(),"not present" , Toast.LENGTH_LONG).show();
        }


        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=findViewById(R.id.list_data);
        lsNews.setAdapter(myadapter);



    }






    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.linearlist, null);

            final   AdapterItems s = listnewsDataAdpater.get(position);

            TextView ID=myView.findViewById(R.id.Id_textview);
            ID.setText(s.ID);

            TextView username_text=myView.findViewById(R.id.username_textview);
            username_text.setText(s.username);

            TextView password_text=myView.findViewById(R.id.password_textview);
            password_text.setText(s.password);

            Button deletebtn = myView.findViewById(R.id.del_btn);
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] selectionargs = {s.ID};
                    int count = dbManager.delete("ID = ?" , selectionargs);

                    if(count>0){
                      load_logic();

                    }

                }
            });



            return myView;
        }

    }



}
