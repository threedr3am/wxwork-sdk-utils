package com.threedr3am.wxwork.support;

import com.threedr3am.wxwork.bean.robot.MessageBase;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 机器人消息推送
 */
public class RobotMagPush {

  public static RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
  public static HttpClientBuilder httpClientBuilder = new HttpClient();

  static class HttpClient extends HttpClientBuilder {}

  static {
    requestConfigBuilder.setConnectTimeout(5000);
    requestConfigBuilder.setSocketTimeout(5000);
    requestConfigBuilder.setConnectionRequestTimeout(5000);
    requestConfigBuilder.setRedirectsEnabled(false);

    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
        .<ConnectionSocketFactory>create()
        .register("http", PlainConnectionSocketFactory.INSTANCE)
        .register("https", new SSLs().getSSLConnectionSocketFactory(SSLs.SSLProtocolVersion.SSLv3)).build();
    //设置连接池大小
    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
    connManager.setMaxTotal(20);// Increase max total connection to $maxTotal
    connManager.setDefaultMaxPerRoute(10);// Increase default max connection per route to $defaultMaxPerRoute
    //connManager.setMaxPerRoute(route, max);// Increase max connections for $route(eg：localhost:80) to 50
    httpClientBuilder.setConnectionManager(connManager);

    // 请求重试处理
    HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
      public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        if (executionCount >= 3) {// 如果已经重试了n次，就放弃
          return false;
        }
        if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
          return true;
        }
        if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
          return false;
        }
        if (exception instanceof InterruptedIOException) {// 超时
          //return false;
          return false;
        }
        if (exception instanceof UnknownHostException) {// 目标服务器不可达
          return true;
        }
        if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
          return false;
        }
        if (exception instanceof SSLException) {// SSL握手异常
          return false;
        }

        HttpClientContext clientContext = HttpClientContext.adapt(context);
        HttpRequest request = clientContext.getRequest();
        // 如果请求是幂等的，就再次尝试
        if (!(request instanceof HttpEntityEnclosingRequest)) {
          return true;
        }
        return false;
      }
    };
    httpClientBuilder.setRetryHandler(httpRequestRetryHandler);
  }

  /**
   * 消息推送
   * @param webhook
   * @param message
   * @return
   */
  public static String push(String webhook, MessageBase message) {
    HttpEntityEnclosingRequestBase httpBase = new HttpPost(webhook);
    EntityBuilder entityBuilder = EntityBuilder.create();
    try {
      entityBuilder.setBinary(message.build().getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    entityBuilder.setContentEncoding("UTF-8");
    entityBuilder.setContentType(ContentType.APPLICATION_JSON);
    httpBase.setEntity(entityBuilder.build());
    //创建请求对象
    HttpRequestBase request = httpBase;
    request.setConfig(requestConfigBuilder.build());
    CloseableHttpClient client = httpClientBuilder.build();

    try {
      return EntityUtils.toString(client.execute(request).getEntity());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
