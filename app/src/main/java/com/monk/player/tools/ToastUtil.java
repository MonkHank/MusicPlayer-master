package com.monk.player.tools;

import android.widget.Toast;

import com.monk.player.application.MyApplication;


/**
 * Created by andy on 2017/7/18.
 */

public class ToastUtil {
    public static void Toast(String msg)
    {
        Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_SHORT).show();
    }


}
