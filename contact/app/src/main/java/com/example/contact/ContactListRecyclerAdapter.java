package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact.model.Contact;

public class ContactListRecyclerAdapter extends RecyclerView.Adapter<ContactListRecyclerAdapter.ContactListViewHolder> {
    public class ContactListViewHolder extends RecyclerView.ViewHolder {
        private TextView name, phone;
        private LinearLayout rlayout;
        private Context context;

        public ContactListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.phone = itemView.findViewById(R.id.phone);
            this.rlayout = itemView.findViewById(R.id.rlayout);
            this.context = itemView.getContext();
        }

        public Context getContext() {
            return context;
        }
    }

    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list, parent, false);
        return new ContactListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListViewHolder holder, int position) {
        Contact contact = MainActivity.getContacts().get(position);
        holder.name.setText(contact.getNama());
        holder.phone.setText(contact.getNoHp());

        holder.rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.getContext(), EditActivity.class);
                intent.putExtra("id", String.valueOf(contact.getId()));
                holder.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.getContacts().size();
    }
}
