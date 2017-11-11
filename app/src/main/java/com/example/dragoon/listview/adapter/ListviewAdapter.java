package com.example.dragoon.listview.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.dragoon.listview.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragoon on 2017.10.31..
 */

public class ListviewAdapter extends BaseAdapter implements  Filterable {
    private Context context;
    private List<Informations> list;
    public static final String USER_NAME = "name";
    public static final String USER_Information = "information";
    public static final String USER_Id = "id";


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
        TextView info=view.findViewById(R.id.textView2);

        name.setText(list.get(position).getName());
        info.setText(list.get(position).getInformation());

        view.setTag(list.get(position));
        return view;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                    list = (List<Informations>) results.values;
                    notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Informations> FilteredArrayNames = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < list.size(); i++) {

                    JSONObject object = new JSONObject();
                    String dataNames = String.valueOf(list.get(i).getName());
                    String datainfo= String.valueOf(list.get(i).getInformation());
                    int pos=list.get(i).getId();
                    try {
                        object.put(USER_Id,pos );
                        object.put(USER_NAME, dataNames);
                        object.put(USER_Information, datainfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Informations information = new Informations(pos, dataNames, datainfo);

                    if (dataNames.toLowerCase().startsWith(constraint.toString()))  {
                        FilteredArrayNames.add(information);
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;

                Log.e("VALUES", results.values.toString());
                Log.e("VALUES", String.valueOf(results.count));


                return results;
            }
        };

        return filter;
    }


}
