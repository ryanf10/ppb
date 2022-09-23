package com.example.contact;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {
    private EditText nama, noHp;
    private Button simpan;
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        openHelper = new SQLiteOpenHelper(getApplicationContext(), "db.sqlite", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        this.db = openHelper.getWritableDatabase();
        this.db.execSQL("CREATE TABLE IF NOT EXISTS contact(id INTEGER PRIMARY KEY AUTOINCREMENT,nama TEXT, no_hp TEXT)");

        this.nama = (EditText) findViewById(R.id.nama);
        this.noHp = (EditText) findViewById(R.id.noHp);
        this.simpan = (Button) findViewById(R.id.simpan);
        this.simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues data = new ContentValues();
                data.put("nama", nama.getText().toString());
                data.put("no_hp", noHp.getText().toString());
                db.insert("contact", null, data);
                Toast.makeText(getApplicationContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}