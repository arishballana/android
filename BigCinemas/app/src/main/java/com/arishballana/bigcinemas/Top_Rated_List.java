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

public class Top_Rated_List extends AppCompatActivity {

    List<TopRatedDAta> topRatedList = new ArrayList<TopRatedDAta>();
    private ListView mainList;
    private Handler mHandler = null;
    private LayoutInflater inflater;
    boolean loadingMore;
    private NewRelAdapter mainListAdapter;
    private ProgressDialog dialog = null;
    int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top__rated__list);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.top_rated_layout);
        relativeLayout.setAnimation(animation);

        mainList = (ListView) findViewById(R.id.top_rated_list);
        mHandler = new Handler();
        setTopRatedList();

    }

    private void setTopRatedList() {
        try {

            dialog = ProgressDialog.show(Top_Rated_List.this, "",
                    "Loading content please wait...");
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    JSon_Data_Fetchi h = new JSon_Data_Fetchi(Top_Rated_List.this);
                    try {
                        if (Utility.isOnline(Top_Rated_List.this)) {

                            topRatedList = h.getAlbumList(pageNo, "top_rated");

                            if (topRatedList == null) {
                                Log.d("albumlist is null", "-111");
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Top_Rated_List.this,
                                                "No Data Found",
                                                Toast.LENGTH_LONG).show();
                                        if (dialog != null
                                                && dialog.isShowing())
                                            dialog.dismiss();
                                        loadingMore = false;
                                        mainListAdapter = new NewRelAdapter(
                                                Top_Rated_List.this, topRatedList);

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
                                                Top_Rated_List.this, topRatedList);

                                        mainList.setAdapter(mainListAdapter);
                                        mainListAdapter.notifyDataSetChanged();

                                        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @Override
                                            public void onItemClick(
                                                    AdapterView<?> arg0,
                                                    View arg1, int arg2,
                                                    long arg3) {
                                                // TODO Auto-generated method
                                                Intent i= new Intent(Top_Rated_List.this,description_movie.class);
                                                int position=arg2;
                                                i.putExtra("title", topRatedList.get(position).getTitle().toString());
                                                i.putExtra("path", topRatedList.get(position).getPosterPAth().toString());
                                                i.putExtra("background",topRatedList.get(position).getBackgraoudPath().toString());
                                                i.putExtra("date", topRatedList.get(position).getRealeseDate().toString());
                                                i.putExtra("id", topRatedList.get(position).getId().toString());
                                                i.putExtra("pop", topRatedList.get(position).getPopularity().toString());
                                                i.putExtra("ovw", topRatedList.get(position).getOverView().toString());
                                                i.putExtra("votecnt", topRatedList.get(position).getVote_count().toString());
                                                i.putExtra("voteAvg", topRatedList.get(position).getVote_average());
                                                i.putExtra("adult", topRatedList.get(position).getAdult());
                                                i.putExtra("vid", topRatedList.get(position).getVideo());
                                                startActivity(i);

                                                // stub
//
//                                                System.out.println(arg0.getItemAtPosition(arg2)+" HELLO\n\n\n\n");
//                                                ListView lv= (ListView) findViewById(R.id.top_rated_list);
//                                                final String name = lv.getAdapter().getItem(arg2).toString();
                                                if (arg2 < topRatedList.size()) {

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
                                    Toast.makeText(Top_Rated_List.this,
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

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
