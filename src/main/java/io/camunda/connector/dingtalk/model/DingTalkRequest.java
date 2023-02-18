/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.dingtalk.model;

import io.camunda.connector.api.annotation.Secret;
import javax.validation.Valid;

public class DingTalkRequest {

  @Valid @Secret private DingTalkAuthentication authentication;

  private MessageType messageType;

  private String title;

  private String content;

  private String[] atMobiles;

  private boolean atAll;

  public MessageType getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public DingTalkAuthentication getAuthentication() {
    return authentication;
  }

  public void setAuthentication(DingTalkAuthentication authentication) {
    this.authentication = authentication;
  }

  public String[] getAtMobiles() {
    return atMobiles;
  }

  public void setAtMobiles(String[] atMobiles) {
    this.atMobiles = atMobiles;
  }

  public boolean isAtAll() {
    return atAll;
  }

  public void setAtAll(boolean atAll) {
    this.atAll = atAll;
  }
}
