package com.mint.ximalaya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Size;
import android.view.View;

import com.mint.ximalaya.MainActivity;
import com.mint.ximalaya.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Set;

public class IndicatorAdater extends CommonNavigatorAdapter {
    private final String[] mTitles;
    private OnIndicatorTapClickListener listner;

    public IndicatorAdater(Context context) {
        mTitles = context.getResources().getStringArray(R.array.indicator_title);
    }

    @Override
    public int getCount() {
        if (mTitles != null){
            return mTitles.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
        simplePagerTitleView.setNormalColor(Color.GRAY);
        simplePagerTitleView.setSelectedColor(Color.WHITE);
        simplePagerTitleView.setText(mTitles[index]);
        simplePagerTitleView.setTextSize(18);
        simplePagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mViewPager.setCurrentItem(index);
                if (listner != null){
                    listner.onTabClick(index);
                }
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setColors(Color.WHITE);
        return linePagerIndicator;
    }

    public void setonIndicatorAdaterTapClickListener(OnIndicatorTapClickListener listener){
        this.listner = listener;
    }
    public interface OnIndicatorTapClickListener{
        void onTabClick(int index);
    }
}
