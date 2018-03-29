package com.xyc.locationservice.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xyc.guguviews.utils.DateUtils;
import com.xyc.locationservice.R;
import com.xyc.locationservice.logic.model.LocationModel;

import java.util.List;

/**
 * Created by hasee on 2018/3/29.
 */

public class LocationAdapter extends BaseAdapter {
    private Context context;
    private List<LocationModel> locationModels;
    private LayoutInflater inflater;

    public LocationAdapter(Context context, List<LocationModel> locationModels) {
        this.context = context;
        this.locationModels = locationModels;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return locationModels.size();
    }

    @Override
    public Object getItem(int position) {
        return locationModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View containView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (containView == null) {
            viewHolder = new ViewHolder();
            containView = inflater.inflate(R.layout.location_item_list, null);
            viewHolder.tvLocation = containView.findViewById(R.id.tvLocation);
            viewHolder.updateTime = containView.findViewById(R.id.updateTime);
            viewHolder.tvToLocation = containView.findViewById(R.id.tvToLocation);
            containView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) containView.getTag();
        }
        LocationModel locationModel = locationModels.get(position);
        if (locationModel != null) {
            viewHolder.tvLocation.setText(locationModel.getAddress());
            viewHolder.updateTime.setText(locationModel.getLastTime());
            viewHolder.tvToLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return containView;
    }

    private class ViewHolder {
        TextView tvLocation;
        TextView updateTime;
        TextView tvToLocation;
    }
}
