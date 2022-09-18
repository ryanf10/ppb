package com.example.hitungnilai;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

public class InputMinMaxFilter implements InputFilter {
    private double min, max;
    private Context context;

    public InputMinMaxFilter(double min, double max, Context context) {
        this.min = min;
        this.max = max;
        this.context = context;
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        System.out.println(charSequence.toString() + " " + spanned.toString());
        try {
            Double eval = Double.parseDouble(spanned.toString() + charSequence.toString());
            if (eval < min || eval > max) {
                Toast.makeText(context, "Range nilai 0-100", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Toast.makeText(context, "Range nilai 0-100", Toast.LENGTH_SHORT).show();
            return "";
        }
    }
}
