package com.arishballana.bigcinemas;

import android.app.IntentService;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by arishballana on 2/11/16.
 */
public class Background extends IntentService {

    /*
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public Background() {
        super("BACKGROUND PROCESS");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("TAG", "STARTING SERVICE");

        String urlToDownload = intent.getStringExtra("url");
        try {
            Download_Data(urlToDownload,intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("TAG","SERVICE STOPPING");
    }

    private String[] Download_Data(String requestUrl, Intent intent) throws IOException{

        final int UPDATE_PROGRESS = 8344;
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");

        InputStream inputStream=null;
        HttpURLConnection urlConnection=null;

        URL url=new URL(requestUrl);
        urlConnection = (HttpURLConnection)url.openConnection();

        urlConnection.setRequestMethod("GET");
        int requestCode = urlConnection.getResponseCode();

        if(requestCode == 200){
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            OutputStream output = new FileOutputStream("/sdcard/song.mp3");


            int fileLength = urlConnection.getContentLength();
            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = inputStream.read(data)) != -1)
            {

                System.out.println("IN DOWNLOAD DATA IF");
                total += count;
                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress", (int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            inputStream.close();


            System.out.println("IN DOWNLOAD DATA");
            return null;
        }else{
            System.out.println("IN DOWNLOAD DATA ELSE");

        }
        return null;
    }

}
