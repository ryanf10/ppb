package com.example.hitungnilai;

import android.content.Context;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextScore {
    private String fieldName;
    private EditText editTextNilai;
    private EditText editTextPerbaikan;
    private Context context;

    public EditTextScore(String fieldName, EditText editTextNilai, EditText editTextPerbaikan, Context context) {
        this.fieldName = fieldName;
        this.editTextNilai = editTextNilai;
        this.editTextPerbaikan = editTextPerbaikan;
        this.context = context;
        this.editTextNilai.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, this.context)});
        this.editTextPerbaikan.setFilters(new InputFilter[]{new InputMinMaxFilter(0, 100, this.context)});
    }

    public Double evaluateScore() throws Exception {
        try {
            double nilai = this.getValue(editTextNilai, this.fieldName);
            if (nilai < 75) {
                nilai = Math.max(nilai, Math.min(this.getValue(editTextPerbaikan, "Perbaikan " + this.fieldName), 75.0));
            }
            return nilai;
        } catch (Exception e) {
            throw e;
        }
    }

    private Double getValue(EditText editText, String fieldName) throws Exception {
        try {
            return Double.parseDouble(editText.getText().toString());
        } catch (Exception exception) {
            Toast.makeText(this.context, "Mohon cek input " + fieldName, Toast.LENGTH_SHORT).show();
            throw exception;
        }
    }
}