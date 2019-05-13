package com.threedr3am.wxwork.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContexts;

/**
 * 设置ssl
 */
public class SSLs {

    private static final SimpleVerifier simpleVerifier = new SimpleVerifier();
    private static SSLSocketFactory sslSocketFactory;
    private static SSLConnectionSocketFactory sslConnectionSocketFactory;
    private static SSLIOSessionStrategy sslIOSessionStrategy;
    private static SSLs sslutil = new SSLs();
    private SSLContext sslContext;

    public static SSLs getInstance() {
        return sslutil;
    }

    public static SSLs custom() {
        return new SSLs();
    }

    /**
     * 重写X509TrustManager类的三个方法,信任服务器证书
     */
    private static class SimpleVerifier implements X509TrustManager, HostnameVerifier {

        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }

        public boolean verify(String paramString, SSLSession paramSSLSession) {
            return true;
        }
    }

    /**
     * 信任主机
     */
    public static HostnameVerifier getHostnameVerifier() {
        return simpleVerifier;
    }

    public synchronized SSLSocketFactory getSSLSocketFactory(SSLProtocolVersion sslProtocolVersion) {
        if (sslSocketFactory != null)
            return sslSocketFactory;
        try {
            SSLContext sc = getSSLContext(sslProtocolVersion);
            sc.init(null, new TrustManager[]{simpleVerifier}, null);
            sslSocketFactory = sc.getSocketFactory();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    public synchronized SSLConnectionSocketFactory getSSLConnectionSocketFactory(SSLProtocolVersion sslpv) {
        if (sslConnectionSocketFactory != null)
            return sslConnectionSocketFactory;
        try {
            SSLContext sc = getSSLContext(sslpv);
            sc.init(null, new TrustManager[]{simpleVerifier}, new java.security.SecureRandom());
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(sc, simpleVerifier);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslConnectionSocketFactory;
    }

    public synchronized SSLIOSessionStrategy getSSLIOSessionStrategy(SSLProtocolVersion sslProtocolVersion) {
        if (sslIOSessionStrategy != null)
            return sslIOSessionStrategy;
        try {
            SSLContext sslContext = getSSLContext(sslProtocolVersion);
            sslContext.init(null, new TrustManager[]{simpleVerifier}, new java.security.SecureRandom());
            sslIOSessionStrategy = new SSLIOSessionStrategy(sslContext, simpleVerifier);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslIOSessionStrategy;
    }

    public SSLs customSSL(String keyStorePath, String keyStorepass) {
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(keyStorePath));
            trustStore.load(instream, keyStorepass.toCharArray());
            // 相信自己的CA和所有自签名的证书
            sslContext = SSLContexts
                .custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
            }
        }
        return this;
    }

    public SSLContext getSSLContext(SSLProtocolVersion sslpv) {
        if (sslContext == null) {
            try {
                sslContext = SSLContext.getInstance(sslpv.getName());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return sslContext;
    }

    /**
     * The SSL protocol version (SSLv3, TLSv1, TLSv1.1, TLSv1.2)
     *
     * @author arron
     * @version 1.0
     */
    public enum SSLProtocolVersion {
        SSL("SSL"),
        SSLv3("SSLv3"),
        TLSv1("TLSv1"),
        TLSv1_1("TLSv1.1"),
        TLSv1_2("TLSv1.2"),;
        private String name;

        SSLProtocolVersion(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static SSLProtocolVersion find(String name) {
            for (SSLProtocolVersion pv : SSLProtocolVersion.values()) {
                if (pv.getName().toUpperCase().equals(name.toUpperCase())) {
                    return pv;
                }
            }
            throw new RuntimeException("未支持当前ssl版本号：" + name);
        }

    }
}