package com.example.hitungnilai;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnHitung;
    private EditText nilaiEts, nilaiEas, tugas1, tugas2, perbaikanEts, perbaikanEas, perbaikanTugas1, perbaikanTugas2;
    private TextView hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnHitung = (Button) findViewById(R.id.btnHitung);
        this.nilaiEts = (EditText) findViewById(R.id.nilaiEts);
        this.nilaiEas = (EditText) findViewById(R.id.nilaiEas);
        this.tugas1 = (EditText) findViewById(R.id.tugas1);
        this.tugas2 = (EditText) findViewById(R.id.tugas2);
        this.perbaikanEts = (EditText) findViewById(R.id.perbaikanEts);
        this.perbaikanEas = (EditText) findViewById(R.id.perbaikanEas);
        this.perbaikanTugas1 = (EditText) findViewById(R.id.perbaikanTugas1);
        this.perbaikanTugas2 = (EditText) findViewById(R.id.perbaikanTugas2);
        this.hasil = (TextView) findViewById(R.id.hasil);

        this.nilaiEts.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
        this.nilaiEas.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
        this.tugas1.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
        this.tugas2.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
        this.perbaikanEts.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
        this.perbaikanEas.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
        this.perbaikanTugas1.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
        this.perbaikanTugas2.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, getApplicationContext())});
    }

    public void hitung(View v) {

        //perbaikan jika nilai < 75
        //ets
        double etsScore = 0, easScore = 0, tugas1Score = 0, tugas2Score = 0;
        try {
            etsScore = this.getValue(nilaiEts, "ETS");
            if (etsScore < 75) {
                etsScore = this.getValue(perbaikanEts, "Perbaikan ETS");
            }

            easScore = this.getValue(nilaiEas, "EAS");
            if (easScore < 75) {
                easScore = this.getValue(perbaikanEas, "Perbaikan EAS");
            }

            tugas1Score = this.getValue(tugas1, "Tugas 1");
            if (tugas1Score < 75) {
                tugas1Score = this.getValue(perbaikanTugas1, "Perbaikan Tugas 1");
            }

            tugas2Score = this.getValue(tugas2, "Tugas 2");
            if (tugas2Score < 75) {
                tugas2Score = this.getValue(perbaikanTugas2, "Perbaikan Tugas 2");
            }

            double avg = (etsScore + easScore + tugas1Score + tugas2Score) / 4;
            this.hasil.setText("Rata-rata: " + avg);
        } catch (Exception exception) {

        }


    }

    private Double getValue(EditText editText, String fieldName) throws Exception {
        try {
            return Double.parseDouble(editText.getText().toString());
        } catch (Exception exception) {
            Toast.makeText(getApplicationContext(), "Mohon cek input " + fieldName, Toast.LENGTH_SHORT).show();
            throw exception;
        }
    }


}