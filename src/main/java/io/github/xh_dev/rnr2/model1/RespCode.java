package io.github.xh_dev.rnr2.model1;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@JsonSerialize(using = RespCode.Serializer.class)
@JsonDeserialize(using = RespCode.Deserializer.class)
public enum RespCode {
    Success("00"),
    Error("01"),
    ValidationFailed("02"),
    BusinessError("03"),
    NotFound("04");

    private final String code;
    RespCode(String code){
        this.code = code;
    }

    public String code(){
        return code;
    }

    public static RespCode ofCode(String code){
        return Optional.of(code)
                .flatMap(x-> Arrays.stream(RespCode.values()).filter(xx->xx.code.equals(x)).findFirst())
                .orElseThrow(()->new RuntimeException("Not a valid response code"));
    }

    public static class Serializer extends JsonSerializer<RespCode> {
        @Override
        public void serialize(RespCode respCode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(respCode.code);
        }
    }

    public static class Deserializer extends JsonDeserializer<RespCode>{
        @Override
        public RespCode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return RespCode.ofCode(jsonParser.getText());
        }
    }
}
