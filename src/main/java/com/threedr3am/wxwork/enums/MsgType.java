package com.threedr3am.wxwork.enums;

public enum MsgType {
  TEXT("text","文本消息"),
  MD("markdown","markdown"),
  IMAGE("image","图片消息"),
  NEWS("news","图文"),
  ;

  private String type;
  private String desc;

  MsgType(String type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public String getType() {
    return type;
  }
}
