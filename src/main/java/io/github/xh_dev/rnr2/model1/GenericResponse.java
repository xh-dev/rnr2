package io.github.xh_dev.rnr2.model1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GenericResponse<DATA> {
    private RespCode responseCode;
    private String responseMessage;
    private List<ValidationResultLine> errors;
    private DATA data;

    public static <DATA> GenericResponse<DATA> notFound(){
        return GenericResponse.<DATA>builder()
                .responseCode(RespCode.NotFound)
                .responseMessage("Resource not found")
                .data(null)
                .build();
    }

    public static <INPUT, DATA> GenericResponse<DATA> validationFail(Collection<ConstraintViolation<INPUT>> validationResult){
        return GenericResponse.<DATA>builder()
                .responseCode(RespCode.ValidationFailed)
                .responseMessage("The input data is correct")
                .errors(
                        validationResult.stream()
                                .map(i->
                                        ValidationResultLine.builder().field(i.getPropertyPath().toString()).message(i.getMessage()).build()
                                )
                                .collect(Collectors.toList())
                )
                .data(null)
                .build();
    }

    public static <DATA> GenericResponse<DATA> data(DATA data){
        return GenericResponse.<DATA>builder()
                .responseCode(RespCode.Success)
                .responseMessage("")
                .data(data)
                .build();
    }

    public static <DATA> GenericResponse<DATA> error(String msg){
        return GenericResponse.<DATA>builder()
                .responseCode(RespCode.Success)
                .responseMessage(msg)
                .data(null)
                .build();
    }
}
