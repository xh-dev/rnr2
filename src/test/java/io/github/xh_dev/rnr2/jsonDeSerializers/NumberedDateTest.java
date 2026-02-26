package io.github.xh_dev.rnr2.jsonDeSerializers;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

public class NumberedDateTest {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class TestObj{
        @JsonSerialize(using = NumberedDate.Ser.class)
        @JsonDeserialize(using = NumberedDate.Deser.class)
        private Date date;
    }

    final static TimeZone HKT= TimeZone.getTimeZone("Asia/Hong_Kong");

    @BeforeAll
    public static void setUp() {
        TimeZone.setDefault(HKT);
    }

    @Test
    @DisplayName("Test for normal")
    void testNormal() throws IOException {
        TestObj to = TestObj.builder()
                .date(
                        new Date(
                                LocalDateTime.of(2025, 7, 2, 3, 4, 6, 7)
                                        .atZone(HKT.toZoneId()).toInstant().toEpochMilli()
                        )
                ).build();

        ObjectMapper om = new ObjectMapper();
        String string = om.writeValueAsString(to);
        assertEquals("{\"date\":\"20250702030406\"}",string);
        TestObj value = om.readValue(string.getBytes(StandardCharsets.UTF_8), TestObj.class);
        assertEquals(new Date(
                LocalDateTime.of(2025, 7, 2, 3, 4, 6, 7)
                        .atZone(HKT.toZoneId()).toInstant().toEpochMilli()
        ), value.getDate());


    }

    @Test
    @DisplayName("Test for null value")
    void testNull() throws IOException {
        TestObj to = TestObj.builder()
                .date(null).build();
        ObjectMapper om = new ObjectMapper();
        String string = om.writeValueAsString(to);
        assertEquals("{\"date\":null}",string);
        TestObj value = om.readValue(string.getBytes(StandardCharsets.UTF_8), TestObj.class);
        assertNull(value.getDate());
    }

    @Test
    @DisplayName("Wrong date passing in")
    void testWrongDate() throws IOException {
        ObjectMapper om = new ObjectMapper();
        assertThrows(JsonMappingException.class, ()->{
                    om.readValue("{\"date\":\"20557865sdf124\"}".getBytes(StandardCharsets.UTF_8), TestObj.class);
                });

    }
}
