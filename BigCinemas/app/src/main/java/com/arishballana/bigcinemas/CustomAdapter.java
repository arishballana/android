package com.arishballana.bigcinemas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arishballana on 2/2/16.
 */
public class CustomAdapter extends BaseAdapter {


    ArrayList<String> movies=new ArrayList<String>();
    ArrayList<String> directors=new ArrayList<String>();
    ArrayList<String> writers=new ArrayList<String>();
    ArrayList<Integer> ratings=new ArrayList<Integer>();

    Context context;
    private static LayoutInflater inflater=null;
    Holder holder=new Holder();

    public CustomAdapter(Static_list activity, ArrayList<String> movies_List, ArrayList<String> directors_List, ArrayList<String> writers_List,ArrayList<Integer> ratings_List) {
        movies=movies_List;
        context=activity;
        directors=directors_List;
        writers=writers_List;
        ratings=ratings_List;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Holder
    {
        TextView tv,tv1,tv2;
       // RatingBar ratingBar;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.movie_name_textView);
        holder.tv.setText(movies.get(position));
        //holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        //holder.img.setImageResource(imageId[position]);
        holder.tv1 = (TextView) rowView.findViewById(R.id.director_name_textView);
        holder.tv1.setText(directors.get(position));

        //holder.tv2 = (TextView) rowView.findViewById(R.id.writer_textView);
        //holder.tv2.setText(writers[position]);

        //holder.ratingBar = (RatingBar) rowView.findViewById(R.id.ratingBar_ListItem);
        //int rate=ratings.get(position);
        //System.out.println(rate);
        //holder.ratingBar.setNumStars(rate);
        //holder.ratingBar.setRating(Float.parseFloat(ratings.get(position).toString()));
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                v.getContext().startActivity(new Intent(context, Movie_Description.class).putExtra("name", movies.get(position)));
            }
        });

        Animation animation= AnimationUtils.loadAnimation(context, R.anim.fade_in);
        animation.setDuration(200);
        rowView.startAnimation(animation);
        return rowView;
    }
}
