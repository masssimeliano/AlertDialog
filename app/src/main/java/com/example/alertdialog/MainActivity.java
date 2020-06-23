package com.example.alertdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mETData;
    private Button mBExit;

    private int ID_ALERT_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mETData = (EditText) findViewById(R.id.mETData);
        mBExit = (Button) findViewById(R.id.mBExit);

        mETData.setText(getData());

        mBExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ID_ALERT_DIALOG);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == ID_ALERT_DIALOG) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle("Exit");
            alertDialogBuilder.setMessage("Save changes in data?");
            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_info);

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mETData.getText().toString().equals("") == false) {
                        saveData();

                        finish();
                    }
                    else
                        Toast.makeText(MainActivity.this, "Field for data isn't filled", Toast.LENGTH_LONG).show();
                }
            });
            alertDialogBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alertDialogBuilder.create();

            return alertDialogBuilder.create();
        }
        return super.onCreateDialog(id);
    }

    public void onBackPressed() {
        showDialog(ID_ALERT_DIALOG);
    }

    private String getData() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        return sharedPreferences.getString("Data", "");
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Data", mETData.getText().toString());
        editor.commit();

        Toast.makeText(MainActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
    }
}