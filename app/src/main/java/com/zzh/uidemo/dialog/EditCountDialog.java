package com.zzh.uidemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.zzh.uidemo.R;

public class EditCountDialog extends Dialog {

    private Button tvCancel;
    private Button tvSure;
    private EditText etContent;
    private Context context;
    private String content;
    private onSureOnclickListener sureOnclickListener;

    public EditCountDialog(@NonNull Context context) {
        super(context, R.style.editCountDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_count_dialog);
        //初始化界面控件
        initView();
        setView(content);
    }


    private void initView() {
        etContent = findViewById(R.id.et_content);
        tvCancel = findViewById(R.id.tv_cancel);
        tvSure = findViewById(R.id.tv_sure);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureOnclickListener.onCancelDialog();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureOnclickListener.onSureClick();
            }
        });
    }

    public void setView(String content) {
        if (!TextUtils.isEmpty(content)) {
            etContent.setText(content);
        }
    }

    public void setSureOnclickListener(onSureOnclickListener sureOnclickListener) {
        this.sureOnclickListener = sureOnclickListener;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent(){
        return etContent.getText().toString().trim();
    }

    public interface onSureOnclickListener {
        void onSureClick();

        void onCancelDialog();
    }

    @Override
    public void show() {
        super.show();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = wm.getDefaultDisplay().getWidth() - wm.getDefaultDisplay().getWidth() / 4; //设置宽度
        getWindow().setAttributes(lp);
    }
}
