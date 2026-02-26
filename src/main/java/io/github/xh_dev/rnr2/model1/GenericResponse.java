package io.github.xh_dev.rnr2.model1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GenericResponse<D> {
    @Schema(title = "Response Code",description = "Response Code for API request")
    private RespCode responseCode;
    @Schema(title = "Response message", description = "Response message for human readable (if any)")
    private String responseMessage;
    @Schema(title = "Request validation results", description = "Request validation failure result (exist when response is `02`)")
    @Builder.Default
    private List<ValidationResultLine> errors = new ArrayList<>();
    @Schema(title = "Response data",description = "Response data (exist when response is `00`")
    private D d;

    public static <DATA> GenericResponse<DATA> notFound(){
        return GenericResponse.<DATA>builder()
                .responseCode(RespCode.NotFound)
                .responseMessage("Resource not found")
                .d(null)
                .build();
    }

    public static <I, D> GenericResponse<D> validationFail(Collection<ConstraintViolation<I>> validationResult){
        return validationFail(
                validationResult.stream()
                        .map(i->
                                ValidationResultLine.builder().field(i.getPropertyPath().toString()).message(i.getMessage()).build()
                        )
                        .collect(Collectors.toList())
        );
    }

    public static <D> GenericResponse<D> validationFail(List<ValidationResultLine> validationResult){
        return GenericResponse.<D>builder()
                .responseCode(RespCode.ValidationFailed)
                .responseMessage("The input data is not correct")
                .errors( validationResult )
                .d(null)
                .build();
    }

    public static <D> GenericResponse<D> data(D d){
        return GenericResponse.<D>builder()
                .responseCode(RespCode.Success)
                .responseMessage("")
                .d(d)
                .build();
    }

    public static <D> GenericResponse<D> error(String msg){
        return GenericResponse.<D>builder()
                .responseCode(RespCode.Success)
                .responseMessage(msg)
                .d(null)
                .build();
    }

    public static <D> GenericResponse<D> businessError(String msg){
        return GenericResponse.<D>builder()
                .responseCode(RespCode.BusinessError)
                .responseMessage(msg)
                .d(null)
                .build();
    }
}
