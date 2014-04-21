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
            // ��ʱ����
/* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
            ConnManagerParams.setTimeout(params, 5000);
            /* ���ӳ�ʱ */
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            /* ����ʱ */
            HttpConnectionParams.setSoTimeout(params, 10000);
            
            // �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
            SchemeRegistry schReg =new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schReg.register(new Scheme("https", org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory(), 443));

            // ʹ���̰߳�ȫ�����ӹ���������HttpClient
            ClientConnectionManager conMgr =new ThreadSafeClientConnManager(
                    params, schReg);
            customerHttpClient =new DefaultHttpClient(conMgr, params);
        }
        return customerHttpClient;
    }
}
