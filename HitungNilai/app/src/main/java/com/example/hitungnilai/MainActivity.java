package com.example.hitungnilai;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Button btnHitung;
    private ArrayList<EditTextScore> editTextScores = new ArrayList<>();
    private TextView hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnHitung = (Button) findViewById(R.id.btnHitung);
        this.editTextScores.add(new EditTextScore("ETS", (EditText) findViewById(R.id.nilaiEts), (EditText) findViewById(R.id.perbaikanEts), getApplicationContext()));
        this.editTextScores.add(new EditTextScore("EAS", (EditText) findViewById(R.id.nilaiEas), (EditText) findViewById(R.id.perbaikanEas), getApplicationContext()));
        this.editTextScores.add(new EditTextScore("Tugas 1", (EditText) findViewById(R.id.tugas1), (EditText) findViewById(R.id.perbaikanTugas1), getApplicationContext()));
        this.editTextScores.add(new EditTextScore("Tugas 2", (EditText) findViewById(R.id.tugas2), (EditText) findViewById(R.id.perbaikanTugas2), getApplicationContext()));
        this.hasil = (TextView) findViewById(R.id.hasil);
    }

    public void hitung(View v) {
        //perbaikan jika nilai < 75
        //ets
        double etsScore = 0, easScore = 0, tugas1Score = 0, tugas2Score = 0;
        try {
            double total = 0;
            for (int i = 0; i < editTextScores.size(); i++) {
                System.out.println(editTextScores.get(i).evaluateScore());
                total += editTextScores.get(i).evaluateScore();
            }

            double avg = total / editTextScores.size();
            this.hasil.setText("Rata-rata: " + avg);
        } catch (Exception exception) {
            this.hasil.setText("");
        }


    }
}