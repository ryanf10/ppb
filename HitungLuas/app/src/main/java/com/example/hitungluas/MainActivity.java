package com.example.hitungluas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editPanjang, editLebar;
    private Button btnHitung;
    private TextView txtLuas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPanjang = (EditText) findViewById(R.id.edit_panjang);
        editLebar = (EditText) findViewById(R.id.edit_lebar);
        btnHitung = (Button) findViewById(R.id.btn_hitung);
        txtLuas = (TextView) findViewById(R.id.txt_luas);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String panjang = editPanjang.getText().toString().trim();
                String lebar = editLebar.getText().toString().trim();
                double luas = 0;
                try {
                    double p = Double.parseDouble(panjang);
                    double l = Double.parseDouble(lebar);
                    luas = p * l;
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }

                txtLuas.setText("Luas : " + luas);
            }
        });
    }
}