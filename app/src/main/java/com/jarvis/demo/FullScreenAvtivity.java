package com.jarvis.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.jarvis.systemui.SystemUiManager;

/**
 * @author chenjieliang
 */
public class FullScreenAvtivity extends AppCompatActivity {

    SystemUiManager mSystemUiManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        mSystemUiManager = SystemUiManager.getInstance(this);
        mSystemUiManager.setFlags(SystemUiManager.FLAG_FULLSCREEN);
        mSystemUiManager.setNavigationTranslucent();
        mSystemUiManager.setStatusBarTranslucent();
        mSystemUiManager.setOnVisibilityChangeListener(new SystemUiManager.OnVisibilityChangeListener() {
            @Override
            public void onVisibilityChange(boolean visible) {
                Log.i("SystemUiManager","onVisibilityChange : " + visible);
            }
        });
    }

    public void show(View view) {
        mSystemUiManager.show();
    }

    public void hide(View view) {
        mSystemUiManager.hide();
    }
}
