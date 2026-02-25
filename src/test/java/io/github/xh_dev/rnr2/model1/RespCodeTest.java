package io.github.xh_dev.rnr2.model1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RespCodeTest {
    @Test
    @DisplayName("Test response code")
    void testNormal() throws IOException {
        assertEquals("00", RespCode.Success.code());
        assertEquals("01", RespCode.Error.code());
        assertEquals("02", RespCode.ValidationFailed.code());
        assertEquals("03", RespCode.BusinessError.code());
        assertEquals("04", RespCode.NotFound.code());

        assertEquals(RespCode.Success, RespCode.ofCode("00"));
        assertEquals(RespCode.Error, RespCode.ofCode("01"));
        assertEquals(RespCode.ValidationFailed, RespCode.ofCode("02"));
        assertEquals(RespCode.BusinessError, RespCode.ofCode("03"));
        assertEquals(RespCode.NotFound, RespCode.ofCode("04"));
        final ObjectMapper om = new ObjectMapper();
        assertEquals("\"00\"", om.writeValueAsString(RespCode.Success));
        assertEquals(RespCode.NotFound, om.readValue("\"04\"", RespCode.class));




    }
}
