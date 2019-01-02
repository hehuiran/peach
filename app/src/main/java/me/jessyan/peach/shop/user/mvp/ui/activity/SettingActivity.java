package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Space;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.EventBusManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.dialog.ChooseAlbumDialogFragment;
import me.jessyan.peach.shop.entity.event.BindAlipayEvent;
import me.jessyan.peach.shop.entity.event.ChangeAvatarEvent;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.help.MediaScanner;
import me.jessyan.peach.shop.help.glide.GlideMatisseEngine;
import me.jessyan.peach.shop.launcher.mvp.ui.activity.MainActivity;
import me.jessyan.peach.shop.user.di.component.DaggerSettingComponent;
import me.jessyan.peach.shop.user.mvp.contract.SettingContract;
import me.jessyan.peach.shop.user.mvp.presenter.SettingPresenter;
import me.jessyan.peach.shop.utils.CacheUtils;
import me.jessyan.peach.shop.utils.CameraUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/28/2018 22:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    private static final int REQUEST_CODE_MATISSE = 100;
    private static final int REQUEST_CODE_CAMERA = 200;
    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.iv_avatar)
    RoundedImageView mIvAvatar;
    @BindView(R.id.tv_sina)
    TextView mTvSina;
    @BindView(R.id.tv_wechat)
    TextView mTvWechat;
    @BindView(R.id.tv_alipay)
    TextView mTvAlipay;
    @BindView(R.id.tv_cache)
    TextView mTvCache;
    private ImageLoader mImageLoader;
    private String mCameraFilePath;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mImageLoader = ImageLoaderHelper.getImageLoader();

        setAvatarImage();
        updateCacheSize();

        updateAlipay();
    }

    private void setAvatarImage() {
        mImageLoader.loadImage(this, ImageConfigImpl.builder()
                .imageView(mIvAvatar)
                .isCircle(true)
                .url(UserInfo.getInstance().getHeadImgUrl())
                .build());
    }

    private void updateCacheSize() {
        try {
            long totalCacheSize = CacheUtils.getTotalCacheSize(Utils.getApp());
            mTvCache.setText(CacheUtils.getFormatSize(totalCacheSize));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAlipay() {
        String alipay = UserInfo.getInstance().getAlipay();
        mTvAlipay.setText(TextUtils.isEmpty(alipay) ? getString(R.string.unbind) : alipay);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.iv_avatar, R.id.v_nickname, R.id.v_mobile, R.id.v_password,
            R.id.v_sina, R.id.v_wechat, R.id.v_alipay, R.id.v_change_shipping_address,
            R.id.v_clear_cache, R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_avatar:
                showChooseAlbumDialog();
                break;
            case R.id.v_nickname:
                ChangeNicknameActivity.launcher(this);
                break;
            case R.id.v_mobile:
                break;
            case R.id.v_password:
                ChangePasswordActivity.launcher(this);
                break;
            case R.id.v_sina:
                break;
            case R.id.v_wechat:
                break;
            case R.id.v_alipay:
                BindAlipayActivity.launcher(this, BindAlipayActivity.PURPOSE_NORMAL);
                break;
            case R.id.v_change_shipping_address:
                break;
            case R.id.v_clear_cache:
                CacheUtils.clearAllCache(Utils.getApp());
                ToastUtils.showShort(R.string.clear_success);
                updateCacheSize();
                break;
            case R.id.tv_login_out:
                showLoginOutDialog();
                break;
        }
    }

    private void showLoginOutDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.tips)
                .setMessage(R.string.is_login_out)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    dialog.dismiss();
                    logout();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void logout() {
        //用户信息设置为null
        UserInfo.getInstance().setNull();
        mPresenter.logoutAlibc();
        MainActivity.backToMain(this, MainActivity.BACK_TO_MAIN_LOGOUT);
    }

    private void showChooseAlbumDialog() {
        ChooseAlbumDialogFragment.newInstance()
                .setOnChooseAlbumListener(new ChooseAlbumDialogFragment.OnChooseAlbumListener() {
                    @Override
                    public void onCameraClick() {
                        launcherCamera();
                    }

                    @Override
                    public void onAlbumClick() {
                        launcherAlbum();
                    }
                }).show(getSupportFragmentManager());
    }

    private void launcherCamera() {
        mCameraFilePath = CameraUtils.getCameraFilePath();
        if (TextUtils.isEmpty(mCameraFilePath)) {
            return;
        }
        File file = new File(mCameraFilePath);
        Uri uri = UriUtils.file2Uri(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void launcherAlbum() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), true)
                .theme(R.style.matisse_theme)
                .showSingleMediaType(true)
                .countable(true)
                .maxSelectable(1)
                .spanCount(3)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideMatisseEngine())
                .autoHideToolbarOnSingleTap(true)
                .forResult(REQUEST_CODE_MATISSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MATISSE && resultCode == RESULT_OK && data != null) {
            String path = Matisse.obtainPathResult(data).get(0);
            mPresenter.changeAvatarImage(path);
        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            new MediaScanner().scan(mCameraFilePath);
            mPresenter.changeAvatarImage(mCameraFilePath);
        }
    }

    @Override
    public void onChangeAvatarImageSuccess(String avatarUrl) {
        if (!avatarUrl.startsWith("http")) {
            avatarUrl = "http://" + avatarUrl;
        }
        UserInfo.getInstance().setHeadImgUrl(avatarUrl);
        setAvatarImage();
        EventBusManager.getInstance().post(new ChangeAvatarEvent());
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(Object event) {
        if (event instanceof BindAlipayEvent) {
            updateAlipay();
        }
    }
}
