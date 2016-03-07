package com.cubicl.buzzclicker;

import android.util.Log;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingyi on 3/6/16.
 */
public class ServerRequest {
    HttpClient httpClient;
    String address = "http://143.215.89.191";
    String localhost = ":8081/mlogin";
    public boolean login(String user, String pass) throws IOException {
        BufferedReader reader = null;
        HttpURLConnection con = null;
        String value;
        try {
            String url = address + localhost;
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", user));
            params.add(new BasicNameValuePair("password", pass));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // writing error to Log
                e.printStackTrace();
            }
            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity respEntity = response.getEntity();

                if (respEntity != null) {
                    InputStream instream = respEntity.getContent();
                    // EntityUtils to get the response content
                    reader = new BufferedReader(new InputStreamReader(instream));
                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        return false;
                    }
                    value = buffer.toString();
                    System.out.print(value);
                    return true;
                }
            } catch (ClientProtocolException e) {
                // writing exception to log
                e.printStackTrace();
            } catch (IOException e) {
                // writing exception to log
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
