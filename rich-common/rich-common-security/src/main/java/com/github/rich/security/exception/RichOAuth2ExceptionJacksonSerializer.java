package com.github.rich.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author Petty
 */
public class RichOAuth2ExceptionJacksonSerializer extends StdSerializer<RichOAuth2Exception> {

    private static final long serialVersionUID = 2166984503759234629L;

    protected RichOAuth2ExceptionJacksonSerializer() {
        super(RichOAuth2Exception.class);
    }

    @Override
    public void serialize(RichOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("status", e.getHttpErrorCode());
        jsonGenerator.writeObjectField("timestamp", System.currentTimeMillis());
        String errorMessage = e.getMessage();
        if (errorMessage != null) {
            errorMessage = HtmlUtils.htmlEscape(errorMessage);
        }
        jsonGenerator.writeStringField("message", errorMessage);
        if (e.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jsonGenerator.writeStringField(key, add);
            }
        }
        jsonGenerator.writeEndObject();
    }
}
