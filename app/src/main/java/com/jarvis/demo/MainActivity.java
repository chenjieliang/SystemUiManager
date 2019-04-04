package com.jarvis.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fullscreen(View view) {
        Intent intent = new Intent(this,FullScreenAvtivity.class);
        startActivity(intent);
    }

    public void statusbar(View view) {
        Intent intent = new Intent(this,StatusBarAvtivity.class);
        startActivity(intent);
    }

    public void navigation(View view) {
        Intent intent = new Intent(this,NavigationAvtivity.class);
        startActivity(intent);
    }
}
