package com.example.dragoon.listview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragoon.listview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dragoon on 2017.10.31..
 */

public class ListviewAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Informations> informationList;
    private List<Informations> usedList;

    public ListviewAdapter(Context context, List<Informations> list) {
        this.context = context;
        this.informationList = list;
        this.usedList = new ArrayList<>(list);
    }

    @Override
    public int getCount() {
        return getInformationList().size();
    }

    @Override
    public Object getItem(int position) {
        return getInformationList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = View.inflate(context, R.layout.row, null);
        TextView name = view.findViewById(R.id.textView);
        TextView info = view.findViewById(R.id.textView2);
        ImageView imageView = view.findViewById(R.id.adapterCircleImageView);
        name.setText(getInformationList().get(position).getName());
        info.setText(getInformationList().get(position).getInformation());
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        // imageView.setBackgroundColor(color);
        imageView.setColorFilter(color);
        //imageView.setDrawingCacheBackgroundColor(color);
        view.setTag(getInformationList().get(position));
        return view;
    }


    @Override
    public Filter getFilter() {
        return filter;
    }


    public List<Informations> getInformationList() {
        return usedList;
    }


    private Filter filter = new Filter() {
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            usedList = (List<Informations>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                results.count = informationList.size();
                results.values = new ArrayList<>(informationList);
            } else {
                List<Informations> filteredList = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                for (Informations informations : informationList) {
                    if (informations.getName().toLowerCase().contains(constraint.toString())) {
                        filteredList.add(informations);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }
    };

}
