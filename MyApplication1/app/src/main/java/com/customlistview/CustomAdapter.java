package com.customlistview;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.example.arishballana.myapplication1.Main3Activity;
import com.example.arishballana.myapplication1.MainActivity;
import com.example.arishballana.myapplication1.R;

/**
 * Created by arishballana on 1/20/16.
 */

public class CustomAdapter extends BaseAdapter {
    private static LayoutInflater inflater=null;
    Context context;
    protected String[] result;
    protected int[] images;

    public CustomAdapter(Main3Activity activity, String[] list_names, int[] list_images){
        result=list_names;
        context=activity;
        images=list_images;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return result.length;
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
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View view=inflater.inflate(R.layout.program_list,null);
        holder.textView = (TextView) view.findViewById(R.id.textView1);
        holder.imageView = (ImageView) view.findViewById(R.id.imageView1);

        holder.textView.setText(result[position]);
        holder.imageView.setImageResource(images[position]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = (String) result[position];
         //       Toast.makeText(context, "IT's " + value + " HAVE A NICE DAY", Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(context)
                        .setTitle("NOTIFICATION")
                        .setMessage("IT's " + value + " HAVE A NICE DAY")
                        .setIcon(R.drawable.abc)
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();


        //        NotificationManager notificationManager= (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);

        //        AlarmManager alarmManager = (AlarmManager) getSystemService
            }
        });
        return view;

    }
}
