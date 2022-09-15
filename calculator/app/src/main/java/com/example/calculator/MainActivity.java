package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnBackspace;
    private TextView txtResult;
    private ArrayList<Button> btnNumpads = new ArrayList<>();
    private Button btnErase;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.txtResult = (TextView) findViewById(R.id.txtResult);

        for (int i = 0; i <= 9; i++){
            String btnId = "btn" + i;
            Button btn = ((Button) findViewById(getResources().getIdentifier(btnId, "id", getPackageName())));
            btnNumpads.add(btn);
        }

        btnNumpads.add((Button) findViewById(R.id.btnDot));
        btnNumpads.add((Button) findViewById(R.id.btnOpenParentheses));
        btnNumpads.add((Button) findViewById(R.id.btnCloseParentheses));
        btnNumpads.add((Button) findViewById(R.id.btnAddition));
        btnNumpads.add((Button) findViewById(R.id.btnSubtraction));
        btnNumpads.add((Button) findViewById(R.id.btnDivision));
        btnNumpads.add((Button) findViewById(R.id.btnMultiplication));

        for (int i = 0; i < btnNumpads.size(); i++){
            Button temp = btnNumpads.get(i);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String now = txtResult.getText().toString() + temp.getText().toString();
                    txtResult.setText(now);
                }
            });
        }

        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expression e = new org.mariuszgromada.math.mxparser.Expression();
                e.setExpressionString((String) txtResult.getText());
                mXparser.consolePrintln((String) txtResult.getText());
                DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
                df.setMinimumFractionDigits(0);
                df.setMaximumFractionDigits(10);
                df.setGroupingUsed(false);
                txtResult.setText(df.format(e.calculate()));
            }
        });

        this.btnBackspace = (ImageButton) findViewById(R.id.btnBackspace);
        this.btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder str = new StringBuilder((String) txtResult.getText());
                if (str.length() > 0) {
                    if (((String) txtResult.getText()).contains("NaN")) {
                        txtResult.setText("");
                    } else {
                        txtResult.setText(str.deleteCharAt(str.length() - 1).toString());
                    }
                }
            }
        });

        this.btnErase = (Button) findViewById(R.id.btnErase);
        this.btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText("");
            }
        });

    }
}