package com.markwebtechnologies.lupus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arishballana on 3/15/16.
 */
public class Map_Adapter {

    List<Map_Data> map_locations ;

    public ArrayList<holder_view> Map_Adapter(List<Map_Data> maplocations){
        this.map_locations=maplocations;
        ArrayList<holder_view> holders_list=new ArrayList<holder_view>();
        int len=getCount();
        if (map_locations!=null && len!=0) {
            for(int i=0;i<len;i++){
                holder_view _holder=new holder_view();
                _holder.longitude = Double.parseDouble(map_locations.get(i).getLongitude());
                _holder.latitude = Double.parseDouble(map_locations.get(i).getLatitude());
                _holder.desc = map_locations.get(i).getTitle();
                //_holder.mimageview.setImageResource(R.drawable.glrc_logo);
                holders_list.add(_holder);
            }
        }
        return holders_list;
    }

    public int getCount() {
        if (map_locations!= null && map_locations.size()!=0) {
            return map_locations.size();
        }else {
            return 0;
        }
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }



}
