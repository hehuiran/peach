package me.jessyan.peach.shop.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.home.GoodsDetailInfoBean;
import me.jessyan.peach.shop.dialog.ShareDialogFragment;
import me.jessyan.peach.shop.home.di.component.DaggerCreateShareComponent;
import me.jessyan.peach.shop.home.mvp.contract.CreateShareContract;
import me.jessyan.peach.shop.home.mvp.presenter.CreateSharePresenter;
import me.jessyan.peach.shop.utils.StringUtils;
import timber.log.Timber;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/22/2018 18:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CreateShareActivity extends BaseActivity<CreateSharePresenter> implements CreateShareContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_commission)
    TextView mTvCommission;
    @BindView(R.id.iv_img_one)
    ImageView mIvImgOne;
    @BindView(R.id.check_box_one)
    AppCompatCheckBox mCheckBoxOne;
    @BindView(R.id.iv_img_two)
    ImageView mIvImgTwo;
    @BindView(R.id.check_box_two)
    AppCompatCheckBox mCheckBoxTwo;
    @BindView(R.id.iv_img_three)
    ImageView mIvImgThree;
    @BindView(R.id.check_box_three)
    AppCompatCheckBox mCheckBoxThree;
    @BindView(R.id.iv_img_four)
    ImageView mIvImgFour;
    @BindView(R.id.check_box_four)
    AppCompatCheckBox mCheckBoxFour;
    @BindView(R.id.iv_img_five)
    ImageView mIvImgFive;
    @BindView(R.id.check_box_five)
    AppCompatCheckBox mCheckBoxFive;
    @BindView(R.id.tv_good_title)
    TextView mTvGoodTitle;
    @BindView(R.id.tv_original_price)
    TextView mTvOriginalPrice;
    @BindView(R.id.tv_discount_price)
    TextView mTvDiscountPrice;
    @BindView(R.id.tv_password)
    TextView mTvPassword;
    private ArrayList<String> mImageUrlList;
    private int mCurrentCheckIndex;
    private CheckBox[] mCheckBoxes;
    private ImageView[] mImageViews;
    private ImageLoader mImageLoader;
    private GoodsDetailInfoBean mDetailInfoBean;

    public static void launcher(@NonNull Context context, GoodsDetailInfoBean infoBean, ArrayList<String> list) {
        Intent intent = new Intent(context, CreateShareActivity.class);
        intent.putExtra(IntentExtra.GOODS_DETAIL, infoBean);
        intent.putStringArrayListExtra(IntentExtra.IMAGE_LIST, list);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCreateShareComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_create_share; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mDetailInfoBean = intent.getParcelableExtra(IntentExtra.GOODS_DETAIL);
        mImageUrlList = intent.getStringArrayListExtra(IntentExtra.IMAGE_LIST);

        mImageLoader = ArmsUtils.getImageLoaderInstance(this);

        setViews();
    }

    private void setViews() {
        configImages();

        String commission = String.format(getString(R.string.your_commission_expect), StringUtils.keepTwoDecimal(mDetailInfoBean.getTkMoney()));
        mTvCommission.setText(commission);

        mTvGoodTitle.setText(mDetailInfoBean.getTitle());

        String original = String.format(getString(R.string.online_price), StringUtils.keepTwoDecimal(mDetailInfoBean.getOriginalPrice()));
        mTvOriginalPrice.setText(original);

        String discount = String.format(getString(R.string.real_price), StringUtils.keepTwoDecimal(mDetailInfoBean.getDiscountPrice()));
        mTvDiscountPrice.setText(discount);
    }

    private void configImages() {
        mImageViews = new ImageView[]{mIvImgOne, mIvImgTwo, mIvImgThree, mIvImgFour, mIvImgFive};
        mCheckBoxes = new CheckBox[]{mCheckBoxOne, mCheckBoxTwo, mCheckBoxThree, mCheckBoxFour, mCheckBoxFive};
        int size = mImageUrlList.size() > 5 ? 5 : mImageUrlList.size();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                } else {
                    int position = (int) checkBox.getTag(R.id.tag_position);
                    mCheckBoxes[mCurrentCheckIndex].setChecked(false);
                    mCurrentCheckIndex = position;
                }
            }
        };

        for (int i = 0; i < size; i++) {
            mImageViews[i].setVisibility(View.VISIBLE);
            mCheckBoxes[i].setVisibility(View.VISIBLE);
            mCheckBoxes[i].setTag(R.id.tag_position, i);
            mCheckBoxes[i].setOnClickListener(onClickListener);
            mImageLoader.loadImage(this, ImageConfigImpl.builder()
                    .imageView(mImageViews[i])
                    .url(mImageUrlList.get(i))
                    .build());
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.tv_copy_password, R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_copy_password:
                break;
            case R.id.tv_share:
                shareImage();
                break;
        }
    }

    private void shareImage() {
        String img = mImageUrlList.get(mCurrentCheckIndex);
        if (!TextUtils.isEmpty(img)) {
            ShareDialogFragment.ShareGoodsPosterBean posterBean = new ShareDialogFragment.ShareGoodsPosterBean();
            posterBean.setImg(img);
            posterBean.setCouponMoney(StringUtils.keepTwoDecimal(mDetailInfoBean.getCouponMoney()));
            posterBean.setDiscountPrice(StringUtils.keepTwoDecimal(mDetailInfoBean.getDiscountPrice()));
            posterBean.setOriginalPrice(StringUtils.keepTwoDecimal(mDetailInfoBean.getOriginalPrice()));
            posterBean.setItemId(mDetailInfoBean.getItemId());
            posterBean.setTitle(mDetailInfoBean.getTitle());
            ShareDialogFragment.newInstance(posterBean).show(getSupportFragmentManager());
        } else {
            Timber.tag(TAG).w("thumb is empty...");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
