package io.github.xh_dev.rnr2.model1;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResultLine {
    private String field;
    private String message;
}
