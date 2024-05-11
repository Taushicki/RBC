package com.example.rbc.warning;

import android.view.View;

public class ShowWarningLabel {
    public static void enable(View label){
        label.setVisibility(View.VISIBLE);
    }

    public static void disable(View label){
        label.setVisibility(View.INVISIBLE);
    }


}
