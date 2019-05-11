package com.threedr3am.wxwork.bean.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threedr3am.wxwork.bean.MessageBase;
import com.threedr3am.wxwork.enums.MsgType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 普通文本消息
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextMessage extends MessageBase {

  @JsonProperty("msgtype")
  private String msgType = MsgType.TEXT.getType();

  private Text text = new Text();

  @Getter
  public static class Text {

    private String content;

    @JsonProperty("mentioned_list")
    private List<String> mentionedList;

    @JsonProperty("mentioned_mobile_list")
    private List<String> mentionedMobileList;

    private Text() {
    }

  }

  @Override
  public void preHandle() {
    assert (this.text.content != null);
  }

  public TextMessage msg(String msg) {
    this.text.content = msg;
    return this;
  }

  public TextMessage setMobileReceiver(List<String> mobiles) {
    assert (mobiles != null && !mobiles.isEmpty());
    if (this.text.mentionedMobileList == null) {
      synchronized (this.text) {
        if (this.text.mentionedMobileList == null) {
          this.text.mentionedMobileList = mobiles;
        }
      }
    }
    return this;
  }

  public TextMessage addMobileReceiver(String mobile) {
    assert (mobile != null && mobile.length() > 0);
    if (this.text.mentionedMobileList == null) {
      synchronized (this.text) {
        if (this.text.mentionedMobileList == null) {
          this.text.mentionedMobileList = new ArrayList<>();
        }
      }
    }
    this.text.mentionedMobileList.add(mobile);
    return this;
  }

  public TextMessage mobileReceiverWithAll() {
    if (this.text.mentionedMobileList == null) {
      synchronized (this.text) {
        if (this.text.mentionedMobileList == null) {
          this.text.mentionedMobileList = new ArrayList<>();
        }
      }
    }
    this.text.mentionedMobileList.add("@all");
    return this;
  }

  public TextMessage setUserIdReceiver(List<String> userIds) {
    assert (userIds != null && !userIds.isEmpty());
    if (this.text.mentionedList == null) {
      synchronized (this.text) {
        if (this.text.mentionedList == null) {
          this.text.mentionedList = userIds;
        }
      }
    }
    return this;
  }

  public TextMessage addUserIdReceiver(String userId) {
    assert (userId != null && userId.length() > 0);
    if (this.text.mentionedList == null) {
      synchronized (this.text) {
        if (this.text.mentionedList == null) {
          this.text.mentionedList = new ArrayList<>();
        }
      }
    }
    this.text.mentionedList.add(userId);
    return this;
  }

  public TextMessage userIdReceiverWithAll() {
    if (this.text.mentionedList == null) {
      synchronized (this.text) {
        if (this.text.mentionedList == null) {
          this.text.mentionedList = new ArrayList<>();
        }
      }
    }
    this.text.mentionedList.add("@all");
    return this;
  }


}
