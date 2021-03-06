package com.monk.player.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andy.player.R;
import com.monk.player.aidl.SongBean;
import com.monk.player.interfaces.ClickMoreListner;
import com.monk.player.tools.CoverLoader;
import com.monk.player.tools.SongEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by andy on 2017/12/7.
 */

public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    List<SongBean> data;
    private ClickMoreListner<SongBean> clickMoreListner;

    public void setClickMoreListner(ClickMoreListner<SongBean> clickMoreListner) {
        this.clickMoreListner = clickMoreListner;
    }

    public LocalMusicAdapter(Context context, List<SongBean> list) {
        this.context = context;
        this.data = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_holder_music, parent, false);
        //有自定义的Holder实例，改进：从单独封装的Holder基类中获取
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvArtist.setText(data.get(position).getSingername());
        holder.tvTitle.setText(data.get(position).getSongname());
        Bitmap cover = CoverLoader.getInstance().loadThumbnail(data.get(position));
        holder.ivCover.setImageBitmap(cover);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SongEvent(data.get(position)));
            }
        });
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickMoreListner!=null){
                    clickMoreListner.ClickMore(data.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView ivCover;
        TextView tvTitle;
        TextView tvArtist;
        ImageView ivMore;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            linearLayout=(LinearLayout) itemView.findViewById(R.id.just_for_click);
            ivCover=(ImageView)itemView.findViewById(R.id.iv_cover);
            tvTitle=(TextView)itemView.findViewById(R.id.tv_title);
            tvArtist=(TextView)itemView.findViewById(R.id.tv_artist);
            ivMore=(ImageView)itemView.findViewById(R.id.iv_more);

        }
    }
}
