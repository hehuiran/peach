package me.jessyan.peach.shop.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.help.ImageLoaderHelper;

public class PreviewActivity extends BaseActivity {


    @BindView(R.id.iv_img)
    ImageView mIvImg;

    public static void luncher(Context context, String url, int width, int height, Bundle options) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(IntentExtra.URL, url);
        intent.putExtra(IntentExtra.WIDTH, width);
        intent.putExtra(IntentExtra.HEIGHT, height);
        ActivityCompat.startActivity(context, intent, options);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //do nothing
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_preview;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra(IntentExtra.URL);
        int width = intent.getIntExtra(IntentExtra.WIDTH, 0);
        int height = intent.getIntExtra(IntentExtra.HEIGHT, 0);

        ViewGroup.LayoutParams layoutParams = mIvImg.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mIvImg.setLayoutParams(layoutParams);

        ImageLoader imageLoader = ImageLoaderHelper.getImageLoader();
        imageLoader.loadImage(this, ImageConfigImpl.builder()
                .imageView(mIvImg)
                .url(imgUrl)
                .build());
    }


    @OnClick(R.id.root)
    public void onViewClicked() {
        ActivityCompat.finishAfterTransition(this);
    }
}
