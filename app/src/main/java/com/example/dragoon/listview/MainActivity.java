package com.example.dragoon.listview;

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
    String AllIMustKnow="{";
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









        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  String str = "";
                int i;

                Type type = new TypeToken<ArrayList<Informations>>() { }.getType();
                ArrayList<Informations> restoreData = new Gson().fromJson(str, type);
                if(str!="") {
                    for (i = 0; i < restoreData.size(); i++)
                        textView.setText(str);
                }*/

              //  listviewAdapter=new ListView(getApplicationContext(),AllIMustKnow);
               // personView.setAdapter(listviewAdapter);




            }
        });






        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                array.put(writeJSON( nameText.getText().toString()));
                Toast.makeText(MainActivity.this, array.toString(), Toast.LENGTH_SHORT).show();





            }
        });



    }
    public JSONObject writeJSON(String name) {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
     return object;
    }

    public void loadJson(String value) {
        JSONArray jsonarray = null;
        try {
            jsonarray = new JSONArray(value);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String name = jsonobject.getString("name");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}