package com.example.contact;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private EditText search;
    private LinearLayout data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.search = (EditText) findViewById(R.id.search);
        this.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                readData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        this.data = (LinearLayout) findViewById(R.id.data);


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

    public void create(View v) {
        Intent intent = new Intent(getBaseContext(), CreateActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStop() {
        this.db.close();
        this.openHelper.close();
        super.onStop();
    }

    private void readData() {
        Cursor cursor;
        String keyword = this.search.getText().toString();
        if (!keyword.isEmpty()) {
            cursor = this.db.rawQuery("SELECT id, nama, no_hp FROM contact WHERE nama LIKE '%" + keyword + "%' OR no_hp LIKE '%" + keyword + "%'", null);
        } else {
            cursor = this.db.rawQuery("select id, nama, no_hp from contact;", null);
        }
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
                textViewNama.setTypeface(null, Typeface.BOLD);

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
        divider.setMinimumHeight(2);
        divider.setBackgroundColor(getResources().getColor(R.color.black));
        return divider;
    }
}