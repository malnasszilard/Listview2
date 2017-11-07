package com.example.dragoon.listview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.dragoon.listview.adapter.Informations;

import org.json.JSONException;


public class ShowNewInformationsDialog extends Dialog {
    public interface Presenter {
        void onItemSaved(Informations informations) throws JSONException;


    }

    public ShowNewInformationsDialog(@NonNull final Context context, final Presenter presenter, final Informations informations) {
        super(context);
        setContentView(R.layout.dialog_changeinfo);
        show();
        Button saveItem =findViewById(R.id.saveButton);


        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.editText3);
                EditText information = findViewById(R.id.editText4);
                //new NewItem(name.getText().toString(),information.getText().toString(),context);

                informations.setName(name.getText().toString());
                informations.setInformation(information.getText().toString());
                try {
                    presenter.onItemSaved(informations);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismiss();
            }
        });


    }

}

