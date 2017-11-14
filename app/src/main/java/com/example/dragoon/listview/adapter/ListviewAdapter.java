package com.example.dragoon.listview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.dragoon.listview.R;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.row, null);
        TextView name = view.findViewById(R.id.textView);
        TextView info = view.findViewById(R.id.textView2);

        name.setText(getInformationList().get(position).getName());
        info.setText(getInformationList().get(position).getInformation());

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
