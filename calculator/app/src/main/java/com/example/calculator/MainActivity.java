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
    private ArrayList<Button> btnNumbers = new ArrayList<>();
    private Button btnDot;
    private Button btnOpenParentheses;
    private Button btnCloseParentheses;
    private Button btnErase;
    private Button btnAddition;
    private Button btnSubtraction;
    private Button btnDivision;
    private Button btnMultiplication;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.txtResult = (TextView) findViewById(R.id.txtResult);

        for (int i = 0; i <= 9; i++) {
            String btnId = "btn" + i;
            Button btn = ((Button) findViewById(getResources().getIdentifier(btnId, "id", getPackageName())));
            int num = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String now = txtResult.getText().toString() + num;
                    txtResult.setText(now);
                }
            });
        }

        this.btnDot = (Button) findViewById(R.id.btnDot);
        this.btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now = txtResult.getText().toString() + ".";
                txtResult.setText(now);
            }
        });

        this.btnAddition = (Button) findViewById(R.id.btnAddition);
        this.btnAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now = txtResult.getText().toString() + "+";
                txtResult.setText(now);
            }
        });

        this.btnSubtraction = (Button) findViewById(R.id.btnSubtraction);
        this.btnSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now = txtResult.getText().toString() + "-";
                txtResult.setText(now);
            }
        });

        this.btnDivision = (Button) findViewById(R.id.btnDivision);
        this.btnDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now = txtResult.getText().toString() + "/";
                txtResult.setText(now);
            }
        });

        this.btnMultiplication = (Button) findViewById(R.id.btnMultiplication);
        this.btnMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now = txtResult.getText().toString() + "*";
                txtResult.setText(now);
            }
        });

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

        this.btnErase = (Button)

                findViewById(R.id.btnErase);
        this.btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResult.setText("");
            }
        });

        this.btnOpenParentheses = (Button) findViewById(R.id.btnOpenParentheses);
        this.btnOpenParentheses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now = txtResult.getText().toString() + "(";
                txtResult.setText(now);
            }
        });
        this.btnCloseParentheses = (Button) findViewById(R.id.btnCloseParentheses);
        this.btnCloseParentheses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now = txtResult.getText().toString() + ")";
                txtResult.setText(now);
            }
        });
    }
}