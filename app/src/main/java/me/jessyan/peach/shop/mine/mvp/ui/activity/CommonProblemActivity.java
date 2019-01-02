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
import me.jessyan.peach.shop.entity.mine.CommonProblemBean;
import me.jessyan.peach.shop.mine.di.component.DaggerCommonProblemComponent;
import me.jessyan.peach.shop.mine.mvp.contract.CommonProblemContract;
import me.jessyan.peach.shop.mine.mvp.presenter.CommonProblemPresenter;
import me.jessyan.peach.shop.mine.mvp.ui.adapter.CommonProblemAdapter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/02/2019 01:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CommonProblemActivity extends BaseActivity<CommonProblemPresenter> implements CommonProblemContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, CommonProblemActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonProblemComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_common_problem; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        CommonProblemAdapter adapter = new CommonProblemAdapter(fetchData());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    private List<CommonProblemBean> fetchData() {
        List<CommonProblemBean> list = new ArrayList<>();

        int[] icons = {R.mipmap.ic_about_commission, R.mipmap.ic_about_commission, R.mipmap.ic_about_coupon, R.mipmap.ic_about_share, R.mipmap.ic_about_other};
        String[] titles = getResources().getStringArray(R.array.common_problem_title_array);
        String[] tops = getResources().getStringArray(R.array.common_problem_top_array);
        String[] bottoms = getResources().getStringArray(R.array.common_problem_bottom_array);

        for (int i = 0; i < icons.length; i++) {
            CommonProblemBean bean = new CommonProblemBean();
            bean.setIcon(icons[i]);
            bean.setTitle(titles[i]);
            bean.setTopProblem(tops[i]);
            bean.setBottomProblem(bottoms[i]);
            list.add(bean);
        }
        return list;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
