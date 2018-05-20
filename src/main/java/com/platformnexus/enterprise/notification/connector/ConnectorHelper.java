package com.platformnexus.enterprise.notification.connector;


import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ConnectorHelper {

    /***
     * Generic method for generate HttpEntry object for HTTP request.
     *
     * @param body
     * @param <T>
     * @return HttpEntity<T>
     */
    public static <T> HttpEntity<T> createStandardRequest(T body, String userName, String password) {
        // TODO: This *must* be removed before the production go-live of the Insurance
        // Service.
        nonProductionTrustSSL();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        addBasicAuthCredentials(headers, userName, password);

        HttpEntity<T> request = new HttpEntity<T>(body, headers);

        return request;
    }

    /**
     * This method causes the current HTTPS connection to accept ANY certificate,
     * without any validation. This code is only safe for non-production environments.
     */
    private static void nonProductionTrustSSL() {
        // HIPAA/TODO: We cannot go to production while trusting self-signed SSL, and verifying
        // all SSL sessions. We will need to generate valid certs for the Platform Search service and
        // for the Insurance Service before we can go live.
        //
        // THIS CODE MUST BE REMOVED — OR MADE TO BE CONDITIONAL TO STAGING
        // ENVIRONMENTS — BEFORE WE GO TO PRODUCTION.
        ConnectorHelper.trustSelfSignedSSL();
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    /**
     * This function adds the autowired Basic Authentication credentials to the given
     * HttpHeaders
     *
     * @param headers Headers to add the basic authorization headers to. Any values
     *                that are currently stored in the "Authorization" will be overwritten.
     */
    private static void addBasicAuthCredentials(HttpHeaders headers, String userName, String password) {
        String plainCreds = userName + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers.add("Authorization", "Basic " + base64Creds);
    }

    private static void trustSelfSignedSSL() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLContext.setDefault(ctx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /***
     * Generic method for generate HttpEntry object for HTTP request.
     *
     * @param userName
     * @param password
     * @return HttpEntity<T>
     */
    public static HttpEntity getHttpEntity(String userName, String password) {
        return new HttpEntity(generateHeaders(userName, password));
    }


    /***
     * Generic method for generate HttpEntry object for HTTP request.
     *
     * @param body
     * @param userName
     * @param password
     * @return HttpEntity<T>
     */
    public static <T> HttpEntity<T> getHttpEntity(T body, String userName, String password) {
        return new HttpEntity(body, generateHeaders(userName, password));
    }

    /***
     * Generic method for generate HttpEntry object for HTTP request.
     *
     * @param body
     * @param userName
     * @param password
     * @return HttpEntity<T>
     */
    public static <T> HttpEntity<T> getNonJsonHttpEntity(T body, String userName, String password) {
        return new HttpEntity(body, generateNonJsonHeaders(userName, password));
    }

    /***
     * Generic method for generate HttpEntry object for HTTP request.
     *
     * @param body
     * @return HttpEntity<T>
     */
    public static <T> HttpEntity<T> getHttpEntity(T body) {
        return new HttpEntity(body, generateJsonHeaders());
    }

    /***
     * Generic method for generate HttpEntry object for HTTP request.
     *
     * @param body
     * @return HttpEntity<T>
     */
    public static <T> HttpEntity<T> getNonJsonHttpEntity(T body) {
        return new HttpEntity(body);
    }

    private static HttpHeaders generateJsonHeaders() {
        ConnectorHelper.trustSelfSignedSSL();
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /***
     * Generic method for generate HttpHeaders object for HTTP request.
     *
     * @param userName
     * @param password
     * @return HttpHeaders
     */
    private static HttpHeaders generateHeaders(String userName, String password) {
        ConnectorHelper.trustSelfSignedSSL();
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        HttpHeaders headers = new HttpHeaders();
        String plainCreds = userName + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers.add("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /***
     * Generic method for generate HttpHeaders object for HTTP request.
     *
     * @param userName
     * @param password
     * @return HttpHeaders
     */
    private static HttpHeaders generateNonJsonHeaders(String userName, String password) {
        ConnectorHelper.trustSelfSignedSSL();
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        HttpHeaders headers = new HttpHeaders();
        String plainCreds = userName + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
    }
}
