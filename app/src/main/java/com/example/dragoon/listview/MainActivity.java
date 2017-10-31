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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView personView;
    private ListviewAdapter listviewAdapter;
    private List<Informations> list;
    String AllIMustKnow="{";
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
                int i=1 ;
                if(i==1) {
                    list.add(new Informations(i, nameText.getText().toString()));
                }
                    if (AllIMustKnow.charAt(AllIMustKnow.length() - 1) == '}') {
                        AllIMustKnow = AllIMustKnow.replace(AllIMustKnow.substring(AllIMustKnow.length() - 1), "");
                        i++;
                    if (AllIMustKnow.charAt(AllIMustKnow.length() - 1) == '{') {
                        AllIMustKnow += i + nameText.getText().toString() + "}";

                    } else {
                        AllIMustKnow += i + "," + nameText.getText().toString() + "}";
                        textView.setText(AllIMustKnow);
                    }

                    Toast.makeText(getApplicationContext(), "Login details are saved..", Toast.LENGTH_SHORT).show();

                    listviewAdapter = new ListviewAdapter(getApplicationContext(), list);
                    personView.setAdapter(listviewAdapter);
                    i++;



                }

            }
        });



    }
}

              /*  SharedPreferences memory = getSharedPreferences("memory", 0);

                SharedPreferences.Editor edit = memory.edit();


                edit.commit();*/  /*ArrayList<Informations> yourData = new ArrayList<Informations>();
                String dataStr = new Gson().toJson(AllIMustKnow);
                */