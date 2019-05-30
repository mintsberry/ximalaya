package com.mint.ximalaya.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mint.ximalaya.R;
import com.mint.ximalaya.base.BaseApplication;

public abstract class UILoader extends FrameLayout {

    private View loadingView,successView,newtWorkError,emptyView;
    private OnRetryClickListener mOnRetryClickstener;

    public enum  UIStatus{
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE;
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(Context context) {
        super(context, null);
    }

    public UILoader( Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public UILoader( Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化UI
     */
    private void init() {
        switchUIByCurrentStatus();
    }

    public void updateStatus(UIStatus status){
        mCurrentStatus = status;
        BaseApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }

    private void switchUIByCurrentStatus() {
        if (loadingView == null) {
            loadingView = getLoadingView();
            addView(loadingView);
        }
        //根据状态设置是否可见
        loadingView.setVisibility(mCurrentStatus==UIStatus.LOADING?VISIBLE : GONE);

        //成功
        if (successView == null) {
            successView = getSuccessView(this);
            addView(successView);
        }
        //根据状态设置是否可见
        successView.setVisibility(mCurrentStatus==UIStatus.SUCCESS?VISIBLE : GONE);

        //网络错误
        if (newtWorkError == null) {
            newtWorkError = getNetWorkError();
            addView(newtWorkError);
        }
        //根据状态设置是否可见
        newtWorkError.setVisibility(mCurrentStatus==UIStatus.NETWORK_ERROR?VISIBLE : GONE);

        //数据为空
        if (emptyView == null) {
            emptyView = getEmptyView();
            addView(emptyView);
        }
        //根据状态设置是否可见
        emptyView.setVisibility(mCurrentStatus==UIStatus.EMPTY?VISIBLE : GONE);
    }

    private View getEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
    }

    private View getNetWorkError() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_error_view, this, false);
        view.findViewById(R.id.network_error).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //去重新刷新
                if (mOnRetryClickstener != null){
                    mOnRetryClickstener.onRetryClick();
                }
            }
        });
        return view;
    }

    protected abstract View getSuccessView(ViewGroup container);

    private View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
    }

    public void setOnRetryClickListener(OnRetryClickListener listener){
        this.mOnRetryClickstener = listener;
    }

    public interface  OnRetryClickListener{
        void onRetryClick();
    }
}
