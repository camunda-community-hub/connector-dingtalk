/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.dingtalk.model;

public class DingTalkResponse {

  private Integer errcode;

  private String errmsg;

  public DingTalkResponse(Integer errcode, String errmsg) {
    this.errcode = errcode;
    this.errmsg = errmsg;
  }

  public Integer getErrcode() {
    return errcode;
  }

  public void setErrcode(Integer errcode) {
    this.errcode = errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  @Override
  public String toString() {

    return "DingTalkResponse{" + "errcode=" + errcode + ", errmsg='" + errmsg + '\'' + '}';
  }
}
