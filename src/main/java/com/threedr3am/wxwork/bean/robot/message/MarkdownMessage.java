package com.threedr3am.wxwork.bean.robot.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threedr3am.wxwork.bean.robot.MessageBase;
import com.threedr3am.wxwork.enums.MsgType;
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


  /**
   * 包装MarkDown支持
   */
  public static class Warpper {

    /**
     * 构造1-6级标题返回
     * @param title
     * @param level
     * @return
     */
    public static String warpLevelTitle(String title, int level) {
      assert (level > 0 && level < 7);
      StringBuilder tmp = new StringBuilder();
      for (int i = 0; i < level; i++) {
        tmp.append("#");
      }
      tmp.append(" ".concat(title));
      return tmp.toString();
    }

    /**
     * 构造加粗文字返回
     * @param bold
     * @return
     */
    public static String warpBold(String bold) {
      return "**".concat(bold).concat("**");
    }

    /**
     * 构造链接返回
     * @param url
     * @param text
     * @return
     */
    public static String warpHref(String url, String text) {
      return "[".concat(text).concat("](").concat(url).concat(")");
    }

    /**
     * 构造行内代码返回
     * @param code
     * @return
     */
    public static String warpLineCode(String code) {
      return "`".concat(code).concat("`");
    }

    /**
     * 构造引用返回
     * @param reference
     * @return
     */
    public static String warpReference(String reference) {
      return "> ".concat(reference);
    }

    /**
     * 构造绿色文字返回
     * @param text
     * @return
     */
    public static String warpInfoMsg(String text) {
      return "<font color=\"info\">".concat(text).concat("</font>");
    }

    /**
     * 构造灰色文字返回
     * @param text
     * @return
     */
    public static String warpCommentMsg(String text) {
      return "<font color=\"comment\">".concat(text).concat("</font>");
    }

    /**
     * 构造橙红色文字返回
     * @param text
     * @return
     */
    public static String warpWarningMsg(String text) {
      return "<font color=\"warning\">".concat(text).concat("</font>");
    }
  }
}
