package io.github.xh_dev.rnr2.validations;


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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckIsEmailValidatorTest {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestObject{
        @CheckIsEmail
        private String email;
        @CheckIsNotEmpty
        private String name;
    }

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test validator for no error")
    void testValidatorForNoError(){
        final TestObject email = TestObject.builder().email("a@a.com").name("123").build();
        Set<ConstraintViolation<TestObject>> rs = validator.validate(email);
        assertEquals(0, rs.size());

    }
    @Test
    @DisplayName("Test validator for empty")
    void testValidatorForEmpty(){
        final TestObject email = TestObject.builder().email("").name("").build();
        Set<ConstraintViolation<TestObject>> rs = validator.validate(email);
        assertEquals(2, rs.size());
        ConstraintViolation<TestObject> emailFailure = rs.stream().filter(x -> x.getPropertyPath().toString().equals("email")).findFirst().get();
        assertEquals("email", emailFailure.getPropertyPath().toString());
        assertEquals("The email address[] is not valid", emailFailure.getMessage());

        ConstraintViolation<TestObject> nameFailure = rs.stream().filter(x -> x.getPropertyPath().toString().equals("name")).findFirst().get();
        assertEquals("name", nameFailure.getPropertyPath().toString());
        assertEquals("The field is empty", nameFailure.getMessage());

    }

    @Test
    @DisplayName("Test validator for null")
    void testValidatorForNull(){
        final TestObject email = TestObject.builder().email(null).name(null).build();
        Set<ConstraintViolation<TestObject>> rs = validator.validate(email);
        assertEquals(2, rs.size());
        ConstraintViolation<TestObject> emailFailure = rs.stream().filter(x -> x.getPropertyPath().toString().equals("email")).findFirst().get();
        assertEquals("email", emailFailure.getPropertyPath().toString());
        assertEquals("The email address[] is not valid", emailFailure.getMessage());

        ConstraintViolation<TestObject> nameFailure = rs.stream().filter(x -> x.getPropertyPath().toString().equals("name")).findFirst().get();
        assertEquals("name", nameFailure.getPropertyPath().toString());
        assertEquals("The field is null", nameFailure.getMessage());

    }
}
