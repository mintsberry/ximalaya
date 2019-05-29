package com.mint.ximalaya;

import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mint.ximalaya.adapters.IndicatorAdater;
import com.mint.ximalaya.adapters.MainContetAdapter;
import com.mint.ximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    MagicIndicator magicIndicator;
    ViewPager contentPage;
    IndicatorAdater indicatorAdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEven();
    }

    private void initEven() {
        indicatorAdater.setonIndicatorAdaterTapClickListener(new IndicatorAdater.OnIndicatorTapClickListener() {
            @Override
            public void onTabClick(int index) {
                LogUtil.d(TAG, "Click --> " + index);
                contentPage.setCurrentItem(index);
            }
        });
    }

    private void initView() {
        magicIndicator = this.findViewById(R.id.main_indicator);
        magicIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));
        //创建indicator的适配器
        indicatorAdater = new IndicatorAdater(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(indicatorAdater);
        commonNavigator.setAdjustMode(true);
        //设置显示内容



        //Viewpage
        contentPage = findViewById(R.id.content_pager);
        //创建适配器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        MainContetAdapter mainContetAdapter = new MainContetAdapter(supportFragmentManager);
        contentPage.setAdapter(mainContetAdapter);

        //把viewpage和indicator绑定在一起
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, contentPage);
    }
}
