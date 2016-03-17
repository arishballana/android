package com.markwebtechnologies.lupus;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by arishballana on 2/29/16.
 */
public class Post_Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    Activity _context;
    List<Post_Data> post_list ;


    public Post_Adapter(Activity PostActivity, List<Post_Data> postList){   // TODO Auto-generated constructor stub
        this._context=PostActivity;
        this.post_list=postList;
        inflater = _context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        if (post_list!= null && post_list.size()!=0) {
            return post_list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private static class holder_view{
        public ImageView mimageview;
        public TextView mpostname,mposttype,moverview;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         holder_view _holder=null;
        if (convertView == null) {
            _holder = new holder_view();
            convertView = inflater.inflate(R.layout.post_view, null);
            _holder.mimageview = (ImageView) convertView
                    .findViewById(R.id.albumImage);
            _holder.mpostname = (TextView) convertView
                    .findViewById(R.id.post_name);
            _holder.mposttype = (TextView) convertView
                    .findViewById(R.id.post_type);
            _holder.moverview = (TextView) convertView
                    .findViewById(R.id.overview);
            convertView.setTag(_holder);
        } else {
            _holder = (holder_view) convertView.getTag();
        }
        if (post_list!=null&&post_list.size()!=0) {
            _holder.mpostname.setText(post_list.get(position).getPostname());
            _holder.mposttype.setText(post_list.get(position).getPosttype());
            _holder.moverview.setText(Html.fromHtml(post_list.get(position).getOverview()));
            //Picasso.with(_context).load(post_list.get(position).getImg_path()).error(R.drawable.glrc_logo).fit().into(_holder.mimageview);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(_context,display_post.class);
                intent.putExtra("content",post_list.get(position).getOverview());
                _context.startActivity(intent);
            }
        });
        return convertView;
    }

}
