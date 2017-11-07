package com.example.dragoon.listview;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;


import static android.content.Context.MODE_PRIVATE;


public class NewItem {

    public NewItem(String name, String information, @NonNull final Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("newName", name);
        editor.putString("newInf", information);
        editor.commit();

    }
}
