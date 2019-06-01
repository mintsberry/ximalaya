package com.mint.ximalaya;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mint.ximalaya.adapters.DetailListAdater;
import com.mint.ximalaya.base.BaseActivity;
import com.mint.ximalaya.interfaces.IAlbumDetailVaiewCallback;
import com.mint.ximalaya.presenters.AlbumDetailPresenterImpl;
import com.mint.ximalaya.utils.ImageBlur;
import com.mint.ximalaya.utils.LogUtil;
import com.mint.ximalaya.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

import static com.mint.ximalaya.views.UILoader.UIStatus.EMPTY;
import static com.mint.ximalaya.views.UILoader.UIStatus.NETWORK_ERROR;

public class DetailActivity extends BaseActivity implements IAlbumDetailVaiewCallback, UILoader.OnRetryClickListener {
    ImageView largeCover,smallCover;
    TextView albumTitle,albumAuthor;
    AlbumDetailPresenterImpl mAlbumDetailPresenter;
    RecyclerView detailList;
    private int mCurrentId = -1;
    private static final String TAG = "DetailActivity";
    private DetailListAdater mDetailListAdater;
    private FrameLayout mDetailListContainer;
    private UILoader mUiLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        intiView();
        mAlbumDetailPresenter = AlbumDetailPresenterImpl.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);
    }

    private void intiView() {
        mDetailListContainer = this.findViewById(R.id.detail_list_container);
        //加载UIload
        if (mUiLoader == null){
            mUiLoader = new UILoader(DetailActivity.this) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };
            mDetailListContainer.removeAllViews();
            mDetailListContainer.addView(mUiLoader);
            mUiLoader.setOnRetryClickListener(this);
        }
        largeCover = this.findViewById(R.id.iv_large_cover);
        smallCover = this.findViewById(R.id.viv_small_cover);
        albumTitle = this.findViewById(R.id.tv_album_title);
        albumAuthor = this.findViewById(R.id.tv_album_author);

    }

    private View createSuccessView(ViewGroup container) {
        View detailListView = LayoutInflater.from(this).inflate(R.layout.item_album_detail_list, container, false);
        //布局管理
        detailList = detailListView.findViewById(R.id.album_detail_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        detailList.setLayoutManager(layoutManager);
        //设置适配器
        mDetailListAdater = new DetailListAdater();
        detailList.setAdapter(mDetailListAdater);
        //设置item上下间距
        detailList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 2);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 2);
                outRect.left = UIUtil.dip2px(view.getContext(), 2);
                outRect.right = UIUtil.dip2px(view.getContext(), 2);
            }
        });
        return detailListView;
    }

    @Override
    public void getDetailListLoaded(List<Track> tracks) {
        //根据结构显示
        if (tracks == null && tracks.size() == 0) {
            mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
        } else {
            mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
        }
        //更新设置UI
        mDetailListAdater.setData(tracks);
    }

    @Override
    public void getAlbumLoaded(Album album) {
        //获取专辑
        int id = (int) album.getId();
        mCurrentId = id;
        mAlbumDetailPresenter.getAlbumDetail(id, 1);

        //显示状态
        if (mUiLoader != null){
            mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
        }
        if (albumTitle != null){
            albumTitle.setText(album.getAlbumTitle());
        }
        if (albumAuthor != null){
            albumAuthor.setText(album.getAnnouncer().getNickname());
        }
        //高斯模糊
        //TODO:
        if (largeCover != null){
            Glide.with(this).load(album.getCoverUrlLarge()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    ImageBlur.makeBlur(largeCover, DetailActivity.this);
                    Bitmap bitmap = ImageBlur.blurRenderScript(DetailActivity.this, ((BitmapDrawable) resource).getBitmap(), 20);
                    largeCover.setImageBitmap(bitmap);
                    return true;
                }
            }).into(largeCover);
        }
        if (smallCover != null){
            Glide.with(this).load(album.getCoverUrlLarge()).into(smallCover);
        }
    }

    @Override
    public void onNetworkError(String s, int i) {
        //请求发送错误回调UI
        mUiLoader.updateStatus(NETWORK_ERROR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAlbumDetailPresenter.registerViewCallback(this);
    }

    @Override
    public void onRetryClick() {
        //用户网络重新刷新
        if (mAlbumDetailPresenter != null){
            mAlbumDetailPresenter.getAlbumDetail(mCurrentId, 1);
        }
    }
}
