package com.example.contact;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contact.model.Contact;
import com.example.contact.repository.ContactRepository;

public class EditActivity extends AppCompatActivity {
    private EditText nama, noHp;
    private Button simpan;
    private Contact contact;
    private ContactRepository contactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit Contact");

        this.nama = (EditText) findViewById(R.id.nama);
        this.noHp = (EditText) findViewById(R.id.noHp);

        this.contactRepository = new ContactRepository(getApplicationContext());
        this.getData(Integer.parseInt(getIntent().getStringExtra("id")));

        this.simpan = (Button) findViewById(R.id.simpan);
        this.simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama.getText().toString().isEmpty() || noHp.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Mohon isi semua field", Toast.LENGTH_SHORT).show();
                    return;
                }
                contact.setNama(nama.getText().toString());
                contact.setNoHp(noHp.getText().toString());
                contactRepository.update(contact);
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
                        contactRepository.delete(contact);
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


    private void getData(int id) {
        this.contact = this.contactRepository.getOne(id);
        this.nama.setText(this.contact.getNama());
        this.noHp.setText(this.contact.getNoHp());
    }

}