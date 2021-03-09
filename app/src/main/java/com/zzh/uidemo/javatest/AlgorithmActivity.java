package com.zzh.uidemo.javatest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.zzh.uidemo.R;

import java.util.Arrays;

public class AlgorithmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        int[] arr = {3, 1, 2, 5 , 0, 9, 6, 2, 1};
        bubble1(arr);
        Log.i("zzz1", "" + Arrays.toString(arr));

//        recycle();
//        recycle1();

        int x = 5;
        Log.i("zzz1", "main 改变前： " + x);
        change(x);
        Log.i("zzz1", "main 改变后： " + x);
    }


    private void bubbleSort(int[] arr){
        int temp = 0;
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++){

            for (int j = 0; j < arr.length - i -1; j++){
                if (arr[j] > arr[j + 1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            if (!flag){
                break;
            } else {
                flag = false;
            }
        }

    }

    private void bubble1(int[] arr){
        int temp = 0;
        boolean flag = false;
        for (int i = 0; i < arr.length -1; i++){

            for (int j = 0; j < arr.length -1 -i; j++){
                if (arr[j] > arr[j + 1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            if (!flag){
                break;
            }else {
                flag = false;
            }
        }
    }

    private void recycle(){
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 20; j++){
                if (i == 2 ){
                    break; // 默认跳出最内侧循环
                }
                Log.i("zzz1", "" + i);
            }
        }
    }

    private void recycle1(){
        for1:
        for (int i = 0; i < 20; i++){
            for2:
            for (int j = 0; j < 20; j++){
                if (i == 2 ){
                    break for1;
                }
                Log.i("zzz1", "" + i);
            }
        }
    }

    private void change(int x){
        Log.i("zzz1", "改变前： " + x);
        x = 3;
        Log.i("zzz1", "改变后： " + x);
    }
}
