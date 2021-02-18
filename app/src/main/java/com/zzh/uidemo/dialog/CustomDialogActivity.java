package com.zzh.uidemo.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzh.uidemo.R;

public class CustomDialogActivity extends AppCompatActivity implements View.OnClickListener{

    private Button tv_show_dialog;
    // 评论输入框
    private CommentDialog commentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        tv_show_dialog = findViewById(R.id.bt_show_dialog);
        tv_show_dialog.setOnClickListener(this);

        // 评论输入框
        commentDialog = new CommentDialog(this);
        commentDialog.setOnCommitListener(new CommentDialog.OnCommitListener() {
            @Override
            public void onCommit(EditText et, View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_show_dialog){
            if (commentDialog == null){
                commentDialog = new CommentDialog(CustomDialogActivity.this);
            } else {
                commentDialog.show();
            }
        }
    }
}
