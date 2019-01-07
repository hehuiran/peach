package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Space;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;

public class NewUserGuidelineActivity extends BaseActivity {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, NewUserGuidelineActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_new_user_guideline;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        NewUserGuidelineAdapter adapter = new NewUserGuidelineAdapter(mockData());
        mRecyclerView.setAdapter(adapter);
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

    private static class NewUserGuidelineAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        private NewUserGuidelineAdapter(@Nullable List<String> data) {
            super(R.layout.item_new_user_guideline, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
