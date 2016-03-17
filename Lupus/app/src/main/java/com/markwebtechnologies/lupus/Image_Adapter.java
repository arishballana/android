package com.markwebtechnologies.lupus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by arishballana on 2/6/16.
 */

public class Image_Adapter extends BaseAdapter {

    private Context mContext;
    private String[] mMarkers;
    private int[] mImages;
    public static LayoutInflater inflater=null;
    private GridView gridView;

    public Image_Adapter(Context c, String[] markers_list, int[] image_list, GridView gridView1){
        mContext=c;
        mMarkers=markers_list;
        mImages=image_list;
        gridView=gridView1;
        inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mMarkers.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        ImageView img;
        TextView tv;
    }

    Holder holder=new Holder();

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rowview;
        rowview= inflater.inflate(R.layout.grid_view_list,null);
        holder.img= (ImageView) rowview.findViewById(R.id.imageView2);
        holder.img.setImageResource(mImages[position]);
        holder.tv= (TextView) rowview.findViewById(R.id.editText);
        holder.tv.setText(mMarkers[position]);
        rowview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                float currentXposition = event.getX();
                float currentYposition = event.getY();
                int position = gridView.pointToPosition((int) currentXposition, (int) currentYposition);

                return false;
            }
        });

        rowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMarkers[position].equals("BLOG")) {
                    try {
                        Intent intent = new Intent(mContext, blog_activity.class);
                        mContext.startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                if (mMarkers[position].equals("LOCATE")) {

                    try {
                        Intent intent = new Intent(mContext, display_locations.class);
                        mContext.startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (mMarkers[position].equals("EMAIL")) {
                    try {
                        Intent intent =new Intent(mContext, composeMail.class);
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return  rowview;

    }
}
