package com.zzh.uidemo.javatest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zzh.uidemo.R;

public class JavaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);

        Man man = new Man();
        Person person = man;
//        person.setName();
//        man.setName();
    }
}
