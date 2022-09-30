package com.example.dialog_listview.model;

public class Contact {
    private int id;
    private String nama, noHp;

    public Contact(int id, String nama, String noHp) {
        this.id = id;
        this.nama = nama;
        this.noHp = noHp;
    }

    public Contact(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}

