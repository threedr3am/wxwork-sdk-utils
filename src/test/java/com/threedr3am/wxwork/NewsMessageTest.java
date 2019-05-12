package com.threedr3am.wxwork;

import static org.junit.Assert.assertTrue;

import com.threedr3am.wxwork.bean.message.ImageMessage;
import com.threedr3am.wxwork.bean.message.NewsMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Unit test for NewsMessage.
 */
public class NewsMessageTest {

  @Test
  public void shouldAnswerWithTrue() {
    String template = "{\"news\":{\"articles\":[{\"title\":\"图文消息标题\",\"description\":\"图文消息简述\",\"url\":\"https://图文链接\",\"picurl\":\"https://图文消息封面图\"}]},\"msgtype\":\"news\"}";
    String msg = new NewsMessage()
        .addArticle("图文消息标题", "图文消息简述", "https://图文链接", "https://图文消息封面图")
        .build();
    assertTrue(StringUtils.equals(msg, template));
  }
}
