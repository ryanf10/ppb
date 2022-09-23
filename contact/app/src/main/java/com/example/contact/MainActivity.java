package com.example.contact;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private EditText nama, noHp;
    private Button simpan;
    private LinearLayout data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nama = (EditText) findViewById(R.id.nama);
        this.noHp = (EditText) findViewById(R.id.noHp);
        this.data = (LinearLayout) findViewById(R.id.data);

        this.simpan = (Button) findViewById(R.id.simpan);
        this.simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues data = new ContentValues();
                data.put("nama", nama.getText().toString());
                data.put("no_hp", noHp.getText().toString());
                db.insert("contact", null, data);
                Toast.makeText(getApplicationContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();
                readData();
            }
        });

    }

    @Override
    protected void onStart() {
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
        this.readData();

        super.onStart();
    }


    @Override
    protected void onStop() {
        this.db.close();
        this.openHelper.close();
        super.onStop();
    }

    private void readData() {
        Cursor cursor = this.db.rawQuery("select id, nama, no_hp from contact;", null);
        data.removeAllViews();
        if (cursor.getCount() > 0) {
            data.addView(this.addDivider());
            while (cursor.moveToNext()) {
                System.out.println(cursor.toString());
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                LinearLayout kontak = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                kontak.setOrientation(LinearLayout.VERTICAL);
                kontak.setPadding(50, 50, 50, 50);


                TextView textViewNama = new TextView(this);
                textViewNama.setText(name);
                textViewNama.setTextColor(getResources().getColor(R.color.purple_700));


                TextView textViewNoHp = new TextView(this);
                textViewNoHp.setText(phone);
                textViewNoHp.setTextColor(getResources().getColor(R.color.purple_200));

                kontak.addView(textViewNama);
                kontak.addView(textViewNoHp);

                kontak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getBaseContext(), EditActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });

                data.addView(kontak);
                data.addView(this.addDivider());
            }
        }
    }

    private LinearLayout addDivider() {
        LinearLayout divider = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        divider.setLayoutParams(params);
        divider.setMinimumHeight(5);
        divider.setBackgroundColor(getResources().getColor(R.color.purple_200));
        return divider;
    }
}