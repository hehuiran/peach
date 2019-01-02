package me.jessyan.peach.shop.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * author: Create by HuiRan on 2018/12/28 下午11:21
 * email: 15260828327@163.com
 * description:
 */
public class ChooseAlbumDialogFragment extends DialogFragment {

    private static final String TAG = "ChooseAlbumDialogFragment";
    private RxPermissions mRxPermissions;

    public static ChooseAlbumDialogFragment newInstance() {
        return new ChooseAlbumDialogFragment();
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
        return LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose_album,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        View.OnClickListener onClickListener = new OnSingleClickListener() {
            @Override
            public void onClicked(View view) {
                switch (view.getId()) {
                    case R.id.tv_camera:
                        requestCameraPermission();
                        break;
                    case R.id.tv_album:
                        requestExternalStoragePermission();
                        break;
                    case R.id.tv_cancel:
                        dismiss();
                        break;
                }
            }
        };
        view.findViewById(R.id.tv_camera).setOnClickListener(onClickListener);
        view.findViewById(R.id.tv_album).setOnClickListener(onClickListener);
        view.findViewById(R.id.tv_cancel).setOnClickListener(onClickListener);
    }

    private void requestCameraPermission(){
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
        }
        RxErrorHandler rxErrorHandler = ArmsUtils.obtainAppComponentFromContext(getContext()).rxErrorHandler();
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                if (mOnChooseAlbumListener != null) {
                    mOnChooseAlbumListener.onCameraClick();
                }
                dismiss();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                ToastUtils.showShort(com.jess.arms.R.string.request_permissions_failure);
                dismiss();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                ToastUtils.showShort(com.jess.arms.R.string.need_to_go_to_the_settings);
                dismiss();
            }
        }, mRxPermissions, rxErrorHandler);
    }

    private void requestExternalStoragePermission(){
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
        }
        RxErrorHandler rxErrorHandler = ArmsUtils.obtainAppComponentFromContext(getContext()).rxErrorHandler();
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                if (mOnChooseAlbumListener != null) {
                    mOnChooseAlbumListener.onAlbumClick();
                }
                dismiss();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                ToastUtils.showShort(com.jess.arms.R.string.request_permissions_failure);
                dismiss();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                ToastUtils.showShort(com.jess.arms.R.string.need_to_go_to_the_settings);
                dismiss();
            }
        }, mRxPermissions, rxErrorHandler);
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
            layoutParams.height = SizeUtils.dp2px(163.5f);
            window.setAttributes(layoutParams);
        }
    }

    private OnChooseAlbumListener mOnChooseAlbumListener;

    public ChooseAlbumDialogFragment setOnChooseAlbumListener(OnChooseAlbumListener onChooseAlbumListener) {
        this.mOnChooseAlbumListener = onChooseAlbumListener;
        return this;
    }

    public interface OnChooseAlbumListener {
        void onCameraClick();

        void onAlbumClick();
    }
}
