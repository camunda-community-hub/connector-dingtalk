/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.dingtalk;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.dingtalk.client.DingTalkRobotClient;
import io.camunda.connector.dingtalk.model.DingTalkRequest;
import io.camunda.connector.dingtalk.model.DingTalkResponse;
import io.camunda.connector.dingtalk.model.MessageType;
import io.camunda.connector.dingtalk.suppliers.GsonSupplier;
import java.util.Objects;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(
    name = "DINGTALK_ROBOT",
    inputVariables = {"authentication", "messageType", "title", "content"},
    type = "io.camunda:dingtalk-robot:1")
public class DingTalkRobotConnectorFunction implements OutboundConnectorFunction {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(DingTalkRobotConnectorFunction.class);

  @Override
  public Object execute(final OutboundConnectorContext context) {
    final var variables = context.getVariables();
    LOGGER.info("Executing DingTalk connector with variables : {}", variables);

    var request = context.getVariablesAsType(DingTalkRequest.class);
    context.validate(request);
    context.replaceSecrets(request);

    final var res = executeConnector(request);
    System.out.println(res);
    return res;
  }

  private static DingTalkResponse executeConnector(final DingTalkRequest request) {
    final var authentication = request.getAuthentication();
    final var client =
        new DingTalkRobotClient(authentication.getAccessToken(), authentication.getSecretToken());

    String content = StringEscapeUtils.unescapeJava(request.getContent());
    if (MessageType.TEXT.equals(request.getMessageType())) {
      System.out.println(GsonSupplier.gsonInstance().toJson(request));
      if (request.isAtAll()) {
        return client.sendTextMessage(content, true);
      } else if (Objects.nonNull(request.getAtMobiles())) {
        return client.sendTextMessage(content, request.getAtMobiles());
      } else {
        return client.sendTextMessage(content);
      }
    } else {
      String title = StringEscapeUtils.unescapeJava(request.getTitle());
      if (request.isAtAll()) {
        return client.sendMarkdownMessage(title, content, true);
      } else if (Objects.nonNull(request.getAtMobiles())) {
        return client.sendMarkdownMessage(title, content, request.getAtMobiles());
      } else {
        return client.sendMarkdownMessage(title, content);
      }
    }
  }
}
