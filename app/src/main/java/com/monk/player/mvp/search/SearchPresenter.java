package com.monk.player.mvp.search;

import com.monk.player.aidl.SongBean;
import com.monk.player.bean.SearchBean;
import com.monk.player.mvp.base.BasePresenter;
import com.monk.player.tools.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by andy on 2017/12/25.
 */

public class SearchPresenter extends BasePresenter<SearchMusicActivity,SearchModle>{
    @Override
    public SearchModle createModel() {
        return new SearchModle(this);
    }

    public void searchResult(String keyword,int page){
        mModel.searchReuslt(new Observer<SearchBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                RetrofitUtil.addDisposable(d);
            }

            @Override
            public void onNext(SearchBean value) {
                if(value.getSong()!=null) {
                    mView.ongetSearchResult(converTo(value.getSong()));
                }else{
                    mView.loadFail();
                }
            }
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },keyword,page);
    }

    public List<SongBean> converTo(List<SearchBean.SongBean> list){
        List<SongBean> myList=new ArrayList<>();
        for(SearchBean.SongBean song:list){
            SongBean songBean=new SongBean();
            songBean.setSingername(song.getArtistname());
            songBean.setSongname(song.getSongname());
            songBean.setSongid(Long.valueOf(song.getSongid()));
            myList.add(songBean);
        }
        return  myList;
    }
}
