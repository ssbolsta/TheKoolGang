package connection;

/**
 * Created by boye on 16.03.15.
 */
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HTTPConnection {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String BASE_URL;

    private CookieStore cookieStore;
    private HttpContext httpContext;
    private CloseableHttpClient client;


    public HTTPConnection(String URL) {
        BASE_URL = URL;
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        cookieStore = new BasicCookieStore();
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    public String me() throws Exception {
        return sendGet("");
    }

    // HTTP GET
    private String sendGet(String path) throws Exception {

        HttpGet request = new HttpGet(BASE_URL + path);

        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request, httpContext);

        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        return json;
    }

    // HTTP POST
    private String sendPost(String path, HashMap<String, String> param) throws Exception {
        HttpPost post = new HttpPost(BASE_URL + path);

        post.addHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        for(String key : param.keySet()) {
            urlParameters.add(new BasicNameValuePair(key, param.get(key)));
        }

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post, httpContext);

        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        return json;
    }

    // HTTP PUT
    private String sendPut(String path, HashMap<String, String> param) throws Exception {
        HttpPut put = new HttpPut(BASE_URL + path);

        put.addHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        for(String key : param.keySet()) {
            urlParameters.add(new BasicNameValuePair(key, param.get(key)));
        }

        put.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(put, httpContext);

        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        return json;
    }

    // HTTP DELETE
    private String sendDelete(String path) throws Exception {

        HttpDelete request = new HttpDelete(BASE_URL + path);

        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request, httpContext);

        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        return json;
    }

    // LOGIN
    private Boolean login(String username, String password) throws Exception {

        HttpPost post = new HttpPost(BASE_URL + "login");

        post.addHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("username", username));
        urlParameters.add(new BasicNameValuePair("password", password));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post, httpContext);

        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        return (json != "[]");
    }

    // LOGOUT
    private void logout() throws Exception {
        sendGet("logout");
    }

}