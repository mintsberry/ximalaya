package com.mint.ximalaya.adapters;

import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mint.ximalaya.R;
import com.mint.ximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {
    private List<Album> mData = new ArrayList<>();
    private static final String TAG = "RecommendListAdapter";
    private onRecommendItemClickListner itemClickListner;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //生成视图
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend, viewGroup, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder innerHolder, int i) {
        //设置数据
        innerHolder.itemView.setTag(i);
        innerHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListner != null){
                    int clickPosition = (Integer) v.getTag();
                    itemClickListner.onItemClick(clickPosition, mData.get(clickPosition));
                }
            }
        });
        innerHolder.setData(mData.get(i));
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (mData != null){
            mData.clear();
            mData.addAll(albumList);
        }
        LogUtil.d(TAG, "change");
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        ImageView albumCoverIv;
        TextView albumTitleTv, albumDesrcTv, albumPlayCount, albumContenCountTv;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            albumCoverIv = itemView.findViewById(R.id.album_cover);
            albumTitleTv = itemView.findViewById(R.id.album_title_tv);
            albumDesrcTv = itemView.findViewById(R.id.album_description_tv);
            albumPlayCount = itemView.findViewById(R.id.album_play_count);
            albumContenCountTv = itemView.findViewById(R.id.album_content_size);
         }

        public void setData(Album album) {
            //设置数据
            Glide.with(itemView.getContext()).load(album.getCoverUrlLarge()).into(albumCoverIv);
            //            //专辑头像
            //标题
            albumTitleTv.setText(album.getAlbumTitle());
            //描述
            albumDesrcTv.setText(album.getAlbumIntro());
            //播放数量
            albumPlayCount.setText(album.getPlayCount()+ "");
            //集数
            albumContenCountTv.setText(album.getIncludeTrackCount()+ "");
        }
    }
    public void setOnRecommendItemClickListner(onRecommendItemClickListner listner){
        this.itemClickListner = listner;
    }

    public interface  onRecommendItemClickListner{
        void onItemClick(int postion, Album album);
    }
}
