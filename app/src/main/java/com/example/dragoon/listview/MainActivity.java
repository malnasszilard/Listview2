package com.example.dragoon.listview;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
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

    private SharedPreferences userDetails;
    private ListView personView;
    private ListviewAdapter listviewAdapter;
    private List<Informations> list;
    EditText nameText, informations;
    Button save;
    JSONArray array = new JSONArray();
    Context context = this;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameText = findViewById(R.id.editText2);
        informations = findViewById(R.id.editText);
        save = findViewById(R.id.saveButton);
        personView = findViewById(R.id.listview);
        list = new ArrayList<>();
        userDetails = getApplicationContext().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        String rawJSON = userDetails.getString(TAG, "");
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArray();
            }
        });
    }

    @Override
    public void onItemSaved(Informations informations) {
        try {
            for (int i = 0; i < array.length()-1; i++) {
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
            updateList();
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


    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        this.menu = menu;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    MainActivity.this.listviewAdapter.getFilter().filter(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    MainActivity.this.listviewAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return true;
    }
}