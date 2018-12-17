package me.jessyan.peach.shop.netconfig;

import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import me.jessyan.peach.shop.BuildConfig;
import me.jessyan.peach.shop.entity.user.UserInfo;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

/**
 * author: Created by HuiRan on 2017/12/27 17:42
 * E-Mail: 15260828327@163.com
 * description:公共参数
 */

public class BasicParamsInterceptor implements Interceptor {

    private static final String TAG = "BasicParamsInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String url = request.url().toString();
        if (url.contains(BuildConfig.BASE_URL)) {
            RequestBody body = request.body();
            if (body instanceof MultipartBody) {
                request = handlerMultipartBody(request);
            } else {
                request = handlerRequest(request);
            }

            if (request == null) {
                throw new NullPointerException("Request返回值不能为空");
            }
        }


        return chain.proceed(request);
    }

    private Request handlerMultipartBody(Request request) {
        String method = request.method();
        Map<String, Object> params = new HashMap<>();
        //这里为公共的参数
        params.put("timestamp", System.currentTimeMillis());
        params.put("version", AppUtils.getAppVersionName());
        params.put("deviceType", String.valueOf(2));
        params.put("channel", "");
        params.put("token", UserInfo.getInstance().getToken());
        //params.put("deviceSerialId", SampleApplicationLike.getIMEI());

        String sign = getSign(params);
        params.put("sign", sign);

        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Iterator iterator = params.entrySet().iterator();
        Timber.tag(TAG).d("OkHttp:《《《《《《  读取参数 - Multipart  》》》》》》");
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            multipartBuilder.addFormDataPart((String) entry.getKey(), String.valueOf(entry.getValue()));
            Timber.tag(TAG).d("OkHttp:参数" + entry.getKey() + "  value=" + entry.getValue() + "\n");
        }


        MultipartBody multipartBody = (MultipartBody) request.body();
        List<MultipartBody.Part> oldParts = multipartBody.parts();
        if (oldParts != null && oldParts.size() > 0) {

            try {
                for (MultipartBody.Part part : oldParts) {
                    multipartBuilder.addPart(part);
                    Timber.tag(TAG).d("参数：part=" + part.body() + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        Timber.tag(TAG).d("《《《《《《  读取参数 - Multipart ending  》》》》》》");
        return request.newBuilder().method(method, multipartBuilder.build()).build();
    }

    private Request handlerRequest(Request request) {
        String method = request.method();
        /*if ("GET".equals(method)) {
            return request;
        }*/

        Map<String, Object> params = null;
        if (!"GET".equals(method)) {
            params = parseParams(request);
        }
        if (params == null) {
            params = new HashMap<>();
        }
        //这里为公共的参数
        params.put("timestamp", System.currentTimeMillis());
        params.put("version", AppUtils.getAppVersionName());
        params.put("deviceType", String.valueOf(2));
        params.put("channel", "");
        params.put("token", UserInfo.getInstance().getToken());
        //params.put("deviceSerialId", SampleApplicationLike.getIMEI());
        String sign = getSign(params);
        params.put("sign", sign);

        Timber.tag(TAG).d("《《《《《《  读取参数   》》》》》》");

        if ("GET".equals(method)) {
            Iterator iterator = params.entrySet().iterator();
            Request.Builder requestBuilder = request.newBuilder();
            HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();

                httpUrlBuilder.addQueryParameter((String) entry.getKey(), String.valueOf(entry.getValue()));
            }
            requestBuilder.url(httpUrlBuilder.build());
            return requestBuilder.build();
        } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
            if (request.body() instanceof FormBody) {
                FormBody.Builder newBodyBuilder = new FormBody.Builder();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
//                    LogUtils.e(TAG, "key=" + key + "   ->value=" + String.valueOf(value));
                    newBodyBuilder.add(key, String.valueOf(value));
                    Timber.tag(TAG).d("参数：key=" + key + "  value=" + value + "\n");
                }
                return request.newBuilder().method(method, newBodyBuilder.build()).build();
            }
        }
        Timber.tag(TAG).d("《《《《《《  读取参数 - ending  》》》》》》");
        return request;
    }

    private String getSign(Map<String, Object> map) {
        TreeMap<String, Object> treeMap = new TreeMap<>(map);
        String salt = UserInfo.getInstance().getSalt();

        //salt不为空 且 参数集合里面不含salt
        if (!TextUtils.isEmpty(salt) && !treeMap.containsKey("salt")) {
            treeMap.put("salt", salt);
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            builder.append(key).append("=").append(value).append("&");
            //builder.append(value + "&");
        }
        builder.delete(builder.length() - 1, builder.length());
        //String sign = builder.toString();
        return md5(builder.toString());
    }

    private String md5(String sign) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(sign.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toLowerCase();
    }

    /**
     * 解析请求参数
     *
     * @param request
     * @return
     */
    private Map<String, Object> parseParams(Request request) {
        //GET POST DELETE PUT PATCH
        String method = request.method();
        Map<String, Object> params = null;
        if ("GET".equals(method)) {
            params = doGet(request);
        } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
            RequestBody body = request.body();
            if (body != null && body instanceof FormBody) {
                params = doForm(request);
            }
        }
        return params;
    }

    /**
     * 获取get方式的请求参数
     *
     * @param request
     * @return
     */
    private static Map<String, Object> doGet(Request request) {
        Map<String, Object> params = null;
        HttpUrl url = request.url();
        Set<String> strings = url.queryParameterNames();
        if (strings != null) {
            Iterator<String> iterator = strings.iterator();
            params = new HashMap<>();
            int i = 0;
            while (iterator.hasNext()) {
                String name = iterator.next();
                String value = url.queryParameterValue(i);
                if (!params.containsKey(name)) {
                    //参数去重
                    params.put(name, value);
                }
                i++;
            }
        }
        return params;
    }

    /**
     * 获取表单的请求参数
     *
     * @param request
     * @return
     */
    private static Map<String, Object> doForm(Request request) {
        Map<String, Object> params = null;
        FormBody body = null;
        try {
            body = (FormBody) request.body();
        } catch (ClassCastException c) {
            c.printStackTrace();
        }
        if (body != null) {
            int size = body.size();
            if (size > 0) {
                params = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    if (!params.containsKey(body.name(i))) {
                        //参数去重
                        params.put(body.name(i), body.value(i));
                    }

                }
            }
        }
        return params;
    }
}
