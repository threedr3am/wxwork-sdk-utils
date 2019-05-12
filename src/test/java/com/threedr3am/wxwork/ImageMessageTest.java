package com.threedr3am.wxwork;

import static org.junit.Assert.assertTrue;

import com.threedr3am.wxwork.bean.message.ImageMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Unit test for ImageMessage.
 */
public class ImageMessageTest {

  @Test
  public void shouldAnswerWithTrue() {
    String template = "{\"image\":{\"base64\":\"/9j/4AAQSk";
    String template2 = "AAEhwAC+ogNlKiJqGJDm8B//Z\"},\"msgtype\":\"image\"}";
    String msg = new ImageMessage()
        .file(new File(
            ImageMessageTest.class.getClassLoader().getResource("duolaAmeng.jpg").getFile()))
        .build();
    assertTrue(msg.startsWith(template) && msg.endsWith(template2));
  }

  @Test
  public void shouldAnswerWithTrue2() {
    String template = "{\"image\":{\"base64\":\"/9j/4AAQSk";
    String template2 = "AAEhwAC+ogNlKiJqGJDm8B//Z\"},\"msgtype\":\"image\"}";
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      Path path = Paths.get(
          ImageMessageTest.class.getClassLoader().getResource("duolaAmeng.jpg").getPath());
      Files.copy(path, byteArrayOutputStream);
      String msg = new ImageMessage()
          .bytes(byteArrayOutputStream.toByteArray())
          .build();
      assertTrue(msg.startsWith(template) && msg.endsWith(template2));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
