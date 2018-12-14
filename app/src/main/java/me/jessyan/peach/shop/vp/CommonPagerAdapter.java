package me.jessyan.peach.shop.vp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 0021.
 */
public abstract class CommonPagerAdapter<T> extends FragmentPagerAdapter {

    protected List<T> mData;
    protected final String TAG = this.getClass().getSimpleName();

    public CommonPagerAdapter(FragmentManager manager, List<T> mDatas) {
        super(manager);
        this.mData = mDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    protected abstract Fragment getFragment(int position);

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

}
