package com.zzh.uidemo.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.zzh.uidemo.R;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        Log.i("zzz", "Activity1 onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("zzz", "Activity1 onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("zzz", "Activity1 onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("zzz", "Activity1 onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("zzz", "Activity1 onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("zzz", "Activity1 onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("zzz", "Activity1 onDestroy");
    }
}
