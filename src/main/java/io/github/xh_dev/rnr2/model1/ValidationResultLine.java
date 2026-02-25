package io.github.xh_dev.rnr2.model1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResultLine {
    @Schema(description = "The field with error")
    private String field;
    @Schema(description = "The error message")
    private String message;
}
