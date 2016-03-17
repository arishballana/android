package com.arishballana.bigcinemas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Movie_Description extends AppCompatActivity {

    TextView mMovie_name,mDirector_name,mWriter_name,mYear_name;
    RatingBar mRatingBar;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__description);

        db=openOrCreateDatabase("Look_Cinemas", Context.MODE_PRIVATE, null);
        Bundle extras= getIntent().getExtras();

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.movie_description_layout);
        relativeLayout.setAnimation(animation);


        final String MOVIE_NAME=extras.getString("name");

        mMovie_name = (TextView) findViewById(R.id.movie_name);
        mDirector_name = (TextView) findViewById(R.id.director_name);
        mWriter_name = (TextView) findViewById(R.id.writer_name);
        mYear_name = (TextView) findViewById(R.id.year_name);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar2);

        Cursor c=db.rawQuery("SELECT * FROM movies_new WHERE name='"+MOVIE_NAME+"'", null);

        //System.out.println(c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4)+" "+c.getString(5));

        if(c.moveToFirst())
        {
            mMovie_name.setText(c.getString(0));
            mDirector_name.setText(c.getString(1));
            mWriter_name.setText(c.getString(2));
            mYear_name.setText(c.getString(3));
            mRatingBar.setRating(Float.parseFloat(c.getString(4)));
        }
        else
        {
            showMessage("Error", "Invalid Rollno");
            clearText();
        }
    }


    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        mMovie_name.setText("");
        mDirector_name.setText("");
        mWriter_name.setText("");
        mYear_name.setText("");
        mRatingBar.setRating(0.0f);
     }
}
