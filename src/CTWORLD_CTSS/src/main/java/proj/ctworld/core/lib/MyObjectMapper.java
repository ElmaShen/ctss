package proj.ctworld.core.lib;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class MyObjectMapper extends ObjectMapper {

public MyObjectMapper() {

    registerModule(new JodaModule());
    setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz"));

    this.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

    this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}
