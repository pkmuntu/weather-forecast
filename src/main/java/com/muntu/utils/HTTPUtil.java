package com.muntu.utils;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HTTPUtil {

    public static String connection(String pUrl, String pPayload, String pMethodType, Map<String, String> pHeaders) throws IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
        String response;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(pUrl))
                .header("X-RapidAPI-Key", pHeaders.get("X-RapidAPI-Key"))
                .header("X-RapidAPI-Host", pHeaders.get("X-RapidAPI-Host"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> responseBody = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        response=responseBody.body();
        return response;
    }




        public static String connection1(String pUrl, String pPayload, String pMethodType, Map<String, String> pHeaders) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        StringBuilder response = new StringBuilder();

        // Setup the TrustManager to trust all certificates
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Initialize the SSLContext with the TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Set the default SSL socket factory to use our SSLContext
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        //open connection
        URL url = new URL(pUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // Ensure we use HttpsURLConnection if the protocol is https
        if (httpURLConnection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) httpURLConnection).setHostnameVerifier((hostname, session) -> true);
        }
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
            // Set timeouts
        httpURLConnection.setConnectTimeout(20000); // 20 seconds
        httpURLConnection.setReadTimeout(20000); // 20 seconds
        // Setting MethodType
        httpURLConnection.setRequestMethod(pMethodType);
        // Add request headers
        for (Map.Entry<String, String> header : pHeaders.entrySet()) {
            httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
        }
        //Adding request Body
        if ("POST".equalsIgnoreCase(pMethodType) || "PUT".equalsIgnoreCase(pMethodType)) {
            httpURLConnection.setDoOutput(true);
            if (pPayload != null) {
                try (OutputStream os = httpURLConnection.getOutputStream()) {
                    byte[] input = pPayload.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }
        }
        //Getting response code
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        //For no Error
        if (responseCode == HttpURLConnection.HTTP_OK ||
                responseCode == HttpURLConnection.HTTP_CREATED ||
                responseCode == HttpURLConnection.HTTP_ACCEPTED) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        }else{
            //For Error
            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()))) {
                String inputLine;
                response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                throw  new RuntimeException(response.toString());
            }
        }
        return response.toString();
    }
}
