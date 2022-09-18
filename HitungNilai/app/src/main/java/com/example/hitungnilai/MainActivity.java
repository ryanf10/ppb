package com.example.hitungnilai;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnHitung;
    private EditText nilaiEts;
    private EditText nilaiEas;
    private TextView hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnHitung = (Button) findViewById(R.id.btnHitung);
        this.nilaiEts = (EditText) findViewById(R.id.nilaiEts);
        this.nilaiEas = (EditText) findViewById(R.id.nilaiEas);
        this.hasil = (TextView) findViewById(R.id.hasil);

        this.nilaiEts.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
        this.nilaiEas.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
    }

    public void hitung(View v) {
        double avg = (Double.parseDouble(nilaiEts.getText().toString()) + Double.parseDouble(nilaiEas.getText().toString())) / 2;
        this.hasil.setText("Rata-rata: " + avg);
    }


}