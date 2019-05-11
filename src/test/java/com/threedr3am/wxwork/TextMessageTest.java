package com.threedr3am.wxwork;

import static org.junit.Assert.assertTrue;

import com.threedr3am.wxwork.support.MessageBuilder;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Unit test for TextMessage.
 */
public class TextMessageTest
{
    @Test
    public void shouldAnswerWithTrue()
    {
        String template = "{\"text\":{\"content\":\"test\",\"mentioned_list\":[\"1\",\"2\",\"3\"],\"mentioned_mobile_list\":[\"4\",\"5\",\"6\"]},\"msgtype\":\"text\"}";
        String msg = MessageBuilder.buildTextMessage("test",Arrays.asList("1","2","3"),Arrays.asList("4","5","6")).build();
        assertTrue(StringUtils.equals(template,msg));
    }

    @Test
    public void shouldAnswerWithTrue2()
    {
        String template = "{\"text\":{\"content\":\"test\",\"mentioned_mobile_list\":[\"@all\"]},\"msgtype\":\"text\"}";
        String msg = MessageBuilder.buildTextMessage("test",false,true).build();
        assertTrue(StringUtils.equals(template,msg));
    }

    @Test
    public void shouldAnswerWithTrue3()
    {
        String template = "{\"text\":{\"content\":\"test\",\"mentioned_list\":[\"@all\"]},\"msgtype\":\"text\"}";
        String msg = MessageBuilder.buildTextMessage("test",true,false).build();
        assertTrue(StringUtils.equals(template,msg));
    }
}
