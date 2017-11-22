package com.example.dragoon.listview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.DialogTitle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.dragoon.listview.adapter.Informations;

import org.json.JSONException;


public class ShowNewInformationsDialog extends Dialog {
    public interface Presenter {
        void onDialogItemSaved(Informations informations) throws JSONException;

    }

    public ShowNewInformationsDialog(@NonNull final Context context, final Presenter presenter, final Informations informations) {
        super(context);
        setContentView(R.layout.dialog_changeinfo);
        Button saveItem =findViewById(R.id.saveButton1);
        final EditText name = findViewById(R.id.editText3);
        final EditText information = findViewById(R.id.editText4);
        name.setText(informations.getName());
        information.setText(informations.getInformation());
        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                informations.setName(name.getText().toString());
                informations.setInformation(information.getText().toString());
                try {
                    presenter.onDialogItemSaved(informations);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismiss();
            }
        });


    }
}