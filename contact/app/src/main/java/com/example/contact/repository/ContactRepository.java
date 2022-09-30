package com.example.contact.repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import com.example.contact.database.Database;
import com.example.contact.model.Contact;

import java.util.ArrayList;

public class ContactRepository {
    private Database database;

    public ContactRepository(Context context) {
        this.database = Database.getInstance(context);
    }

    public ArrayList<Contact> getAll(String keyword) {
        Cursor cursor;
        ArrayList<Contact> contacts = new ArrayList<>();

        if (!keyword.isEmpty()) {
            cursor = database.getDb().rawQuery("SELECT id, nama, no_hp FROM contact WHERE nama LIKE '%" + keyword + "%' OR no_hp LIKE '%" + keyword + "%'", null);
        } else {
            cursor = database.getDb().rawQuery("select id, nama, no_hp from contact;", null);
        }
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                System.out.println(cursor.toString());
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                contacts.add(new Contact(id, name, phone));
            }
        }
        return contacts;
    }

    public Contact getOne(int id) throws Resources.NotFoundException {
        Cursor cursor = database.getDb().rawQuery("select id, nama, no_hp from contact WHERE id = " + id + ";", null);

        if (cursor.getCount() == 0) {
            throw new Resources.NotFoundException();
        }
        cursor.moveToFirst();
        return new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
    }


    public void insert(Contact contact) {
        ContentValues data = new ContentValues();
        data.put("nama", contact.getNama());
        data.put("no_hp", contact.getNoHp());
        database.getDb().insert("contact", null, data);
    }

    public void update(Contact contact) {
        ContentValues data = new ContentValues();
        data.put("nama", contact.getNama());
        data.put("no_hp", contact.getNoHp());
        database.getDb().update("contact", data, "id = " + contact.getId(), null);
    }

    public void delete(Contact contact) {
        database.getDb().delete("contact", "id = " + contact.getId(), null);
    }


    @Override
    protected void finalize() throws Throwable {
        try {
            database.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            super.finalize();
        }
    }
}
