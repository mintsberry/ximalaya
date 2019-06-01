package com.mint.ximalaya.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mint.ximalaya.R;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailListAdater extends RecyclerView.Adapter<DetailListAdater.InnerHolder> {
    private List<Track> detailData = new ArrayList<>();
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mDurationFormat = new SimpleDateFormat("mm:ss");
    private ItemClickListener mItemClickListner;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_album_detail, viewGroup, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder innerHolder, final int i) {
        //
        Track track = detailData.get(i);
        innerHolder.orderTv.setText(String.valueOf(i+1));
        innerHolder.playcountTv.setText(String.valueOf(track.getPlayCount()));
        innerHolder.titleTv.setText(String.valueOf(track.getTrackTitle()));
        String duration = mDurationFormat.format(track.getDuration() * 1000);
        innerHolder.durationTv.setText(duration);
        String updateTime = mSimpleDateFormat.format(track.getUpdatedAt());
        innerHolder.updataDateTv.setText(updateTime);

        //设置点击事件
        innerHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListner != null){
                    //参数需要列表和位置

                    mItemClickListner.onItemClick(detailData, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailData.size();
    }

    public void setData(List<Track> tracks) {
        detailData.clear();
        detailData.addAll(tracks);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        TextView orderTv, titleTv, playcountTv, durationTv, updataDateTv;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            orderTv = itemView.findViewById(R.id.order_text);
            titleTv = itemView.findViewById(R.id.detail_item_title);
            playcountTv = itemView.findViewById(R.id.detail_item_playcount);
            durationTv = itemView.findViewById(R.id.detail_item_duration);
            updataDateTv = itemView.findViewById(R.id.detail_item_update_time);
        }
    }

    public void setItemCLickListener(ItemClickListener listener){
        mItemClickListner = listener;
    }

    public interface ItemClickListener{
        void onItemClick(List<Track> tracks, int position);
    }
}
