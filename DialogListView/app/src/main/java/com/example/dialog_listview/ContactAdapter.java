package com.example.dialog_listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dialog_listview.model.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private static class ViewHolder {
        TextView tName, tPhone;
        LinearLayout linearLayout;
    }

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View ConvertView, ViewGroup parent) {
        Contact contact = getItem(position);
        ViewHolder viewHolder;

        if (ConvertView == null) {
            viewHolder = new ViewHolder();
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);

            viewHolder.tName = (TextView) ConvertView.findViewById(R.id.tName);
            viewHolder.tPhone = (TextView) ConvertView.findViewById(R.id.tPhone);
            viewHolder.linearLayout = (LinearLayout) ConvertView.findViewById(R.id.linearlayout);

            ConvertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) ConvertView.getTag();
        }

        viewHolder.tName.setText(contact.getNama());
        viewHolder.tPhone.setText(contact.getNoHp());

        return ConvertView;
    }
}
