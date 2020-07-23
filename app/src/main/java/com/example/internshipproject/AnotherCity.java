package com.example.internshipproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class AnotherCity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_city_layout);
    }


    public void BackToPrevious(View view)
    {
        finish();
    }
}
