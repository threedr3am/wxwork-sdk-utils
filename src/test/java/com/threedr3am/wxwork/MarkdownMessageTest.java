package com.threedr3am.wxwork;

import static org.junit.Assert.assertTrue;

import com.threedr3am.wxwork.bean.robot.message.MarkdownMessage;
import com.threedr3am.wxwork.support.MessageBuilder;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Unit test for MarkdownMessage.
 */
public class MarkdownMessageTest
{
    @Test
    public void shouldAnswerWithTrue()
    {
        String template = "{\"markdown\":{\"content\":\"test\"},\"msgtype\":\"markdown\"}";
        String msg = MessageBuilder.buildMarkdownMessage("test").build();
        assertTrue(StringUtils.equals(template,msg));
    }

    @Test
    public void shouldAnswerWithTrue2()
    {
        String template = "{\"markdown\":{\"content\":\"## 二级标题自定义内容\\n**文字加粗**[百度链接](https://www.baidu.com)> 引用<font color=\\\"info\\\">绿色文字</font><font color=\\\"comment\\\">灰色文字</font><font color=\\\"warning\\\">橙红色文字</font>`new MarkdownMessage();//单行代码`\"},\"msgtype\":\"markdown\"}";
        String msg = new MarkdownMessage()
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
        assertTrue(StringUtils.equals(template,msg));
    }

    @Test
    public void shouldAnswerWithTrue3()
    {
        String template = "{\"markdown\":{\"content\":\"## 二级标题自定义内容\\n**文字加粗**[百度链接](https://www.baidu.com)> 引用<font color=\\\"info\\\">绿色文字</font><font color=\\\"comment\\\">灰色文字</font><font color=\\\"warning\\\">橙红色文字</font>`new MarkdownMessage();//单行代码`\"},\"msgtype\":\"markdown\"}";
        String msg = new MarkdownMessage()
            .appendNormal(MarkdownMessage.Warpper.warpLevelTitle("二级标题",2))
            .appendNormal("自定义内容")
            .newLine()//换行
            .appendNormal(MarkdownMessage.Warpper.warpBold("文字加粗"))
            .appendNormal(MarkdownMessage.Warpper.warpHref("https://www.baidu.com","百度链接"))
            .appendNormal(MarkdownMessage.Warpper.warpReference("引用"))
            .appendNormal(MarkdownMessage.Warpper.warpInfoMsg("绿色文字"))
            .appendNormal(MarkdownMessage.Warpper.warpCommentMsg("灰色文字"))
            .appendNormal(MarkdownMessage.Warpper.warpWarningMsg("橙红色文字"))
            .appendNormal(MarkdownMessage.Warpper.warpLineCode("new MarkdownMessage();//单行代码"))
            .build();
        assertTrue(StringUtils.equals(template,msg));
    }
}
