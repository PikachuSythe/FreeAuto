/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sythe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Clinton
 */
public class PostRequests {

    private String TYPE = "application/x-www-form-urlencoded";
    private String USER_AGENT = "Mozilla/5.0";
    private HttpClient client;
    private HttpGet Get;
    private HttpPost post;
    private HttpResponse response;
    private String Username = "";
    private String Password = "";
    private String twoFA = "";
    public String XfToken = "";

    public PostRequests(String Username, String Password, String twoFA) {
        this.Username = Username;
        this.Password = Password;
        this.twoFA = twoFA;
    }

    void Login() throws UnsupportedEncodingException, IOException {
        try {
            String url = "https://www.sythe.org/login/login?";
            client = HttpClientBuilder.create().build();
            post = new HttpPost(url);
            // add header
            post.setHeader("User-Agent", USER_AGENT);
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("login", Username));
            urlParameters.add(new BasicNameValuePair("password", Password));
            //Add fuucations.
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            response = client.execute(post);
            System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine());
        } finally {
//            post.releaseConnection();
        }
    }

    void twofa() throws UnsupportedEncodingException, IOException {
        try {
            String url = "https://www.sythe.org/login/two-step?";
            post = new HttpPost(url);
            // add header
            post.setHeader("User-Agent", USER_AGENT);
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

            urlParameters.add(new BasicNameValuePair("code", twoFA));
            urlParameters.add(new BasicNameValuePair("trust", "1"));
            urlParameters.add(new BasicNameValuePair("provider", "totp_blackbox"));
            urlParameters.add(new BasicNameValuePair("_xfConfirm", "1"));
            urlParameters.add(new BasicNameValuePair("_xfToken", "1"));
            urlParameters.add(new BasicNameValuePair("remember", "1"));
            urlParameters.add(new BasicNameValuePair("save", "Confirm"));
            urlParameters.add(new BasicNameValuePair("_xfRequestUri", "/login/two-step?redirect=https%3A%2F%2Fwww.sythe.org%2F&remember=1"));
            urlParameters.add(new BasicNameValuePair("_xfNoRedirect", "1"));
            //Add fuucations.

            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            response = client.execute(post);
            System.out.println(response.getStatusLine());
        } finally {
//            post.releaseConnection();
        }
    }

    public void bump(String thread, String message) throws UnsupportedEncodingException, IOException {
        try {
            String url = "https://www.sythe.org/threads/" + thread + "/add-reply/";
            post = new HttpPost(url);
            System.out.println("\n ***** PostRequest:" + url + "***** \n");
            // add header
            printMessage();
            print(txt);
            post.setHeader("User-Agent", USER_AGENT);
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("message_html", "<p>" + message + "</p>"));
            urlParameters.add(new BasicNameValuePair("_xfToken", XfToken));

            //Add fuucations.
            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            System.out.println("Post about to excute");
            response = client.execute(post);
            System.out.println(response.getStatusLine());
            System.out.println("\n ****************************** \n");
            printMessage();
        } finally {
            post.releaseConnection();
        }
    }

    private String txt = null;

    void printMessage() throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
//            System.out.println(line);
            if (line.contains("_csrfToken:")) {
                txt = line;
//                System.out.println(txt);
            }
        }
    }

    void print(String L) {
        String[] parts = L.split(" ");
        String removeLastLetters = (parts[1].substring(1, parts[1].length() - 2));
        System.out.println("Token: " + removeLastLetters);
        XfToken = removeLastLetters;
    }

    public void refeshToken() throws IOException {
        String url = "https://www.sythe.org/login/two-step?";
        Get = new HttpGet(url);
        printMessage();
        print(ReturncsrfToken());
        Get.abort();
    }

    public void releaseConnection() {
        post.releaseConnection();
    }

    public String Token() {
        return XfToken;
    }

    public String ReturncsrfToken() {
        return txt;
    }
}