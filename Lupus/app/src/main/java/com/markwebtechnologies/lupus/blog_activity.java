package com.markwebtechnologies.lupus;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

public class blog_activity extends AppCompatActivity {

    List<Post_Data> post_list = new ArrayList<Post_Data>();
    private ListView mainList;
    private Handler mHandler = null;
    private LayoutInflater inflater;
    boolean loadingMore;
    private Post_Adapter mainListAdapter;
    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_activity);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.blog_activity_layout);
        relativeLayout.setAnimation(animation);

        mainList = (ListView) findViewById(R.id.post_listview);
        mHandler = new Handler();
        setPostList();

    }

    private void setPostList() {
        try {

            dialog = ProgressDialog.show(blog_activity.this, "",
                    "Loading content please wait...");
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Json_Data_Fetch h = new Json_Data_Fetch(blog_activity.this);
                    try {
                        if (Utility.isOnline(blog_activity.this)) {

                            post_list = h.getAlbumList("posts");

                            if (post_list == null) {
                                Log.d("albumlist is null", "-111");
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(blog_activity.this,
                                                "No Data Found",
                                                Toast.LENGTH_LONG).show();
                                        if (dialog != null
                                                && dialog.isShowing())
                                            dialog.dismiss();
                                        loadingMore = false;
                                        mainListAdapter = new Post_Adapter(
                                                blog_activity.this, post_list);

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
                                        mainListAdapter = new Post_Adapter(
                                                blog_activity.this, post_list);

                                        mainList.setAdapter(mainListAdapter);
                                        mainListAdapter.notifyDataSetChanged();

                                        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @Override
                                            public void onItemClick(
                                                    AdapterView<?> arg0,
                                                    View arg1, int arg2,
                                                    long arg3) {
                                                // TODO Auto-generated method
                                                /*Intent i= new Intent(blog_activity.this,description_movie.class);
                                                int position=arg2;
                                                i.putExtra("title", topRatedList.get(position).getTitle().toString());
                                                i.putExtra("path", topRatedList.get(position).getPosterPAth().toString());
                                                i.putExtra("date", topRatedList.get(position).getRealeseDate().toString());
                                                i.putExtra("id", topRatedList.get(position).getId().toString());
                                                i.putExtra("pop", topRatedList.get(position).getPopularity().toString());
                                                i.putExtra("ovw", topRatedList.get(position).getOverView().toString());
                                                i.putExtra("votecnt", topRatedList.get(position).getVote_count().toString());
                                                i.putExtra("voteAvg", topRatedList.get(position).getVote_average());
                                                i.putExtra("adult", topRatedList.get(position).getAdult());
                                                i.putExtra("vid", topRatedList.get(position).getVideo());
                                                startActivity(i);*/

                                                // stub
//
//                                                System.out.println(arg0.getItemAtPosition(arg2)+" HELLO\n\n\n\n");
//                                                ListView lv= (ListView) findViewById(R.id.top_rated_list);
//                                                final String name = lv.getAdapter().getItem(arg2).toString();
                                                if (arg2 < post_list.size()) {

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
                                    Toast.makeText(blog_activity.this,
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

