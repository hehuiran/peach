package me.jessyan.peach.shop.netconfig.function;

import java.lang.reflect.ParameterizedType;

import io.reactivex.functions.Function;
import me.jessyan.peach.shop.entity.BasicResponse;
import timber.log.Timber;

/**
 * author: Create by HuiRan on 2018/12/9 下午2:19
 * email: 15260828327@163.com
 * description:
 */
public class ResponseFunction<T> implements Function<BasicResponse<T>, T> {

    private static final String TAG = "ResponseFunction";

    @Override
    public T apply(BasicResponse<T> tBasicResponse) throws Exception {
        T t = tBasicResponse.getData();
        if (t == null) {
            t = getInstance(this, 0);
        }
        return t;
    }

    /**
     * 根据泛型实例化对象
     */
    @SuppressWarnings({"unchecked", "SameParameterValue"})
    private T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
            Timber.tag(TAG).e("generate response error from reflection....");
        }
        return null;
    }
}
