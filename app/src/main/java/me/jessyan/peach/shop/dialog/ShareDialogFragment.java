package me.jessyan.peach.shop.dialog;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.widget.CouponsLayout;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * author: Create by HuiRan on 2018/12/22 下午10:36
 * email: 15260828327@163.com
 * description:
 */
public class ShareDialogFragment extends DialogFragment implements FragmentLifecycleable {

    private static final String TAG = "ShareDialogFragment";
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Unbinder unbinder;
    private String mThumb;
    private String mTitle;
    private String mDes;
    private String mLink;
    private RxPermissions mRxPermissions;
    private ShareGoodsPosterBean mShareGoodsPosterBean;
    @BindView(R.id.root)
    ConstraintLayout mRoot;
    private String mFilePath;

    public static ShareDialogFragment newInstance(ShareGoodsPosterBean shareGoodsPosterBean) {
        ShareDialogFragment fragment = new ShareDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentExtra.SHARE_POSTER, shareGoodsPosterBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ShareDialogFragment newInstance(String filePath) {
        ShareDialogFragment fragment = new ShareDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentExtra.SHARE_FILE_PATH, filePath);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ShareDialogFragment newInstance(String thumb, String title, String des, String link) {
        ShareDialogFragment fragment = new ShareDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentExtra.SHARE_THUMB, thumb);
        bundle.putString(IntentExtra.SHARE_TITLE, title);
        bundle.putString(IntentExtra.SHARE_DES, des);
        bundle.putString(IntentExtra.SHARE_LINK, link);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.from_bottom_dialog_style);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.dialog_share,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShareGoodsPosterBean = bundle.getParcelable(IntentExtra.SHARE_POSTER);
            mFilePath = bundle.getString(IntentExtra.SHARE_FILE_PATH);
            if (mShareGoodsPosterBean == null && TextUtils.isEmpty(mFilePath)) {
                mThumb = bundle.getString(IntentExtra.SHARE_THUMB);
                mTitle = bundle.getString(IntentExtra.SHARE_TITLE);
                mDes = bundle.getString(IntentExtra.SHARE_DES);
                mLink = bundle.getString(IntentExtra.SHARE_LINK);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setWindowAttributes();
    }

    /**
     * 设置dialog的大小以及位置
     */
    private void setWindowAttributes() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = ScreenUtils.getScreenWidth();
            layoutParams.height = ScreenUtils.getScreenHeight() * 185 / 667;
            window.setAttributes(layoutParams);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_wechat, R.id.tv_circle, R.id.tv_qq, R.id.tv_qzone,
            R.id.tv_sina, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat:
                doShare(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.tv_circle:
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.tv_qq:
                doShare(SHARE_MEDIA.QQ);
                break;
            case R.id.tv_qzone:
                doShare(SHARE_MEDIA.QZONE);
                break;
            case R.id.tv_sina:
                doShare(SHARE_MEDIA.SINA);
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private void doShare(SHARE_MEDIA shareType) {
        if (!UMShareAPI.get(getContext()).isInstall(getActivity(), shareType)) {
            String hint = String.format(getString(R.string.un_install_third_party_app), shareType.getName());
            ToastUtils.showShort(hint);
            return;
        }
        if (mShareGoodsPosterBean != null || shareType == SHARE_MEDIA.QQ || shareType == SHARE_MEDIA.QZONE) {
            requestPermission(shareType);
        } else {
            share(shareType);
        }
    }

    private void share(SHARE_MEDIA shareType) {
        UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                ToastUtils.showShort(R.string.share_success);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                ToastUtils.showShort(R.string.share_failed);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                ToastUtils.showShort(R.string.share_cancel);
            }
        };
        if (mShareGoodsPosterBean == null && TextUtils.isEmpty(mFilePath)) {
            //分享链接
            shareLink(shareType, umShareListener);
        } else if (mShareGoodsPosterBean != null) {
            //分享二维码
            shareGoodsPoster(shareType, umShareListener);
        } else if (!TextUtils.isEmpty(mFilePath)) {
            //分享文件图片
            shareFile(mFilePath, shareType, umShareListener);
        }

    }

    private void shareFile(String filePath, SHARE_MEDIA shareType, UMShareListener shareListener) {
        UMImage umImage = new UMImage(getContext(), new File(filePath));
        new ShareAction(getActivity())
                .setPlatform(shareType)
                .withMedia(umImage)
                .setCallback(shareListener)
                .share();
    }

    private void shareLink(SHARE_MEDIA shareType, UMShareListener shareListener) {
        UMWeb umWeb = new UMWeb(mLink);
        umWeb.setTitle(mTitle);
        umWeb.setDescription(mDes);
        umWeb.setThumb(new UMImage(getContext(), mThumb));

        new ShareAction(getActivity())
                .setPlatform(shareType)
                .withMedia(umWeb)
                .setCallback(shareListener)
                .share();
    }

    private void shareGoodsPoster(SHARE_MEDIA shareType, UMShareListener shareListener) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.share_goods_poster, null);

        ImageView ivGoods = inflate.findViewById(R.id.iv_goods);
        ImageView ivQr = inflate.findViewById(R.id.iv_qr);
        TextView tvTitle = inflate.findViewById(R.id.tv_title);
        TextView tvDiscount = inflate.findViewById(R.id.tv_discount_price);
        TextView tvOriginal = inflate.findViewById(R.id.tv_original_price);
        CouponsLayout couponsLayout = inflate.findViewById(R.id.coupons_layout);

        tvTitle.setText(mShareGoodsPosterBean.getTitle());
        String discount = String.format(getString(R.string.rmb), mShareGoodsPosterBean.getDiscountPrice());
        tvDiscount.setText(discount);
        String original = String.format(getString(R.string.coupon_before_price_yuan), mShareGoodsPosterBean.getOriginalPrice());
        tvOriginal.setText(original);
        tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        couponsLayout.setMoney(mShareGoodsPosterBean.getCouponMoney());

        String qrUrl = String.format(CommonConstant.SHARE_POSTER_BASE_URL, mShareGoodsPosterBean.getItemId(),
                UserInfo.getInstance().getInviteCode(), UserInfo.getInstance().getPid());
        String[] urls = {mShareGoodsPosterBean.getImg(), qrUrl};

        RxErrorHandler rxErrorHandler = ArmsUtils.obtainAppComponentFromContext(getContext()).rxErrorHandler();
        Observable.just(urls)
                .map(new Function<String[], Bitmap[]>() {
                    @Override
                    public Bitmap[] apply(String[] strings) throws Exception {
                        int width = ScreenUtils.getScreenWidth();
                        ImageLoader imageLoader = ArmsUtils.getImageLoaderInstance(getContext());
                        Bitmap goodsBitmap = imageLoader.synGetBitmap(getContext(), ImageConfigImpl.builder()
                                .url(strings[0])
                                .width(width)
                                .height(width).build());

                        int qrWidth = SizeUtils.dp2px(88);
                        Bitmap qrBitmap = CodeUtils.createImage(strings[1], qrWidth, qrWidth, null);
                        return new Bitmap[]{goodsBitmap, qrBitmap};
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this))
                .subscribe(new ErrorHandleSubscriber<Bitmap[]>(rxErrorHandler) {
                    @Override
                    public void onNext(Bitmap[] bitmaps) {
                        ivGoods.setImageBitmap(bitmaps[0]);
                        ivQr.setImageBitmap(bitmaps[1]);

                        String dirPath = getShareDirPath();
                        if (FileUtils.createOrExistsDir(dirPath)) {
                            String path = dirPath + File.separator
                                    + System.currentTimeMillis() + ".jpg";
                            int width = ScreenUtils.getScreenWidth();
                            int height = ScreenUtils.getScreenHeight();
                            inflate.layout(0, 0, width, height);
                            int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                            int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
                            inflate.measure(measuredWidth, measuredHeight);
                            inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());

                            if (FileUtils.createOrExistsFile(path) &&
                                    ImageUtils.save(ImageUtils.view2Bitmap(inflate), path, Bitmap.CompressFormat.JPEG)) {
                                shareFile(path, shareType, shareListener);
                            }
                        }

                    }
                });
    }

    private static String getCachePath() {
        File cacheDir = Utils.getApp().getExternalCacheDir();
        if (cacheDir == null || !cacheDir.exists()) {
            cacheDir = Utils.getApp().getCacheDir();
        }
        return cacheDir.getAbsolutePath();
    }

    public static String getShareDirPath() {
        return getCachePath() + File.separator + "share";
    }

    private void requestPermission(SHARE_MEDIA shareType) {
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
        }
        RxErrorHandler rxErrorHandler = ArmsUtils.obtainAppComponentFromContext(getContext()).rxErrorHandler();
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                share(shareType);
            }
        }, mRxPermissions, rxErrorHandler);
    }

    @NonNull
    @Override
    public Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    public static class ShareGoodsPosterBean implements Parcelable {

        private String img;
        private String itemId;
        private String title;
        private String couponMoney;
        private String originalPrice;
        private String discountPrice;

        public ShareGoodsPosterBean() {

        }

        protected ShareGoodsPosterBean(Parcel in) {
            img = in.readString();
            itemId = in.readString();
            title = in.readString();
            couponMoney = in.readString();
            originalPrice = in.readString();
            discountPrice = in.readString();
        }

        public static final Creator<ShareGoodsPosterBean> CREATOR = new Creator<ShareGoodsPosterBean>() {
            @Override
            public ShareGoodsPosterBean createFromParcel(Parcel in) {
                return new ShareGoodsPosterBean(in);
            }

            @Override
            public ShareGoodsPosterBean[] newArray(int size) {
                return new ShareGoodsPosterBean[size];
            }
        };

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(img);
            dest.writeString(itemId);
            dest.writeString(title);
            dest.writeString(couponMoney);
            dest.writeString(originalPrice);
            dest.writeString(discountPrice);
        }
    }
}
