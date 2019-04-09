package com.lym.util.weixin;

import javax.net.ssl.TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @ClassName MyX509TrustManager
 * @Description TODO
 * @Author lyming
 * @Date 2019/3/25 10:09 PM
 **/
public class MyX509TrustManager implements TrustManager {

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
