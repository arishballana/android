package com.arishballana.bigcinemas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Data_updation extends Activity implements View.OnClickListener {

    Button btn_add;
    EditText name_editText,director_editText,writer_editText,year_editText;
    SQLiteDatabase db;
    RatingBar ratingBar;
    int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_updation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        AbsoluteLayout relativeLayout= (AbsoluteLayout)findViewById(R.id.layout_data_update);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        relativeLayout.setAnimation(animation);

        TextView next_button= (TextView) findViewById(R.id.next_button_textView);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=  new Intent(Data_updation.this,Tab_Bar.class);
                startActivity(i);
            }
        });

        name_editText= (EditText) findViewById(R.id.name_editText);
        director_editText = (EditText) findViewById(R.id.director_editText);
        writer_editText = (EditText) findViewById(R.id.writer_editText);
        year_editText = (EditText) findViewById(R.id.year_editText);
        ratingBar= (RatingBar) findViewById(R.id.ratingBar);
        rating=ratingBar.getNumStars();
        btn_add= (Button) findViewById(R.id.add_movies_button);
        btn_add.setOnClickListener(this);
        db=openOrCreateDatabase("Look_Cinemas", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS movies_new(name VARCHAR PRIMARY KEY, director VARCHAR, writer VARCHAR, year VARCHAR,rating INTEGER);");



    }
    public void onClick(View view) {

        if(view == btn_add){
            try{

                if(name_editText.getText().toString().trim().length() == 0 || writer_editText.getText().toString().trim().length() == 0 ||
                        director_editText.getText().toString().trim().length() ==0 || year_editText.getText().toString().trim().length() == 0){

                    Toast.makeText(Data_updation.this,"PLEASE ENTER ALL THE VALUES",Toast.LENGTH_SHORT).show();
                    return;
                }

                db.execSQL("INSERT INTO movies_new VALUES('" + name_editText.getText().toString().toUpperCase().trim() + "'," +
                        "'" + director_editText.getText().toString().toUpperCase().trim() +
                        "','" + writer_editText.getText().toString().toUpperCase().trim() + "'," +
                        "'" + year_editText.getText().toString().trim() + "'," +
                        "'" + ratingBar.getRating() + "');");
                Toast.makeText(Data_updation.this,"RECORD SUCCESSFULLY ADDED",Toast.LENGTH_SHORT).show();
                clearText();
            }catch(Exception e){
                showMessage("Error","Same Record Already Exist");
            }
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
        name_editText.setText("");
        director_editText.setText("");
        writer_editText.setText("");
        year_editText.setText("");
        ratingBar.setRating(0.0f);
    }
}
