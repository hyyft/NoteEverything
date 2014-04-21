package com.hyyft.noteeverything.modal;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
public class CustomerHttpClient {
    private static HttpClient customerHttpClient;

    private CustomerHttpClient() {
    }

    public static synchronized HttpClient getHttpClient() {
        if (null== customerHttpClient) {
            HttpParams params =new BasicHttpParams();
            // 超时设置
/* 从连接池中取连接的超时时间 */
            ConnManagerParams.setTimeout(params, 5000);
            /* 连接超时 */
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            /* 请求超时 */
            HttpConnectionParams.setSoTimeout(params, 10000);
            
            // 设置我们的HttpClient支持HTTP和HTTPS两种模式
            SchemeRegistry schReg =new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schReg.register(new Scheme("https", org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory(), 443));

            // 使用线程安全的连接管理来创建HttpClient
            ClientConnectionManager conMgr =new ThreadSafeClientConnManager(
                    params, schReg);
            customerHttpClient =new DefaultHttpClient(conMgr, params);
        }
        return customerHttpClient;
    }
}
