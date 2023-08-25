package com.sirmabc.bulkpayments.util;

import com.sirmabc.bulkpayments.ssl.test.CustomKeyManager;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class HttpClientTest {

    private static String KEYSTOREPATH = "/opt/tomcat/conf/tomcat.jks";
    private static String KEYSTOREPASS = "sla6945";

    private static String KEYPASS = "sla6945";



    private static KeyStore readStore() throws Exception {
        try (InputStream keyStoreStream = new FileInputStream(KEYSTOREPATH)) {
            KeyStore keyStore = KeyStore.getInstance("JKS"); // or "PKCS12"
            keyStore.load(keyStoreStream, KEYSTOREPASS.toCharArray());
            return keyStore;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static SSLContext createContext() throws Exception {


        KeyStore keystore = readStore();

        SSLContext ret = SSLContext.getInstance("TLSv1.2");

        ret.init(new KeyManager[] {new CustomKeyManager(keystore, "10nssl", "sla6945")} , new TrustManager[] {new com.sirmabc.bulkpayments.ssl.test.TrustManager()}, null);

        return ret;
    }



    public static String sendData() throws Exception {

        String urlOverHttps = "https://193.41.190.188/pe/bulk/Message";

        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(60))
                .build();

        RequestConfig config = RequestConfig.custom()
                .setConnectionKeepAlive(TimeValue.ofSeconds(60))
                .setConnectionRequestTimeout(Timeout.ofSeconds(60))
                .setResponseTimeout(Timeout.ofSeconds(60))
                .build();

        SSLContext sslcontext = createContext();

        SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                .setSslContext(sslcontext)
                .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .evictExpiredConnections()
                .setDefaultRequestConfig(config)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpclient);


        // spring part
        RestTemplate template = new RestTemplate(requestFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-MONTRAN-RTP-Channel","TEPJBGS0");
        headers.add("X-MONTRAN-RTP-Version", "1");
        //headers.setConnection("close");

        HttpEntity<String> requestEntity =
                new HttpEntity<>(headers);


        ResponseEntity<String> response = template.exchange(urlOverHttps,
                HttpMethod.GET, requestEntity, String.class);


        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        return response.getBody();
    }

}
