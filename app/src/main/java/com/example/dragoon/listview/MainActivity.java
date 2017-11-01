package com.example.dragoon.listview;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dragoon.listview.adapter.Informations;
import com.example.dragoon.listview.adapter.ListviewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView personView;
    private ListviewAdapter listviewAdapter;
    private List<Informations> list;

    JSONArray array=new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView;
        final EditText nameText;

        Button save,load;
        textView= (TextView)findViewById(R.id.listText);
        nameText=(EditText)findViewById(R.id.editText);
        save=(Button)findViewById(R.id.saveButton);
        load=(Button)findViewById(R.id.loadButton);
        personView= (ListView) findViewById(R.id.listview);
        list= new ArrayList<>();



        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("userdetails", MODE_PRIVATE);
        String name = userDetails.getString("name", "");
        loadJson(name);







        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences userDetails = getApplicationContext().getSharedPreferences("userdetails", MODE_PRIVATE);
                String name = userDetails.getString("name", "");
                loadJson(name);

            }
        });






        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userDetails = getApplicationContext().getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor edit = userDetails.edit();

                array.put(writeJSON( nameText.getText().toString()));
                edit.putString("name", array.toString());

                edit.commit();
                Toast.makeText(MainActivity.this, array.toString(), Toast.LENGTH_SHORT).show();


            }
        });



    }
    public JSONObject writeJSON(String name) {
        JSONObject object = new JSONObject();
        try {
            int id = array.length();
            object.put("id",id);
            object.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
     return object;
    }

    public void loadJson(String value) {

        try {

            array = new JSONArray(value);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonobject = array.getJSONObject(i);
                String name = jsonobject.getString("name");
                int id = jsonobject.getInt("id");
                Informations information = new Informations(id, name);
                list.add(information);
                listviewAdapter = new ListviewAdapter(getApplicationContext(), list);
                personView.setAdapter(listviewAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}