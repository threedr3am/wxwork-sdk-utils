package com.threedr3am.wxwork.bean.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threedr3am.wxwork.bean.MessageBase;
import com.threedr3am.wxwork.enums.MsgType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 图文消息
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsMessage extends MessageBase {

  @JsonProperty("msgtype")
  private String msgType = MsgType.NEWS.getType();

  private News news = new News();

  @Getter
  public static class News {

    private List<Article> articles = new ArrayList<>();

    private News() {
    }

  }

  @Getter
  @Setter
  public static class Article {

    private String title;
    private String description;
    private String url;
    private String picurl;

    private Article() {
    }
  }

  @Override
  protected void preHandle() {
    assert (!this.news.articles.isEmpty());
  }

  public NewsMessage addArticle(String title, String description, String url, String picurl) {
    Article article = new Article();
    article.setTitle(title);
    article.setDescription(description);
    article.setUrl(url);
    article.setPicurl(picurl);
    this.news.articles.add(article);
    return this;
  }

}
