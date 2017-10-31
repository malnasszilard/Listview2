package com.example.dragoon.listview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dragoon.listview.R;

import java.util.List;

/**
 * Created by dragoon on 2017.10.31..
 */

public class ListviewAdapter extends BaseAdapter{
    private Context context;
    private List<Informations> list;

    public ListviewAdapter(Context context, List<Informations> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.row,null);
        TextView name=view.findViewById(R.id.textView);


        name.setText(list.get(position).getName());

        view.setTag(list.get(position));
        return view;
    }


}
