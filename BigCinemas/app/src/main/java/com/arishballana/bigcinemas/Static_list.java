package com.arishballana.bigcinemas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Static_list extends AppCompatActivity {

    ListView lv;
    Context context;
    SQLiteDatabase db;

    ArrayList<String> movies_name=new ArrayList<String>();
    ArrayList<String> directors_name=new ArrayList<String>();
    ArrayList<String> writers_name=new ArrayList<String>();
    ArrayList<String> years_array=new ArrayList<String>();
    ArrayList<Integer> rating_array=new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_list);

        context=this;

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.static_list_layout);
        relativeLayout.setAnimation(animation);

        try {
            db=openOrCreateDatabase("Look_Cinemas", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS movies_new(name VARCHAR, director VARCHAR, writer VARCHAR, year VARCHAR);");

            Cursor c=db.rawQuery("SELECT * FROM movies_new ORDER BY name", null);

            if(c.getCount()==0){
                Toast.makeText(context,"RECORD NOT FOUND",Toast.LENGTH_SHORT);
                return;
            }

            while(c.moveToNext()){
                movies_name.add(c.getString(0));
                directors_name.add(c.getString(1));
                writers_name.add(c.getString(2));
                years_array.add(c.getString(3));
                rating_array.add(c.getInt(4));
            }


            lv=(ListView) findViewById(R.id.static_list);
            lv.setAdapter(new CustomAdapter(this, movies_name, directors_name,writers_name,rating_array));


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
