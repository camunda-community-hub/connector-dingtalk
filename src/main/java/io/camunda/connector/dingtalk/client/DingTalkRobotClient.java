/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.dingtalk.client;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.dingtalk.model.DingTalkResponse;
import io.camunda.connector.dingtalk.model.MarkdownMessage;
import io.camunda.connector.dingtalk.model.Message;
import io.camunda.connector.dingtalk.model.TextMessage;
import io.camunda.connector.dingtalk.suppliers.GsonSupplier;
import io.camunda.connector.dingtalk.suppliers.HttpTransportSupplier;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DingTalkRobotClient {
  private static final Logger log = LoggerFactory.getLogger(DingTalkRobotClient.class);

  private final HttpRequestFactory requestFactory;
  private final GsonFactory gsonFactory;
  private final Gson gson;

  private final String accessToken;

  private final String secretToken;

  public DingTalkRobotClient(String accessToken, String secretToken) {
    this.accessToken = accessToken;
    this.secretToken = secretToken;
    this.requestFactory = HttpTransportSupplier.httpRequestFactoryInstance();
    this.gsonFactory = GsonSupplier.gsonFactoryInstance();
    this.gson = GsonSupplier.gsonInstance();
  }

  public DingTalkResponse sendMessage(String url, Message message) {
    URI actualUrl;
    try {
      actualUrl = new URI(url);
    } catch (URISyntaxException e) {
      throw new RuntimeException("Failed to create URI！");
    }
    return sendMessage(actualUrl, message);
  }

  public DingTalkResponse sendMessage(URI url, Message message) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(APPLICATION_JSON.getMimeType());

    final GenericUrl genericUrl = new GenericUrl(url);
    final HttpContent httpContent = new JsonHttpContent(gsonFactory, message.toMap());

    DingTalkResponse dingTalkResponse;

    try {
      final var httpRequest = requestFactory.buildRequest("POST", genericUrl, httpContent);
      final var httpResponse = httpRequest.execute();

      try (InputStream content = httpResponse.getContent();
          Reader reader = new InputStreamReader(content)) {
        dingTalkResponse = gson.fromJson(reader, DingTalkResponse.class);
      }
    } catch (Exception e) {
      log.error(e.fillInStackTrace().toString());
      throw new ConnectorException("Failed to parse result: " + e.getMessage(), e);
    }

    return dingTalkResponse;
  }

  public DingTalkResponse sendTextMessage(TextMessage message) {
    return this.sendMessage(message);
  }

  public DingTalkResponse sendTextMessage(String content) {
    return this.sendMessage(new TextMessage(content));
  }

  public DingTalkResponse sendTextMessage(String content, String[] atMobiles) {
    return this.sendMessage(new TextMessage(content, atMobiles));
  }

  public DingTalkResponse sendTextMessage(String content, boolean isAtAll) {
    return this.sendMessage(new TextMessage(content, isAtAll));
  }

  public DingTalkResponse sendMarkdownMessage(MarkdownMessage message) {
    return this.sendMessage(message);
  }

  public DingTalkResponse sendMarkdownMessage(String title, String text) {
    return this.sendMessage(new MarkdownMessage(title, text));
  }

  public DingTalkResponse sendMarkdownMessage(String title, String text, String[] atMobiles) {
    return this.sendMessage(new MarkdownMessage(title, text, atMobiles));
  }

  public DingTalkResponse sendMarkdownMessage(String title, String text, boolean isAtAll) {
    return this.sendMessage(new MarkdownMessage(title, text, isAtAll));
  }

  public DingTalkResponse sendMessage(Message message) {
    return this.sendMessage(getWebhook(accessToken, secretToken), message);
  }

  public String getWebhook(String accessToken, String secretToken) {
    String urlPrefix = "https://oapi.dingtalk.com/robot/send";

    String url = urlPrefix + "?access_token=" + accessToken;
    Long timestamp = System.currentTimeMillis();
    url += "&timestamp=" + timestamp + "&sign=" + sign(secretToken, timestamp);
    return url;
  }

  private static String sign(String secret, Long timestamp) {
    try {
      final String stringToSign = timestamp + "\n" + secret;
      final String algorithm = "HmacSHA256";
      Mac mac = Mac.getInstance(algorithm);
      mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), algorithm));
      byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
      String sign =
          URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8);
      return sign;
    } catch (Exception e) {
      throw new ConnectorException("-1", "Failed to calculate signature", e);
    }
  }
}
