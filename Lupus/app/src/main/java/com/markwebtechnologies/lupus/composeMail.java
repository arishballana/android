package com.markwebtechnologies.lupus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class composeMail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);

        Button send_mail=(Button) findViewById(R.id.send_mail);
        send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmail();
            }
        });
    }

    private void sendmail() {
        Log.i("SEND MAIL","");
        String[] TO = {"arishbalana@gmail.com"};
        Intent emailIntent=new Intent(Intent.ACTION_SEND);

        EditText subject,msg;
        subject= (EditText) findViewById(R.id.email_subject);
        msg= (EditText) findViewById(R.id.email_message);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("ok", "FINISHED SENDING EMAIL....");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(composeMail.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
