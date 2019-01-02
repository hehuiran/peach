package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.dialog.ShareDialogFragment;
import me.jessyan.peach.shop.entity.user.InviteFriendBean;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.help.ImageQrCodeLoader;
import me.jessyan.peach.shop.help.glide.GlideRoundTransform;
import me.jessyan.peach.shop.mine.di.component.DaggerInviteComponent;
import me.jessyan.peach.shop.mine.mvp.contract.InviteContract;
import me.jessyan.peach.shop.mine.mvp.presenter.InvitePresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 13:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class InviteActivity extends BaseActivity<InvitePresenter> implements InviteContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    //图片的宽
    private static final int W = 414;
    //图片的高
    private static final int H = 736;
    //二维码的宽高
    private static final int SCAN = 80;
    //距离左边的距离
    private static final int LEFT = 17;
    //距离上面的距离
    private static final int TOP = 639;
    private ImageLoader mImageLoader;
    private List<InviteFriendBean.DataBean> mData;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, InviteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInviteComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_invite; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ImageLoaderHelper.getImageLoader();

        mPresenter.getInviteData();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.iv_copy, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_copy:
                copyUrl();
                break;
            case R.id.iv_share:
                sharePoster();
                break;
        }
    }

    private void sharePoster() {
        if (mViewPager.getChildCount() == 0) {
            return;
        }
        View view = mViewPager.getChildAt(mViewPager.getCurrentItem());
        String dirPath = ShareDialogFragment.getShareDirPath();
        if (FileUtils.createOrExistsDir(dirPath)) {
            String path = dirPath + File.separator
                    + System.currentTimeMillis() + ".jpg";
            if (FileUtils.createOrExistsFile(path) &&
                    ImageUtils.save(ImageUtils.view2Bitmap(view), path, Bitmap.CompressFormat.JPEG)) {
                ShareDialogFragment.newInstance(path).show(getSupportFragmentManager());
            }
        }
    }

    private void copyUrl() {
        int size = mData == null ? 0 : mData.size();
        if (size > 0) {
            String shareUrl = mData.get(mViewPager.getCurrentItem()).getShareUrl();
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText("text", shareUrl);
                clipboardManager.setPrimaryClip(clipData);
                ToastUtils.showShort(R.string.copy_success);
            }
        }
    }

    @Override
    public void onGetInviteDataSuccess(List<InviteFriendBean.DataBean> data) {
        int size = data == null ? 0 : data.size();
        if (size > 0) {
            initViewPager(data);
        }
    }

    private void initViewPager(List<InviteFriendBean.DataBean> data) {
        this.mData = data;
        //可视图片宽、高
        int itemWidth = ScreenUtils.getScreenWidth() * 3 / 5;
        int itemHeight = itemWidth * H / W;
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        layoutParams.width = itemWidth;
        layoutParams.height = itemHeight;

        List<View> views = new ArrayList<>();
        for (InviteFriendBean.DataBean bean : data) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_invite_friend, mViewPager, false);
            ImageView ivImg = view.findViewById(R.id.iv_img);
            ImageView ivQr = view.findViewById(R.id.iv_qr);
            int qrSize = itemWidth * SCAN / W;

            //2微码的大小
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivQr.getLayoutParams();
            params.width = qrSize;
            params.height = qrSize;
            params.rightMargin = itemWidth * LEFT / W;
            params.topMargin = itemHeight * TOP / H;

            ImageQrCodeLoader.getDefault().bindQRCodBitmap(bean.getShareUrl(), ivQr, qrSize, qrSize);

            mImageLoader.loadImage(this, ImageConfigImpl.builder()
                    .imageView(ivImg)
                    .url(bean.getImg())
                    .transformation(new GlideRoundTransform(SizeUtils.dp2px(10)))
                    .build());

            views.add(view);
        }

        mViewPager.setAdapter(new InviteFriendPagerAdapter(views));
        mViewPager.setOffscreenPageLimit(views.size());
        mViewPager.setPageMargin(SizeUtils.dp2px(30));
    }

    private static class InviteFriendPagerAdapter extends PagerAdapter {

        private List<View> mViews;

        public InviteFriendPagerAdapter(List<View> views) {
            mViews = views;
        }

        @Override
        public int getCount() {
            return mViews == null ? 0 : mViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mViews.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
