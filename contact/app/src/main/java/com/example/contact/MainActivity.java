package com.example.contact;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contact.model.Contact;
import com.example.contact.repository.ContactRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText search;
    private LinearLayout data;
    private ContactRepository contactRepository;

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
        this.contactRepository = new ContactRepository(getApplicationContext());

    }

    @Override
    protected void onStart() {
        this.readData();
        super.onStart();
    }

    public void create(View v) {
        Intent intent = new Intent(getBaseContext(), CreateActivity.class);
        startActivity(intent);
    }

    private void readData() {
        String keyword = this.search.getText().toString();
        ArrayList<Contact> contacts = this.contactRepository.getAll(keyword);
        data.removeAllViews();
        for (Contact contact : contacts) {
            int id = contact.getId();
            String name = contact.getNama();
            String phone = contact.getNoHp();
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
                    intent.putExtra("id", String.valueOf(id));
                    startActivity(intent);
                }
            });

            data.addView(kontak);
            data.addView(this.addDivider());
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