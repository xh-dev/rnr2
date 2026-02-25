package io.github.xh_dev.rnr2.utils;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogMessageFormatterTest {
    @Test
    @DisplayName("Test for log formatting message")
    void testLogMessageFormatter() {
        io.github.xh_dev.rnr2.utils.LogMessageFormatter log = io.github.xh_dev.rnr2.utils.LogMessageFormatter.instance();
        assertEquals("LogMessageFormatterTest : testLogMessageFormatter : Starts", log.methodStart());
        assertEquals("LogMessageFormatterTest : testLogMessageFormatter : Ends", log.methodEnd());
        assertEquals("LogMessageFormatterTest : testLogMessageFormatter : Hello World", log.msg("Hello World"));
    }

}
