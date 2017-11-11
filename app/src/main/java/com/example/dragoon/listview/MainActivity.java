package com.example.dragoon.listview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dragoon.listview.adapter.Informations;
import com.example.dragoon.listview.adapter.ListviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShowNewInformationsDialog.Presenter {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String PREFERENCES_NAME = "userdetails";
    public static final String USER_NAME = "name";
    public static final String USER_Information = "information";
    public static final String USER_Id = "id";
    public SharedPreferences userDetails;
    private ListView personView;
    private ListviewAdapter listviewAdapter;
    private List<Informations> list;
    EditText nameText, informations;
    Button save, load;
    JSONArray array = new JSONArray();
    Context context = this;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.editText2);
        informations = findViewById(R.id.editText);
        save = findViewById(R.id.saveButton);
        load = findViewById(R.id.loadButton);
        personView = findViewById(R.id.listview);
        list = new ArrayList<>();
        searchView = findViewById(R.id.searchText);




        userDetails =  getApplicationContext().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        String rawJSON = userDetails.getString(TAG,"");
        loadJson(rawJSON);
        updateList();

        personView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SharedPreferences.Editor editor = userDetails.edit();
                editor.putInt(USER_Id, position);
                editor.commit();
                showNewinformationsDialog(list.get(position));
            }
        });


        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                userDetails = getApplicationContext().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
                String rawJSON = userDetails.getString(TAG,"");
                loadJson(rawJSON);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArray();
            }
        });


        searchView.setQueryHint("Loading...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                MainActivity.this.listviewAdapter.getFilter().filter(s);
                // MainActivity.this.listviewAdapter.getFilter().filter(s);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
    }



    @Override
    public void onItemSaved(Informations informations) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (((JSONObject) array.get(i)).getInt(USER_Id) == informations.getId()) {
                    array.put(i, informations.toJsonObject());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateArray();

    }


    public JSONObject writeJSON(String name, String info) {
        JSONObject object = new JSONObject();
        try {
            int id = array.length();
            object.put(USER_Id, id);
            object.put(USER_NAME, name);
            object.put(USER_Information, info);
            Informations information = new Informations(id, name, info);
            list.add(information);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void loadJson(String value) {
        updateList();
        try {
            list.clear();
            array = new JSONArray(value);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonobject = array.getJSONObject(i);
                String name = jsonobject.getString(USER_NAME);
                String inf = jsonobject.getString(USER_Information);
                int id = jsonobject.getInt(USER_Id);
                Informations information = new Informations(id, name, inf);
                list.add(information);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showNewinformationsDialog(Informations informations) {
        Dialog dialog = new ShowNewInformationsDialog(context, this, informations);


    }

    private void updateList() {
        listviewAdapter = new ListviewAdapter(getApplicationContext(), list);
        personView.setAdapter(listviewAdapter);
    }


    private void updateArray() {

        userDetails = getApplicationContext().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        array.put(writeJSON(nameText.getText().toString(), informations.getText().toString()));
        edit.putString(TAG, array.toString());
        edit.commit();
    }

}