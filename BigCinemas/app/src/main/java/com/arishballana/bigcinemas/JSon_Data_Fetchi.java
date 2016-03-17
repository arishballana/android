package com.arishballana.bigcinemas;

import android.content.Context;
import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by arishballana on 2/10/16.
 */

public class JSon_Data_Fetchi {

    Context ct;

    public JSon_Data_Fetchi(Context topRatedActivity) {
        this.ct = topRatedActivity;
    }

    public final List<TopRatedDAta> getAlbumList(int pageNo,String name) throws Exception {
        List<TopRatedDAta> albumsList = null;
        String result = null;
        result = getJSONData("http://api.themoviedb.org/3/movie/"+name+"?api_key=71bf7cb15b793405f4b9b1aaf72460ce&page="+pageNo);
        if (result == null || result.equals("-111")) {
            Log.d("result.equals", "-111");
            albumsList = null;
        } else {
            Log.d("result ****", "" + result);
            JSONObject jObject = new JSONObject(result);
            JSONArray albumJSONArray = jObject.getJSONArray("results");
            if (albumJSONArray != null && albumJSONArray.length() > 0) {
                albumsList = new ArrayList<TopRatedDAta>();
                for (int i = 0; i < albumJSONArray.length(); i++) {
                    JSONObject innerAlbumJSONObj = albumJSONArray.getJSONObject(i);
                    String albumId = innerAlbumJSONObj.getString("id");
                    if (albumId != null && !albumId.equals("")) {
                        TopRatedDAta albums = new TopRatedDAta();
                        albums.setId(albumId);
                        albums.setAdult(innerAlbumJSONObj.getString("adult"));
                        albums.setBackgraoudPath(innerAlbumJSONObj.getString("backdrop_path"));
                        albums.setOriginalTitle(innerAlbumJSONObj.getString("original_title"));
                        albums.setOverView(innerAlbumJSONObj.getString("overview"));
                        albums.setPopularity(innerAlbumJSONObj.getString("popularity"));
                        albums.setPosterPAth(innerAlbumJSONObj.getString("poster_path"));
                        albums.setRealeseDate(innerAlbumJSONObj.getString("release_date"));
                        albums.setTitle(innerAlbumJSONObj.getString("title"));
                        albums.setVideo(innerAlbumJSONObj.getString("video"));
                        albums.setVote_average(innerAlbumJSONObj.getString("vote_average"));
                        albums.setVote_count(innerAlbumJSONObj.getString("vote_count"));
                        albumsList.add(albums);
                    }
                }
            }
        }
        return albumsList;
    }

    public final TopRatedDAta getMovie(int pageNo,String id) throws Exception {
//        List<TopRatedDAta> albums_List = null;
        String result = null;
        result = getJSONData("http://api.themoviedb.org/3/movie/"+id+"?api_key=71bf7cb15b793405f4b9b1aaf72460ce&page="+pageNo);
        if (result == null || result.equals("-111")) {
            Log.d("result.equals", "-111");
//            albums_List = null;
        } else {
            Log.d("result ****", "" + result);
            JSONObject jObject = new JSONObject(result);
            JSONArray albumJSONArray = jObject.getJSONArray("results");
            if (albumJSONArray != null && albumJSONArray.length() > 0) {
//                albums_List = new ArrayList<TopRatedDAta>();
                for (int i = 0; i < albumJSONArray.length(); i++) {
                    JSONObject innerAlbumJSONObj = albumJSONArray.getJSONObject(i);
                    String albumId = innerAlbumJSONObj.getString("id");
                    if (albumId != null && !albumId.equals("")) {
                        TopRatedDAta albums = new TopRatedDAta();
                        albums.setId(albumId);
                        albums.setAdult(innerAlbumJSONObj.getString("adult"));
                        albums.setBackgraoudPath(innerAlbumJSONObj.getString("backdrop_path"));
                        albums.setOriginalTitle(innerAlbumJSONObj.getString("original_title"));
                        albums.setOverView(innerAlbumJSONObj.getString("overview"));
                        albums.setPopularity(innerAlbumJSONObj.getString("popularity"));
                        albums.setPosterPAth(innerAlbumJSONObj.getString("poster_path"));
                        albums.setRealeseDate(innerAlbumJSONObj.getString("release_date"));
                        albums.setTitle(innerAlbumJSONObj.getString("title"));
                        albums.setVideo(innerAlbumJSONObj.getString("video"));
                        albums.setVote_average(innerAlbumJSONObj.getString("vote_average"));
                        albums.setVote_count(innerAlbumJSONObj.getString("vote_count"));
                        return  albums;
// albums_List.add(albums);
                    }
                }
            }
        }
//        return albums_List;
        return null;
    }

    public String getJSONData(String url) throws Exception {
        Log.d("url ", "url" + url);
        String result = "";
        if (!Utility.isOnline(ct)) {
            // throw new Exception("Internet Connection is not available");
            return "-111";
        } else {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                result = convertStreamToString(inputStream);
                inputStream.close();
                httpClient = null;
                httpGet.abort();
            }

            return result;
        }
    }

    private String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        reader.close();
        return sb.toString();
    }

}
