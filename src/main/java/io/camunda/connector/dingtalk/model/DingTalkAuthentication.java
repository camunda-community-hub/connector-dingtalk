/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.dingtalk.model;

import com.google.common.base.Objects;

public class DingTalkAuthentication {

  private String accessToken;

  private String secretToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getSecretToken() {
    return secretToken;
  }

  public void setSecretToken(String secretToken) {
    this.secretToken = secretToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DingTalkAuthentication that = (DingTalkAuthentication) o;
    return Objects.equal(accessToken, that.accessToken)
        && Objects.equal(secretToken, that.secretToken);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(accessToken, secretToken);
  }
}
