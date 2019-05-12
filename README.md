## 企业微信工具包
目前具有以下实现：
1. 机器人webhook utils



### 机器人webhook utils
##### 1.构建文本消息（TextMessage）

**MessageBuilder快速构建**

发送文本"test"，并且@所有手机号对应的用户
```
MessageBuilder
  .buildTextMessage("test",false,true)
  .build()
```
参数
```
String msg ：发送的文本消息内容
boolean forAllUserIds ：是否@所有userId发送
boolean forAllMobiles ：是否@所有手机号发送
public static TextMessage buildTextMessage(String msg, boolean forAllUserIds, boolean forAllMobiles);
```
--- 

发送文本"test"，并且@userId为1、2、3以及手机号为4、5、6的用户
```
MessageBuilder
  .buildTextMessage("test",Arrays.asList("1","2","3"),Arrays.asList("4","5","6"))
  .build()
```
参数
```
String msg ：发送的文本消息内容
List<String> userIds ：@的用户userId集合
List<String> mobiles ：@的用户手机号集合
public static TextMessage buildTextMessage(String msg, List<String> userIds, List<String> mobiles)
```

**TextMessage自定义构建**

发送文本内容"test"，并且@手机号为1、2、3的用户以及userId为4、5、6的用户
```
new TextMessage()
  .msg("test")
  .addMobileReceiver("1")
  .addUserIdReceiver("2")
  .setMobileReceiver(Arrays.asList("1","2","3"))
  .setUserIdReceiver(Arrays.asList("4","5","6"))
  .build();
```
方法
```
addMobileReceiver ：添加@用户手机号
addUserIdReceiver ：添加@用户userId
setMobileReceiver ：设置@用户手机号列表
setUserIdReceiver ：设置@用户userId列表
```

##### 2. 构建Markdown消息（MarkdownMessage）

封装了markdown语法快捷使用方法

```
new MarkdownMessage()
  .appendLevelTitle("二级标题",2)
  .appendNormal("自定义内容")
  .newLine()//换行
  .appendBold("文字加粗")
  .appendHref("https://www.baidu.com","百度链接")
  .appendReference("引用")
  .appendInfoMsg("绿色文字")
  .appendCommentMsg("灰色文字")
  .appendWarningMsg("橙红色文字")
  .appendLineCode("new MarkdownMessage();//单行代码")
  .build();
```

##### 3. 构建图片消息（ImageMessage）

file文件构建
```
String msg = new ImageMessage()
  .file(new File(ImageMessageTest.class.getClassLoader().getResource("duolaAmeng.jpg").getFile()))
  .build();
```

byte字节数组构建
```
ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
Path path = Paths.get(ImageMessageTest.class.getClassLoader().getResource("duolaAmeng.jpg").getPath());
Files.copy(path, byteArrayOutputStream);
String msg = new ImageMessage()
  .bytes(byteArrayOutputStream.toByteArray())
  .build();
```

##### 4. 构建图文消息（NewsMessage）

```
String msg = new NewsMessage()
  .addArticle("图文消息标题", "图文消息简述", "https://图文链接", "https://图文消息封面图")
  .build();
```