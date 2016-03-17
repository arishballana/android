package com.arishballana.bigcinemas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class upcoming_list extends AppCompatActivity {

    List<TopRatedDAta> upcomingList = new ArrayList<TopRatedDAta>();
    private ListView mainList;
    private Handler mHandler = null;
    private LayoutInflater inflater;
    boolean loadingMore;
    private NewRelAdapter mainListAdapter;
    // View footer;
    private ProgressDialog dialog = null;
    int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_list);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.upcoming_list_layout);
        relativeLayout.setAnimation(animation);

        mainList = (ListView) findViewById(R.id.upcoming_list);
        // footer = inflater.inflate(R.layout.footer, null);
        mHandler = new Handler();
        // get TopRated Movie and set on listview....
        upComingList();

    }

    private void upComingList() {
        try{
            dialog = ProgressDialog.show(upcoming_list.this, "",
                    "Loading content please wait...");
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    JSon_Data_Fetchi h = new JSon_Data_Fetchi(upcoming_list.this);
                    try {
                        if (Utility.isOnline(upcoming_list.this)) {

                            upcomingList = h.getAlbumList(pageNo, "upcoming");

                            if (upcomingList == null) {
                                Log.d("albumlist is null", "-111");
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(upcoming_list.this,
                                                "No Data Found",
                                                Toast.LENGTH_LONG).show();
                                        if (dialog != null
                                                && dialog.isShowing())
                                            dialog.dismiss();
                                        loadingMore = false;
                                        mainListAdapter = new NewRelAdapter(
                                                upcoming_list.this, upcomingList);

                                        mainList.setAdapter(mainListAdapter);
                                        mainListAdapter.notifyDataSetChanged();

                                    }
                                });
                            } else {
                                mHandler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (dialog != null
                                                && dialog.isShowing())
                                            dialog.dismiss();
                                        mainListAdapter = new NewRelAdapter(
                                                upcoming_list.this, upcomingList);

                                        mainList.setAdapter(mainListAdapter);
                                        mainListAdapter.notifyDataSetChanged();

                                        // mainList.setAdapter(mainListAdapter);

                                        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @Override
                                            public void onItemClick(
                                                    AdapterView<?> arg0,
                                                    View arg1, int arg2,
                                                    long arg3) {
                                                // TODO Auto-generated method

                                                Intent i= new Intent(upcoming_list.this,description_movie.class);
                                                int position=arg2;
                                                i.putExtra("title", upcomingList.get(position).getTitle().toString());
                                                i.putExtra("path", upcomingList.get(position).getPosterPAth().toString());
                                                i.putExtra("background",upcomingList.get(position).getBackgraoudPath().toString());
                                                i.putExtra("date", upcomingList.get(position).getRealeseDate().toString());
                                                i.putExtra("id", upcomingList.get(position).getId().toString());
                                                i.putExtra("pop", upcomingList.get(position).getPopularity().toString());
                                                i.putExtra("ovw", upcomingList.get(position).getOverView().toString());
                                                i.putExtra("votecnt", upcomingList.get(position).getVote_count().toString());
                                                i.putExtra("voteAvg", upcomingList.get(position).getVote_average());
                                                i.putExtra("adult", upcomingList.get(position).getAdult());
                                                i.putExtra("vid", upcomingList.get(position).getVideo());
                                                startActivity(i);
                                                // stub
                                                if (arg2 < upcomingList.size()) {

                                                }
                                            }
                                        });

                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(upcoming_list.this,
                                            "No Network Detected",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
