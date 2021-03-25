package com.zzh.uidemo.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zzh.uidemo.R;

public class AndroidTestActivity extends AppCompatActivity {

    Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_test);

        bt1 = findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidTestActivity.this, Activity1.class);
                startActivity(intent);
            }
        });
        Log.i("zzz", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("zzz", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("zzz", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("zzz", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("zzz", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("zzz", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("zzz", "onDestroy");
    }
}
