package com.zzh.uidemo.channel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.zzh.uidemo.R;

import com.zzh.uidemo.utils.PackageUtil;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class ChannelActivity extends AppCompatActivity implements  EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    /**
     * 渠道名称
     */
    private String channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        channel = PackageUtil.getAppMetaData(this, "channel");
        Log.i("zzz1", "channel " + channel);

    }

    // 动态权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // 允许使用权限
        if (requestCode == 1) {
            Log.i("zzz1", "onPermissionsGranted: ");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // 拒绝使用权限
        if (requestCode == 1) {
            Log.i("zzz1", "onPermissionsDenied: ");
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        // 再次询问权限，点击确认回调
        if (requestCode == 1) {
            Log.i("zzz1", "onRationaleAccepted: ");
        }
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        // 再次询问权限，点击取消回调
        if (requestCode == 1) {
//            showCustomToast(getString(R.string.need_storage));
            Log.i("zzz1", "onRationaleDenied: ");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
