package com.threedr3am.wxwork.util;

/**
 * 十六进制转换
 */
public class HexTranslater {

  /**
   * 十六进制字符串转ascii字符串（例：666C61672E6A7067 转 flag.jpg）
   * @param hex
   * @return
   * @throws Exception
   */
  public static String hex2AsciiCharacter(String hex) throws Exception {
    if (hex != null && hex.length() > 0 && hex.length() % 2 == 0) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < hex.length(); i+=2) {
        String tmp = hex.substring(i,i+2);
        stringBuilder.append(Character.toString((char) Integer.parseInt(tmp,16)));
      }
      return stringBuilder.toString();
    }
    throw new Exception("16进制字符串不合法");
  }

  /**
   * 十六进制字符串转byte（例：66 转 102）
   * @param hex
   * @return
   * @throws Exception
   */
  public static byte[] hex2Bytes(String hex) throws Exception {
    if (hex != null && hex.length() > 0 && hex.length() % 2 == 0) {
      StringBuilder stringBuilder = new StringBuilder();
      byte[] bytes = new byte[hex.length()];
      for (int i = 0; i < hex.length(); i+=2) {
        String tmp = hex.substring(i,i+2);
        bytes[i] = (byte) Integer.parseInt(tmp,16);
      }
      return bytes;
    }
    throw new Exception("16进制字符串不合法");
  }



  /**
   * ascii字符串转十六进制字符串（例：flag.jpg 转 666C61672E6A7067）
   * @param asciiCharacter
   * @return
   * @throws Exception
   */
  public static String asciiCharacter2Hex(String asciiCharacter) throws Exception {
    return asciiCharacter2Hex(asciiCharacter.getBytes());
  }

  public static String asciiCharacter2Hex(String asciiCharacter,String charsetName) throws Exception {
    return asciiCharacter2Hex(asciiCharacter.getBytes(charsetName));
  }

  public static String asciiCharacter2Hex(byte[] bytes) throws Exception {
    if (bytes != null && bytes.length > 0) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        stringBuilder.append(Integer.toHexString((bytes[i] & 0xFF) | 0x100).substring(1, 3));
      }
      return stringBuilder.toString();
    }
    throw new Exception("ascii字符串不合法");
  }

}
