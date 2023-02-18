/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.dingtalk.model;

import java.util.HashMap;
import java.util.Map;

public class MarkdownMessage extends Message {

  private String text;

  private String title;

  public MarkdownMessage() {}

  public MarkdownMessage(String title, String text) {
    this.text = text;
    this.title = title;
  }

  public MarkdownMessage(String title, String text, String[] atMobiles) {
    this.text = text;
    this.title = title;
    this.atMobiles = atMobiles;
  }

  public MarkdownMessage(String title, String text, boolean isAtAll) {
    this.text = text;
    this.title = title;
    this.isAtAll = isAtAll;
  }

  @Override
  public Map<String, Object> toMap() {

    Map<String, Object> resultMap = new HashMap<>(8);
    resultMap.put("msgtype", "markdown");

    HashMap<String, String> markdownItems = new HashMap<>(8);
    markdownItems.put("title", this.title);
    markdownItems.put("text", this.text);
    resultMap.put("markdown", markdownItems);

    HashMap<String, Object> atItems = new HashMap<>(8);
    atItems.put("atMobiles", this.atMobiles);
    atItems.put("isAtAll", this.isAtAll);
    resultMap.put("at", atItems);

    return resultMap;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
