package com.zzh.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zzh.uidemo.viewpager.ViewPagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_viewPager)
    TextView tv_viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_viewPager})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.tv_viewPager){
            Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
            startActivity(intent);
        }
    }
}
