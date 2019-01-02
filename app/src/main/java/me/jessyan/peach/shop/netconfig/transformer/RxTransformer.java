package me.jessyan.peach.shop.netconfig.transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.netconfig.Optional;

/**
 * author: Create by HuiRan on 2018/12/30 下午9:57
 * email: 15260828327@163.com
 * description:
 */
public class RxTransformer {

    public static <T> ObservableTransformer<BasicResponse<T>, Optional<T>> handleResponse() {
        return upstream -> upstream
                .flatMap(new Function<BasicResponse<T>, ObservableSource<Optional<T>>>() {
                             @Override
                             public ObservableSource<Optional<T>> apply(@NonNull BasicResponse<T> result) throws Exception {
                                 return Observable.just(result.transform());
                                 /*if (0 == result.getCode()) {
                                     // result.transform() 就是将返回结果进行包装
                                     return createHttpData(result.transform());
                                 } else {
                                     // 发送请求失败的信息
                                     return Observable.error(new ApiException(result.getCode(), result.getMsg()));
                                 }

                                 return Observable.empty();*/
                             }
                         }
                );
    }

    /*public static <T> Observable<Optional<T>> createHttpData(Optional<T> t) {

        return Observable.create(e -> {
            try {
                e.onNext(t);
                e.onComplete();
            } catch (Exception exc) {
                e.onError(exc);
            }
        });
    }*/
}
