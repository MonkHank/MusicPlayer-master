package com.monk.player.mvp.paly;

import com.monk.player.aidl.SongBean;
import com.monk.player.bean.AbstractResultUtil;
import com.monk.player.bean.Lyric;
import com.monk.player.contract.Contract;
import com.monk.player.mvp.base.BaseModel;
import com.monk.player.mvp.paly.Api.PlayService;
import com.monk.player.tools.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by andy on 2017/9/27.
 */

public class PlayModle extends BaseModel<PlayPresnter>{
    public PlayModle(PlayPresnter presenter) {
        super(presenter);
    }
    public  void getSongs(Observer<SongBean> observer) {
        Retrofit client= RetrofitUtil.getRetrofit();
        PlayService service=client.create(PlayService.class);
        service.getSongs()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void toGetTheLyrics(Observer<AbstractResultUtil<Lyric>> observer, int musicid){
        Retrofit retrofit= RetrofitUtil.getMusicRetrofit();
        PlayService api=retrofit.create(PlayService.class);
        api.getTheLyrics(Contract.appid,Contract.sign,System.currentTimeMillis(),musicid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
