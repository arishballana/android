package com.markwebtechnologies.lupus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class display_post extends AppCompatActivity {

    TextView post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_post);

        post= (TextView) findViewById(R.id.Post_textview);

        post.setText(Html.fromHtml(getIntent().getExtras().get("content").toString()));

    }
}
