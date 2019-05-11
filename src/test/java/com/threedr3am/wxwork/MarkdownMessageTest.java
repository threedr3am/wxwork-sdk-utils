package com.threedr3am.wxwork;

import static org.junit.Assert.assertTrue;

import com.threedr3am.wxwork.support.MessageBuilder;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Unit test for TextMessage.
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
}
