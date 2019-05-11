### 企业微信工具包
目前具有以下实现：
1. 机器人webhook utils



#### 机器人webhook utils
1. 构建文本消息（TextMessage）

1.1 MessageBuilder快速构建

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

1.2 TextMessage自定义构建

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

2. 构建Markdown消息（MarkdownMessage）

3. 构建图片消息（ImageMessage）

4. 构建图文消息（NewsMessage）