package com.monk.player.tools;

import android.util.Log;

import com.monk.player.application.MyApplication;
import com.monk.player.contract.Contract;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017-7-19.
 */

public class RetrofitUtil {
    private static List<Disposable> mCallList=new ArrayList<>();

    /**
     * when i use the activity-model-power
     */
    private RetrofitUtil() {
    }
    private static volatile Retrofit mRetrofit;
    private static volatile Retrofit musicRetrofit;
    private static File cacheDir = MyApplication.getApplication().getCacheDir();
    private static File cacheFile = new File(cacheDir, "cache");
    private static HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d("RetrofitUtil", "message:" + message);
        }
    });
    private static HttpLoggingInterceptor musicInterceptor = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("RetrofitUtil", "message:" + message);
                }
            });

    private static long fileSize = 10 * 1024 * 1024; //10MB

    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.SECONDS)
            .addInterceptor(mHttpLoggingInterceptor)
            .cache(new Cache(cacheFile, fileSize))
            .build();
    private static OkHttpClient musicClinet = new OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.SECONDS)
            .addInterceptor(musicInterceptor)
            .cache(new Cache(cacheFile, fileSize))
            .build();

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofit == null) {
                    mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    mRetrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .baseUrl(Contract.BASE_URL)
                            .build();

                }
            }
        }
        return mRetrofit;
    }


    public static retrofit2.Retrofit getMusicRetrofit() {
        if (musicRetrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (musicRetrofit == null) {
                    musicInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    musicRetrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(musicClinet)
                            .baseUrl(Contract.BASE_MUSIC_URL)
                            .build();

                }
            }
        }
        return musicRetrofit;
    }


    public static void cancelDisposable() {
        synchronized (mCallList) {
            Iterator<Disposable> iterator = mCallList.iterator();
            while (iterator.hasNext()) {
                Disposable call = iterator.next();
                if (call == null || call.isDisposed()) {
                    continue;
                }
                call.dispose();
                iterator.remove();
            }
        }
    }

    public static void addDisposable(Disposable call) {
        synchronized (mCallList) {
            mCallList.add(call);

        }
    }
}
