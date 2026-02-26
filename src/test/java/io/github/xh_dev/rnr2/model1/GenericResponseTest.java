package io.github.xh_dev.rnr2.model1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericResponseTest {
    @Test
    @DisplayName("Test GenericResponse for success")
    void testNormal() throws IOException {
        final GenericResponse<String> resp = GenericResponse.data("Success");
        assertEquals(RespCode.Success, resp.getResponseCode());
        assertEquals("Success", resp.getD());
        assertEquals("", resp.getResponseMessage());
        assertEquals(0, resp.getErrors().size());
    }

    @Test
    @DisplayName("Test GenericResponse for error")
    void testError() throws IOException {
        final GenericResponse<String> resp = GenericResponse.error("Error");
        assertEquals(RespCode.Success, resp.getResponseCode());
        assertEquals("Error", resp.getResponseMessage());
        assertEquals(null, resp.getD());
        assertEquals(0, resp.getErrors().size());
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestObj{
        @NotNull(message = "field1 must not be null")
        private String field1;
        @NotEmpty(message = "field2 must not be empty")
        private String field2;
    }

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test GenericResponse for validation fail from javax.validation")
    void testValidationFailFromJavax() throws IOException {
        Set<ConstraintViolation<TestObj>> res = validator.validate(TestObj.builder().field1(null).field2("").build());
        final GenericResponse<String> resp = GenericResponse.validationFail(res);
        assertEquals(RespCode.ValidationFailed, resp.getResponseCode());
        assertEquals("The input data is not correct", resp.getResponseMessage());
        assertEquals(2, resp.getErrors().size());
        ValidationResultLine field1 = resp.getErrors().stream().filter(x -> x.getField().equals("field1")).findFirst().get();
        ValidationResultLine field2 = resp.getErrors().stream().filter(x -> x.getField().equals("field2")).findFirst().get();
        assertEquals("field1", field1.getField());
        assertEquals("field1 must not be null", field1.getMessage());
        assertEquals("field2", field2.getField());
        assertEquals("field2 must not be empty", field2.getMessage());
        assertEquals(null, resp.getD());
    }

    @Test
    @DisplayName("Test GenericResponse for validation fail")
    void testValidationFail() throws IOException {
        final GenericResponse<String> resp = GenericResponse.validationFail(
                Arrays.asList(ValidationResultLine.builder().field("a").message("a is not valid").build(),
                        ValidationResultLine.builder().field("a").message("a is not valid").build()
                ));
        assertEquals(RespCode.ValidationFailed, resp.getResponseCode());
        assertEquals("The input data is not correct", resp.getResponseMessage());
        assertEquals(2, resp.getErrors().size());
        assertEquals("a", resp.getErrors().get(0).getField());
        assertEquals("a is not valid", resp.getErrors().get(0).getMessage());
        assertEquals("a", resp.getErrors().get(1).getField());
        assertEquals("a is not valid", resp.getErrors().get(1).getMessage());
        assertEquals(null, resp.getD());
    }

    @Test
    @DisplayName("Test GenericResponse for business error")
    void testBusinessError() throws IOException {
        final GenericResponse<String> resp = GenericResponse.businessError("Business Error");
        assertEquals(RespCode.BusinessError, resp.getResponseCode());
        assertEquals("Business Error", resp.getResponseMessage());
        assertEquals(null, resp.getD());
        assertEquals(0, resp.getErrors().size());
    }

    @Test
    @DisplayName("Test GenericResponse for not found")
    void testNotFound() throws IOException {
        final GenericResponse<String> resp = GenericResponse.notFound();
        assertEquals(RespCode.NotFound, resp.getResponseCode());
    }



}
