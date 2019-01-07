package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Space;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.mine.di.component.DaggerPlatformNoticeComponent;
import me.jessyan.peach.shop.mine.mvp.contract.PlatformNoticeContract;
import me.jessyan.peach.shop.mine.mvp.presenter.PlatformNoticePresenter;
import me.jessyan.peach.shop.mine.mvp.ui.adapter.PlatformNoticeAdapter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2019 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PlatformNoticeActivity extends BaseActivity<PlatformNoticePresenter> implements PlatformNoticeContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, PlatformNoticeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPlatformNoticeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_platform_notice; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        PlatformNoticeAdapter adapter = new PlatformNoticeAdapter();
        mRecyclerView.setAdapter(adapter);

        adapter.setNewData(mockData());
    }

    private List<String> mockData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add("ddd");
        }
        return list;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
