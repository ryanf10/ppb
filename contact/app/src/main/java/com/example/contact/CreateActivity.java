package com.example.contact;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contact.model.Contact;
import com.example.contact.repository.ContactRepository;

public class CreateActivity extends AppCompatActivity {
    private EditText nama, noHp;
    private Button simpan;
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private ContactRepository contactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        this.contactRepository = new ContactRepository(getApplicationContext());

        this.nama = (EditText) findViewById(R.id.nama);
        this.noHp = (EditText) findViewById(R.id.noHp);
        this.simpan = (Button) findViewById(R.id.simpan);
        this.simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama.getText().toString().isEmpty() || noHp.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Mohon isi semua field", Toast.LENGTH_SHORT).show();
                    return;
                }
                Contact contact = new Contact(nama.getText().toString(), noHp.getText().toString());
                contactRepository.insert(contact);
                Toast.makeText(getApplicationContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}