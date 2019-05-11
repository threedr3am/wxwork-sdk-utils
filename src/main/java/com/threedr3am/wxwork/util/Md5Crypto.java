package com.threedr3am.wxwork.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5
 */
public class Md5Crypto {

  public static byte[] md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] array = md.digest(str.getBytes("utf-8"));
    return array;
  }

  public static byte[] md5(byte[] bytes) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] array = md.digest(bytes);
    return array;
  }
}
