package me.jessyan.peach.shop.netconfig;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * author: Create by HuiRan on 2018/12/8 下午7:21
 * email: 15260828327@163.com
 * description:
 */
public class CompatHttps {
    public static X509TrustManager trustAllCert = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    };

    public static SSLSocketFactory getSSLSocketFactory() {
        return new SSLSocketFactoryCompat(trustAllCert);
    }
}
