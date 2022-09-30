package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact.model.Contact;
import com.example.contact.repository.ContactRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText search;
    //    private LinearLayout data;
    private ContactRepository contactRepository;
    private RecyclerView recyclerView;

    private static ArrayList<Contact> contacts;

    public static ArrayList<Contact> getContacts() {
        return contacts;
    }

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

//        this.data = (LinearLayout) findViewById(R.id.data);
        this.contactRepository = new ContactRepository(getApplicationContext());
        this.recyclerView = findViewById(R.id.rview);
        this.setAdapter();

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

    private void setAdapter() {
        ContactListRecyclerAdapter contactListRecyclerAdapter = new ContactListRecyclerAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(contactListRecyclerAdapter);
    }

    private void readData() {
        String keyword = this.search.getText().toString();
        contacts = this.contactRepository.getAll(keyword);
        this.setAdapter();
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