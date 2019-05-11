package com.threedr3am.wxwork.bean.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threedr3am.wxwork.bean.MessageBase;
import com.threedr3am.wxwork.enums.MsgType;
import com.threedr3am.wxwork.util.Base64Translater;
import com.threedr3am.wxwork.util.HexTranslater;
import com.threedr3am.wxwork.util.Md5Crypto;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 图片消息
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageMessage extends MessageBase {

  @JsonProperty("msgtype")
  private String msgType = MsgType.IMAGE.getType();

  private Image image = new Image();

  @Getter
  public static class Image {

    /**
     * 图片内容的base64编码
     */
    private String base64;

    /**
     * 图片内容（base64编码前）的md5值
     */
    private String md5;

    private File tmpFile;

    private byte[] tmpBytes;

    private Image() {
    }

  }

  @Override
  protected void preHandle() {
    if (this.image.tmpFile != null && this.image.tmpFile.exists()) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      try {
        Files.copy(this.image.tmpFile.toPath(),byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        this.image.tmpBytes = bytes;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (this.image.tmpBytes != null) {
      try {
        this.image.md5 = HexTranslater.asciiCharacter2Hex(Md5Crypto.md5(this.image.tmpBytes));
        this.image.base64 = Base64Translater.encode2String(this.image.tmpBytes);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    assert (this.image.base64 != null && this.image.md5 != null);
  }

  public ImageMessage base64(String base64) {
    this.image.base64 = base64;
    return this;
  }

  public ImageMessage md5(String md5) {
    this.image.md5 = md5;
    return this;
  }

  public ImageMessage file(File file) {
    this.image.tmpFile = file;
    return this;
  }

  public ImageMessage bytes(byte[] bytes) {
    this.image.tmpBytes = bytes;
    return this;
  }


}
