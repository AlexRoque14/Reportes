package com.example.reportes.directory;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reportes.R;

import java.util.ArrayList;


public class AdapterReport extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Report> items;

    public AdapterReport (Activity activity, ArrayList<Report> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Report> category) {
        for (int i = 0 ; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_report, null);
        }

        Report dir = items.get(position);

        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(dir.getTitle());

        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(dir.getDescription());

        TextView imagen = (TextView) v.findViewById(R.id.image);
        imagen.setText(dir.getImage());

        return v;
    }
}