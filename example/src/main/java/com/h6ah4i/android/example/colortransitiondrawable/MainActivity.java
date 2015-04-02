package com.h6ah4i.android.example.colortransitiondrawable;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.h6ah4i.android.colortransitiondrawable.ColorTransitionDrawable;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    ColorTransitionDrawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawable = new ColorTransitionDrawable();
        mDrawable.setDuration(1000);
        mDrawable.setInterpolator(new AccelerateDecelerateInterpolator());

        setContentView(R.layout.activity_main);


        setBackground(findViewById(R.id.container_view), mDrawable);

        findViewById(R.id.button_transparent).setOnClickListener(this);
        findViewById(R.id.button_white).setOnClickListener(this);
        findViewById(R.id.button_black).setOnClickListener(this);
        findViewById(R.id.button_red).setOnClickListener(this);
        findViewById(R.id.button_green).setOnClickListener(this);
        findViewById(R.id.button_blue).setOnClickListener(this);
        findViewById(R.id.button_cyan).setOnClickListener(this);
        findViewById(R.id.button_magenta).setOnClickListener(this);
        findViewById(R.id.button_yellow).setOnClickListener(this);
    }

    private void setBackground(View v, ColorTransitionDrawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }

    @Override
    public void onClick(View v) {
        int color = 0;

        switch (v.getId()) {
            case R.id.button_transparent:
                color = getResources().getColor(R.color.bg_transparent);
                break;
            case R.id.button_white:
                color = getResources().getColor(R.color.bg_white);
                break;
            case R.id.button_black:
                color = getResources().getColor(R.color.bg_black);
                break;
            case R.id.button_red:
                color = getResources().getColor(R.color.bg_red);
                break;
            case R.id.button_green:
                color = getResources().getColor(R.color.bg_green);
                break;
            case R.id.button_blue:
                color = getResources().getColor(R.color.bg_blue);
                break;
            case R.id.button_cyan:
                color = getResources().getColor(R.color.bg_cyan);
                break;
            case R.id.button_magenta:
                color = getResources().getColor(R.color.bg_magenta);
                break;
            case R.id.button_yellow:
                color = getResources().getColor(R.color.bg_yellow);
                break;
        }

        mDrawable.setColor(color);
    }
}
