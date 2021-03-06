package com.example.splash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.splash.R;
import com.example.splash.models.Doctores;

public class DoctorAdaptador extends ArrayAdapter<Doctores> {
    Context context;
    ImageLoader queue;
    private class ViewHolder {
        TextView first_name;
        TextView last_name;
        NetworkImageView image;
        private ViewHolder() {
        }
    }
    public DoctorAdaptador(Context context, List<Doctores> items, ImageLoader _queue) {
        super(context, 0, items);
        this.context = context;
        this.queue=_queue;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Doctores rowItem = (Doctores) getItem(position);
        LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_doctores, null);
            holder = new ViewHolder();
            holder.first_name = (TextView) convertView.findViewById(R.id.first_name);
            holder.last_name = (TextView) convertView.findViewById(R.id.last_name);
            holder.image=(NetworkImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.first_name.setText(rowItem.first_name);
        holder.last_name.setText(rowItem.last_name);
        holder.image.setImageUrl(rowItem.url_image,queue);
        return convertView;
    }

}