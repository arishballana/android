package com.markwebtechnologies.lupus;

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
 * Created by arishballana on 3/1/16.
 */
public class Json_Data_Fetch {

    Context ct;

    public Json_Data_Fetch(Context topRatedActivity) {
        this.ct = topRatedActivity;
    }

    public final List<Post_Data> getAlbumList(String name) throws Exception {
        List<Post_Data> albumsList = null;
        String result = null;
        result = getJSONData("http://lupusresourcecenter.org/wp-json/wp/v2/"+name);
        if (result == null || result.equals("-111")) {
            Log.d("result.equals", "-111");
            albumsList = null;
        } else {
            Log.d("result ****", "" + result);

            JSONArray albumJSONArray= new JSONArray(result);
            if (albumJSONArray != null && albumJSONArray.length() > 0) {
                albumsList = new ArrayList<Post_Data>();
                for (int i = 0; i < albumJSONArray.length(); i++) {
                    JSONObject innerAlbumJSONObj = albumJSONArray.getJSONObject(i);
                    String albumId = innerAlbumJSONObj.getString("id");
                    if (albumId != null && !albumId.equals("")) {
                        Post_Data albums = new Post_Data();
                        albums.setId(albumId);
                        albums.setDate(innerAlbumJSONObj.getString("date"));
                        albums.setPostname(innerAlbumJSONObj.getJSONObject("title").getString("rendered"));
                        albums.setPosttype(innerAlbumJSONObj.getString("type"));
                        albums.setOverview(innerAlbumJSONObj.getJSONObject("content").getString("rendered"));
                        //System.out.println(innerAlbumJSONObj.getJSONArray("_links").has("https:\\/\\/api.w.org\\/featuredmedia"));
//                        JSONArray links= innerAlbumJSONObj.getJSONObject("_links").getJSONArray("https:\\/\\/api.w.org\\/featuredmedia");
//                        result=getJSONData(links.getJSONObject(1).getString("href"));
//                        if (result == null || result.equals("-111")) {
//                            Log.d("result.equals", "-111");
//                            albumsList = null;
//                        } else {
//                            Log.d("OUT ****", "" + result);
//                            JSONObject obimginner= new JSONObject(result);
//                            albums.setImg_path(obimginner.getJSONObject("guid").getJSONObject("rendered").toString());
//                        }
                        albumsList.add(albums);
                    }
                }
            }
        }
        return albumsList;
    }

    public final List<Map_Data> getMapLocations(String name) throws Exception{
        List<Map_Data> mapLocation=null;
        String result=null;
        result = getJSONData("http://lupusresourcecenter.org/wp-json/wp/v2/"+name);
            if (result == null || result.equals("-111")) {
                Log.d("result.equals", "-111");
                mapLocation = null;
            } else {
                Log.d("result ****", "" + result);

                JSONArray albumJSONArray = new JSONArray(result);
                if (albumJSONArray != null && albumJSONArray.length() > 0) {
                    mapLocation = new ArrayList<Map_Data>();
                    for (int i = 0; i < albumJSONArray.length(); i++) {
                        JSONObject innerAlbumJSONObj = albumJSONArray.getJSONObject(i);
                        String albumId = innerAlbumJSONObj.getString("id");
                        if (albumId != null && !albumId.equals("")) {
                            Map_Data locations = new Map_Data();
                            locations.setId(albumId);
                            locations.setLongitude(innerAlbumJSONObj.getJSONArray("maplist_longitude").getString(0));
                            locations.setLatitude(innerAlbumJSONObj.getJSONArray("maplist_latitude").getString(0));
                            //locations.setDescription(innerAlbumJSONObj.getString("maplist_description"));
                            locations.setContent(innerAlbumJSONObj.getJSONObject("content").getString("rendered"));
                            locations.setTitle(innerAlbumJSONObj.getJSONObject("content").getString("rendered"));
                            //locations.setImg_path(innerAlbumJSONObj.getJSONObject("_links").getJSONArray("https:\\/\\/api.w.org\\/featuredmedia").getJSONObject(1).getString("href"));
                            mapLocation.add(locations);
                        }
                    }

                }
            }
        return mapLocation;
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
