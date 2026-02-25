package io.github.xh_dev.rnr2.jsonDeSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberedDate {
    private final static String FORMAT="yyyyMMddHHmmss";
    public static class Ser extends JsonSerializer<Date> {
        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if(date == null){
                jsonGenerator.writeNull();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
                jsonGenerator.writeString(sdf.format(date));
            }
        }
    }

    public static class Deser extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String s = jsonParser.getText();
            if(s==null){
                return null;
            } else {
                Matcher matcher = Pattern.compile("[0-9]{14}").matcher(s);
                if(matcher.matches()){
                    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
                    try {
                        return sdf.parse(s);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new RuntimeException(String.format("Fail parse date [%s]", s));
                }
            }
        }
    }
}
