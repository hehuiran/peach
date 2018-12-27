package me.jessyan.peach.shop.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.search.SearchHotValueBean;
import me.jessyan.peach.shop.entity.search.SearchOptionalBean;
import me.jessyan.peach.shop.entity.search.SearchRecordBean;
import me.jessyan.peach.shop.home.di.component.DaggerSearchGoodsComponent;
import me.jessyan.peach.shop.home.mvp.contract.SearchGoodsContract;
import me.jessyan.peach.shop.home.mvp.presenter.SearchGoodsPresenter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.SearchHotAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.SearchRecordAdapter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2018 23:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchGoodsActivity extends BaseActivity<SearchGoodsPresenter> implements SearchGoodsContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_text)
    EditText mEditText;
    @BindView(R.id.flow_layout_hot)
    TagFlowLayout mFlowLayoutHot;
    @BindView(R.id.flow_layout_record)
    TagFlowLayout mFlowLayoutRecord;
    @BindView(R.id.group_hot)
    Group mGroupHot;
    @BindView(R.id.group_record)
    Group mGroupRecord;

    public static void launcher(Context context) {
        launcher(context, false);
    }

    public static void launcher(Context context, boolean addFlag) {
        Intent intent = new Intent(context, SearchGoodsActivity.class);
        if (addFlag) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchGoodsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_goods; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initListener();

        mPresenter.getSearchData();
    }

    private void initListener() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchGoods();
                }
                return false;
            }
        });
        TagFlowLayout.OnTagClickListener onTagClickListener = new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Object item = ((TagFlowLayout) parent).getAdapter().getItem(position);
                if (item instanceof SearchHotValueBean){
                    saveSearchRecordAndLauncherSearchResult(((SearchHotValueBean) item).getValue());
                }else if (item instanceof SearchRecordBean){
                    saveSearchRecordAndLauncherSearchResult(((SearchRecordBean) item).getValue());
                }
                return false;
            }
        };
        mFlowLayoutRecord.setOnTagClickListener(onTagClickListener);
        mFlowLayoutHot.setOnTagClickListener(onTagClickListener);
    }

    /**
     * 保存搜索关键词到历史搜索记录并去搜索
     */
    private void saveSearchRecordAndLauncherSearchResult(String value) {
        mEditText.setText(value);
        mEditText.setSelection(value.length());
        mPresenter.saveSearchRecord(value);
        launcherSearchResult(value);
    }

    private void searchGoods() {
        Editable editable = mEditText.getText();
        if (TextUtils.isEmpty(editable)) {
            ToastUtils.showShort(R.string.input_search_key);
        } else {
            KeyboardUtils.hideSoftInput(mEditText);
            String value = editable.toString();
            mPresenter.saveSearchRecord(value);
            launcherSearchResult(value);
        }
    }

    private void launcherSearchResult(String value) {
        if (!TextUtils.isEmpty(value)) {
            SearchResultActivity.launcher(this, value);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .init();
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        KeyboardUtils.hideSoftInput(mEditText);
        onBackPressed();
    }

    @Override
    public void onGetSearchDataSuccess(SearchOptionalBean bean) {
        List<SearchHotValueBean> hotList = bean.getHotList();
        if (hotList != null && !hotList.isEmpty()) {
            mGroupHot.setVisibility(View.VISIBLE);
            mFlowLayoutHot.setAdapter(new SearchHotAdapter(hotList));
        }

        fillSearchRecordData(bean.getRecordList());
    }

    @Override
    public void onSaveSearchRecordSuccess(List<SearchRecordBean> recordList) {
        fillSearchRecordData(recordList);
    }

    private void fillSearchRecordData(List<SearchRecordBean> recordList) {
        if (recordList != null && !recordList.isEmpty()) {
            if (mGroupRecord.getVisibility() != View.VISIBLE) {
                mGroupRecord.setVisibility(View.VISIBLE);
            }
            mFlowLayoutRecord.setAdapter(new SearchRecordAdapter(recordList));
        }
    }
}
