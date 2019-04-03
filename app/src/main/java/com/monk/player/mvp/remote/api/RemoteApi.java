package com.monk.player.mvp.remote.api;

import com.monk.player.bean.Lrc;
import com.monk.player.bean.PlayeBean;
import com.monk.player.bean.RecomendSongList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by andy on 2017/12/14.
 */

public interface RemoteApi {

    @GET("ting")
    Observable<RecomendSongList> getHotList(@Header("User-Agent")String cookie,
                                            @Query("format") String  format,
                                            @Query("calback") String  calback,
                                            @Query("from") String  from,
                                            @Query("method") String  method,
                                            @Query("type") int  type,
                                            @Query("size") int size,
                                            @Query("offset") int offset);

    @GET("ting")
    Observable<PlayeBean> getPlayBean(@Header("User-Agent")String cookie,
                                     @Query("format") String  format,
                                     @Query("calback") String  calback,
                                     @Query("from") String  from,
                                     @Query("method") String  method,
                                     @Query("songid") int  songid);
    @GET("ting")
    Observable<Lrc> getLrc(@Header("User-Agent")String cookie,
                           @Query("format") String  format,
                           @Query("calback") String  calback,
                           @Query("from") String  from,
                           @Query("method") String  method,
                           @Query("songid") int  songid);
}
