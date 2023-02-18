/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.dingtalk.model;

import java.util.HashMap;
import java.util.Map;

public class TextMessage extends Message {

  private String content;

  public TextMessage() {}

  public TextMessage(String content) {
    this.content = content;
  }

  public TextMessage(String content, String[] atMobiles) {
    this.content = content;
    this.atMobiles = atMobiles;
  }

  public TextMessage(String content, boolean isAtAll) {
    this.content = content;
    this.isAtAll = isAtAll;
  }

  @Override
  public Map<String, Object> toMap() {

    HashMap<String, Object> resultMap = new HashMap<>(8);
    resultMap.put("msgtype", "text");

    HashMap<String, String> textItems = new HashMap<>(8);
    textItems.put("content", this.content);
    resultMap.put("text", textItems);

    HashMap<String, Object> atItems = new HashMap<>(8);
    atItems.put("atMobiles", this.atMobiles);
    atItems.put("isAtAll", this.isAtAll);
    resultMap.put("at", atItems);

    return resultMap;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
