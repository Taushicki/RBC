package com.example.rbc.switching_styles;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.rbc.R;

public class WarningStyle {
    public static void enable(Context context, EditText field){
        field.setBackgroundResource(R.drawable.error_rounded_edit_text_bg);
        field.setHint(R.string.login_activity_hint_warning_text);
        field.startAnimation(AnimationUtils.loadAnimation(context, R.anim.border_blink));
    }

    public static void disable(EditText field, int hint_text){
        field.clearAnimation();
        field.setBackgroundResource(R.drawable.rounded_edittext_bg);
        field.setHint(hint_text);
    }
}
