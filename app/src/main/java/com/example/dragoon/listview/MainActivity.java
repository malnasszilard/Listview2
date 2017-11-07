package com.example.dragoon.listview;

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


    private SharedPreferences userDetails;
    private ListView personView;
    private ListviewAdapter listviewAdapter;
    private List<Informations> list;
    EditText nameText, informations;
    Button save, load;
    JSONArray array = new JSONArray();
    Context context = this;

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

        userDetails = getApplicationContext().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        final String rawJSON = userDetails.getString("rawJSON", "");
        loadJson(rawJSON);
        updateList();

        personView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SharedPreferences.Editor editor = userDetails.edit();
                editor.putInt("position", position);
                editor.commit();
                showNewinformationsDialog(list.get(position));
            }
        });


        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                String name = userDetails.getString("rawJSON", "");
                loadJson(name);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArray();
            }
        });
    }



    private void updateList() {
        listviewAdapter = new ListviewAdapter(getApplicationContext(), list);
        personView.setAdapter(listviewAdapter);
    }

    public JSONObject writeJSON(String name, String info) {
        JSONObject object = new JSONObject();
        try {
            int id = array.length();
            object.put("id", id);
            object.put("name", name);
            object.put("informations", info);
            Informations information = new Informations(id, name, info);
            list.add(information);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void loadJson(String value) {
        try {
            list.clear();
            array = new JSONArray(value);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonobject = array.getJSONObject(i);
                String name = jsonobject.getString("name");
                String inf = jsonobject.getString("informations");
                int id = jsonobject.getInt("id");
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


    @Override
    public void onItemSaved(Informations informations) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (((JSONObject)array.get(i)).getInt("id") == informations.getId()){
                    array.put(i, informations.toJsonObject());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateArray();

    }

    private void updateArray() {
        SharedPreferences.Editor edit = userDetails.edit();
        array.put(writeJSON(nameText.getText().toString(), informations.getText().toString()));
        edit.putString("rawJSON", array.toString());
        edit.commit();
        updateList();
    }

}