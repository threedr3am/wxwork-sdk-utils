package com.threedr3am.wxwork.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * base64转换
 */
public class Base64Translater {

  public static String decode2String(String str)
      throws UnsupportedEncodingException {
    return new String(Base64.getDecoder().decode(str),"UTF-8");
  }

  public static String decode2String(String str, String charsetName)
      throws UnsupportedEncodingException {
    return new String(Base64.getDecoder().decode(str),charsetName);
  }

  public static byte[] decode2Bytes(String str) {
    return Base64.getDecoder().decode(str);
  }

  public static byte[] decode2Bytes(byte[] bytes) {
    return Base64.getDecoder().decode(bytes);
  }

  public static String encode2String(String str) throws UnsupportedEncodingException {
    return encode2String(str, "UTF-8");
  }

  public static String encode2String(String str, String charsetName)
      throws UnsupportedEncodingException {
    return encode2String(str.getBytes(charsetName));
  }

  public static byte[] encode2Bytes(String str) {
    return encode2Bytes(str.getBytes());
  }

  public static String encode2String(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static byte[] encode2Bytes(byte[] bytes) {
    return Base64.getEncoder().encode(bytes);
  }

}
