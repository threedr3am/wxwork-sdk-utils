package com.threedr3am.wxwork.support;

import com.threedr3am.wxwork.bean.message.MarkdownMessage;
import com.threedr3am.wxwork.bean.message.TextMessage;
import java.util.List;

public class MessageBuilder {
  public static TextMessage buildTextMessage(String msg, List<String> userIds, List<String> mobiles) {
    return new TextMessage().msg(msg).setUserIdReceiver(userIds).setMobileReceiver(mobiles);
  }

  public static TextMessage buildTextMessage(String msg, boolean forAllUserIds, boolean forAllMobiles) {
    TextMessage textMessage = new TextMessage().msg(msg);
    if (forAllUserIds)
      textMessage.userIdReceiverWithAll();
    if (forAllMobiles)
      textMessage.mobileReceiverWithAll();
    return textMessage;
  }

  public static MarkdownMessage buildMarkdownMessage(String markdown) {
    return new MarkdownMessage().markdown(markdown);
  }
}
