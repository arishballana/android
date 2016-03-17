package com.arishballana.bigcinemas;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by arishballana on 2/17/16.
 */
public class NewRelAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    Activity _context;
    List<TopRatedDAta> topRatedList ;
    private Bitmap mbitmap;
    ImageLoader imageLoader = ImageLoader.getInstance();

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.idmb_logo)
            .showImageForEmptyUri(R.drawable.idmb_logo)
            .showImageOnFail(R.drawable.idmb_logo).cacheInMemory(true)
            .cacheOnDisc(true).considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();
    public NewRelAdapter(Activity topRatedActivity,
                         List<TopRatedDAta> topRatedList) {
        // TODO Auto-generated constructor stub
        this._context=topRatedActivity;
        this.topRatedList=topRatedList;
        inflater = _context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (topRatedList!= null && topRatedList.size()!=0) {
            return topRatedList.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder _holder = null;
        if (convertView == null) {
            _holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_album, null);
            _holder._albumImg = (ImageView) convertView
                    .findViewById(R.id.albumImage);
            _holder._albumText = (TextView) convertView
                    .findViewById(R.id.album_txt_01);
            _holder.releaseDate = (TextView) convertView
                    .findViewById(R.id.album_txt_02);
            convertView.setTag(_holder);
        } else {
            _holder = (ViewHolder) convertView.getTag();
        }
        if (topRatedList!=null&&topRatedList.size()!=0) {
            _holder._albumText.setText(topRatedList.get(position).getTitle());
            _holder.releaseDate.setText(topRatedList.get(position).getRealeseDate());
//            mbitmap = getBitmapFromUrl("http://image.tmdb.org/t/p/w500/"+topRatedList.get(position).getBackgraoudPath());
//            _holder._albumImg.setImageBitmap(mbitmap);
            Picasso.with(_context).load("http://image.tmdb.org/t/p/w500/"+topRatedList.get(position).getPosterPAth()).error(R.drawable.idmb_logo).resize(68,68).into(_holder._albumImg);

//            Picasso.Builder builder = new Picasso.Builder(_holder._albumImg.getContext());
//            builder.listener(new Picasso.Listener() {
//                @Override
//                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                    exception.printStackTrace();
//                }
//            });
//            builder.build().load("http://image.tmdb.org/t/p/w500/"+topRatedList.get(position).getPosterPAth()).resize(68,68).into(_holder._albumImg);



//			imageLoader.displayImage("http://image.tmdb.org/t/p/w300"+topRatedList.get(position).getPosterPAth(),
//					_holder._albumImg, options);


        }
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                v.getContext().startActivity(new Intent(_context, description_movie.class).putExtra("name", topRatedList.get(position).getId()));
//            }
//        });


        Animation animation= AnimationUtils.loadAnimation(_context, R.anim.slide_in_right);
        animation.setDuration(200);
        convertView.startAnimation(animation);

        return convertView;
    }

    public Bitmap getBitmapFromUrl(String src) {
        try {
            URL url = new URL(src);
            System.out.println(src);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            InputStream ip=con.getInputStream();
            Bitmap bmp= BitmapFactory.decodeStream(ip);
            return  bmp;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static class ViewHolder {
        private ImageView _albumImg;
        private TextView _albumText, releaseDate;

    }
}
