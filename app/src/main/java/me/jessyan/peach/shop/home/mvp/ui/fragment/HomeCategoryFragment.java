package me.jessyan.peach.shop.home.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.home.di.component.DaggerHomeCategoryComponent;
import me.jessyan.peach.shop.home.mvp.contract.HomeCategoryContract;
import me.jessyan.peach.shop.home.mvp.presenter.HomeCategoryPresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeCategoryFragment extends BaseFragment<HomeCategoryPresenter> implements HomeCategoryContract.View {

    public static HomeCategoryFragment newInstance(int typeId, String oneType, String twoType) {
        HomeCategoryFragment fragment = new HomeCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentExtra.ID, typeId);
        bundle.putString(IntentExtra.ONE_TYPE, oneType);
        bundle.putString(IntentExtra.TWO_TYPE, twoType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeCategoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_category, container, false);
    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    public boolean isLazyLoad() {
        return true;
    }
}
