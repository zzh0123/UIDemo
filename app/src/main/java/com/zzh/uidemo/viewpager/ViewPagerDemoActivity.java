package com.zzh.uidemo.viewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zzh.uidemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: zzhh
 * Date: 2020/12/27 21:51
 * Description: viewpager示例
 */
public class ViewPagerDemoActivity extends AppCompatActivity {

    @BindView(R.id.rl_viewpager_show_view1)
    RelativeLayout rl_viewpager_show_view1;

    @BindView(R.id.rl_viewpager_show_view)
    RelativeLayout rl_viewpager_show_view;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_demo);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_viewpager_show_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rl_viewpager_show_view1:
                intent = new Intent(ViewPagerDemoActivity.this, ViewPagerShowViewActivity1.class);
                startActivity(intent);
                break;
            case R.id.rl_viewpager_show_view:
                intent = new Intent(ViewPagerDemoActivity.this, ViewPagerShowViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
