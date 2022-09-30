package com.example.dialog_listview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dialog_listview.model.Contact;
import com.example.dialog_listview.repository.ContactRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText search;
    private ContactRepository contactRepository;
    private ArrayList<Contact> listContact;

    @Override
    protected void onStart() {
        this.readData();
        super.onStart();
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

        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = (Contact) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("id", String.valueOf(contact.getId()));
                startActivity(intent);
            }
        });
        this.listContact = new ArrayList<>();
        this.contactRepository = new ContactRepository(getApplicationContext());
    }

    private void setAdapter() {
        ContactAdapter contactAdapter = new ContactAdapter(this, 0, listContact);
        listView.setAdapter(contactAdapter);
    }

    private void readData() {
        String keyword = this.search.getText().toString();
        listContact = this.contactRepository.getAll(keyword);
        this.setAdapter();
    }


    public void showInputDialog(View v) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View inputDialog = layoutInflater.inflate(R.layout.input_dialog, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(inputDialog)
                .setCancelable(false)
                .setPositiveButton("Simpan", null)
                .setNegativeButton("Cancel", null)
                .create();

        final EditText name = (EditText) inputDialog.findViewById(R.id.name);
        final EditText phone = (EditText) inputDialog.findViewById(R.id.phone);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                Button buttonPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (name.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Mohon lengkapi semua field", Toast.LENGTH_SHORT).show();
                        } else {
                            Contact contact = new Contact(name.getText().toString(), phone.getText().toString());
                            contactRepository.insert(contact);
                            readData();
                            dialog.dismiss();
                        }
                    }
                });


                Button buttonNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(getResources().getColor(R.color.red));
                buttonNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

            }
        });

        dialog.show();
    }
}