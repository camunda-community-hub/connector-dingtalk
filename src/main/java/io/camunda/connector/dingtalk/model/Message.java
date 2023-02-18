/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.dingtalk.model;

import java.io.Serializable;
import java.util.Map;

public abstract class Message implements Serializable {

  protected String[] atMobiles;

  protected boolean isAtAll;

  public String[] getAtMobiles() {
    return atMobiles;
  }

  public void setAtMobiles(String[] atMobiles) {
    this.atMobiles = atMobiles;
  }

  public boolean getIsAtAll() {
    return isAtAll;
  }

  public void setIsAtAll(boolean isAtAll) {
    isAtAll = isAtAll;
  }

  public abstract Map<String, Object> toMap();
}
