package io.github.xh_dev.rnr2.model1;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(title = "Response Code",description = "Response Code for API request")
    private RespCode responseCode;
    @Schema(title = "Response message", description = "Response message for human readable (if any)")
    private String responseMessage;
    @Schema(title = "Request validation results", description = "Request validation failure result (exist when response is `02`)")
    private List<ValidationResultLine> errors;
    @Schema(title = "Response data",description = "Response data (exist when response is `00`")
    private DATA data;

    public static <DATA> GenericResponse<DATA> notFound(){
        return GenericResponse.<DATA>builder()
                .responseCode(RespCode.NotFound)
                .responseMessage("Resource not found")
                .data(null)
                .build();
    }

    public static <INPUT, DATA> GenericResponse<DATA> validationFail(Collection<ConstraintViolation<INPUT>> validationResult){
        return validationFail(
                validationResult.stream()
                        .map(i->
                                ValidationResultLine.builder().field(i.getPropertyPath().toString()).message(i.getMessage()).build()
                        )
                        .collect(Collectors.toList())
        );
    }

    public static <DATA> GenericResponse<DATA> validationFail(List<ValidationResultLine> validationResult){
        return GenericResponse.<DATA>builder()
                .responseCode(RespCode.ValidationFailed)
                .responseMessage("The input data is correct")
                .errors( validationResult )
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

    public static <DATA> GenericResponse<DATA> businessError(String msg){
        return GenericResponse.<DATA>builder()
                .responseCode(RespCode.BusinessError)
                .responseMessage(msg)
                .data(null)
                .build();
    }
}
