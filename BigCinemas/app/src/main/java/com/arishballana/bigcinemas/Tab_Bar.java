package com.arishballana.bigcinemas;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;


public class Tab_Bar extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab__bar);



        TabHost tabHost=getTabHost();
        TabHost.TabSpec spec;
        Intent intent;


        intent=new Intent().setClass(this,Top_Rated_List.class);//.putExtra("name",Movie_name);
        spec=tabHost.newTabSpec("TOP RATED").setIndicator("Top Rated").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this,upcoming_list.class);
        spec=tabHost.newTabSpec("UPCOMING").setIndicator("Upcoming").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this,popular_list.class);
        spec=tabHost.newTabSpec("POPULAR").setIndicator("Popular").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this,Static_list.class);
        spec=tabHost.newTabSpec("STATIC LIST").setIndicator("Static List").setContent(intent);
        tabHost.addTab(spec);


    }
}
