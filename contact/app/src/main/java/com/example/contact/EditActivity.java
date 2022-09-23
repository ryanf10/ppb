package com.example.contact;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private EditText nama, noHp;
    private Button simpan;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.id = getIntent().getStringExtra("id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setTitle("Edit Contact");

        this.nama = (EditText) findViewById(R.id.nama);
        this.noHp = (EditText) findViewById(R.id.noHp);

        openHelper = new SQLiteOpenHelper(getApplicationContext(), "db.sqlite", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        this.db = openHelper.getWritableDatabase();
        this.getData();

        this.simpan = (Button) findViewById(R.id.simpan);
        this.simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues data = new ContentValues();
                data.put("nama", nama.getText().toString());
                data.put("no_hp", noHp.getText().toString());
                db.update("contact", data, "id = " + id, null);
                Toast.makeText(getApplicationContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void showDeleteDialog(View v) {
        deleteDialog().show();
    }

    private AlertDialog deleteDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete this item?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.delete("contact", "id=" + id, null);
                        Toast.makeText(getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
            }
        });

        return alertDialog;
    }


    private void getData() {
        Cursor cursor = this.db.rawQuery("select id, nama, no_hp from contact WHERE id = " + this.id + ";", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            this.nama.setText(cursor.getString(1));
            this.noHp.setText(cursor.getString(2));
        }
    }

}