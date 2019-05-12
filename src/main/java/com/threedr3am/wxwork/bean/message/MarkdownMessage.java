package com.threedr3am.wxwork.bean.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threedr3am.wxwork.bean.MessageBase;
import com.threedr3am.wxwork.enums.MsgType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * markdown消息
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarkdownMessage extends MessageBase {

  @JsonProperty("msgtype")
  private String msgType = MsgType.MD.getType();

  private Markdown markdown = new Markdown();


  @Getter
  public static class Markdown {

    private String content;

    @JsonIgnore
    private StringBuilder tmp = new StringBuilder();

    private Markdown() {

    }

  }

  @Override
  protected void preHandle() {
    assert (this.markdown.content != null || this.markdown.tmp.length() > 0);
    if (this.markdown.tmp.length() > 0) {
      this.markdown.content = this.markdown.tmp.toString();
    }
  }

  public MarkdownMessage markdown(String markdown) {
    this.markdown.content = markdown;
    return this;
  }

  /**
   * 换行
   * @return
   */
  public MarkdownMessage newLine() {
    this.markdown.tmp.append("\n");
    return this;
  }

  /**
   * 自定义内容
   * @param normal
   * @return
   */
  public MarkdownMessage appendNormal(String normal) {
    this.markdown.tmp.append(normal);
    return this;
  }

  /**
   * 1-6级标题追加
   * @param title
   * @param level
   * @return
   */
  public MarkdownMessage appendLevelTitle(String title, int level) {
    assert (level > 0 && level < 7);
    for (int i = 0; i < level; i++) {
      this.markdown.tmp.append("#");
    }
    this.markdown.tmp.append(" ".concat(title));
    return this;
  }

  /**
   * 加粗
   * @param bold
   * @return
   */
  public MarkdownMessage appendBold(String bold) {
    this.markdown.tmp.append("**".concat(bold).concat("**"));
    return this;
  }

  /**
   * 链接
   * @param url
   * @param text
   * @return
   */
  public MarkdownMessage appendHref(String url, String text) {
    this.markdown.tmp.append("[".concat(text).concat("](").concat(url).concat(")"));
    return this;
  }

  /**
   * 行内代码
   * @param code
   * @return
   */
  public MarkdownMessage appendLineCode(String code) {
    this.markdown.tmp.append("`".concat(code).concat("`"));
    return this;
  }

  /**
   * 引用
   * @param reference
   * @return
   */
  public MarkdownMessage appendReference(String reference) {
    this.markdown.tmp.append("> ".concat(reference));
    return this;
  }

  /**
   * 绿色文字
   * @param text
   * @return
   */
  public MarkdownMessage appendInfoMsg(String text) {
    this.markdown.tmp.append("<font color=\"info\">".concat(text).concat("</font>"));
    return this;
  }

  /**
   * 灰色文字
   * @param text
   * @return
   */
  public MarkdownMessage appendCommentMsg(String text) {
    this.markdown.tmp.append("<font color=\"comment\">".concat(text).concat("</font>"));
    return this;
  }

  /**
   * 橙红色文字
   * @param text
   * @return
   */
  public MarkdownMessage appendWarningMsg(String text) {
    this.markdown.tmp.append("<font color=\"warning\">".concat(text).concat("</font>"));
    return this;
  }
}
