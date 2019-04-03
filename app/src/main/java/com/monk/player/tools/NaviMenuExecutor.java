package com.monk.player.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.example.andy.player.R;
import com.monk.player.activity.AboutActivity;
import com.monk.player.activity.MusicAcitivity;
import com.monk.player.activity.SettingActivity;
import com.monk.player.aidl.IMusicPlayer;
import com.monk.player.aidl.SongBean;
import com.monk.player.application.MyApplication;
import com.monk.player.mvp.registeandlogin.LoginActivity;
import com.monk.player.service.MusicService;
import com.monk.player.service.QuitTimer;


/**
 * 导航菜单执行器
 * Created by hzwangchenyan on 2016/1/14.
 */
public class NaviMenuExecutor {

    public static boolean onNavigationItemSelected(MenuItem item, MusicAcitivity activity) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                startActivity(activity, SettingActivity.class);
                return true;
            case R.id.action_night:
                nightMode(activity);
                break;
            case R.id.action_timer:
                timerDialog(activity);
                return true;
            case R.id.action_exit:
                exit(activity);
                return true;
            case R.id.action_about:
                startActivity(activity, AboutActivity.class);
                return true;
            case R.id.action_login:
                startActivity(activity, LoginActivity.class);
                return true;

        }
        return false;
    }

    private static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    private static void nightMode(final MusicAcitivity activity) {
        Preferences.saveNightMode(!Preferences.isNightMode());
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }

    private static void timerDialog(final MusicAcitivity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(R.string.menu_timer)
                .setItems(activity.getResources().getStringArray(R.array.timer_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int[] times = activity.getResources().getIntArray(R.array.timer_int);
                        startTimer(activity, times[which]);
                    }
                })
                .show();
    }

    private static void startTimer(Context context, int minute) {
        QuitTimer.getInstance().start(minute * 60 * 1000);
        if (minute > 0) {
            ToastUtils.show(context.getString(R.string.timer_set, String.valueOf(minute)));
        } else {
            ToastUtils.show(R.string.timer_cancel);
        }
    }

    private static void exit(MusicAcitivity activity) {
        IMusicPlayer service = MyApplication.myApplication.getMusicPlayerService();
        if (service != null) {
            try {
                service.action(MusicService.MUSIC_ACTION_QUIT,new SongBean());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        activity.finish();

    }
}
