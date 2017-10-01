package proj.ctworld.core.lib;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Component
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
    	SimpleModule module = new SimpleModule();
    	
    	module.addSerializer(DateTime.class, new MyDateTimeSerializer());
//    	module.addSerializer(DateTime.class, new ItemSerializer());
    	registerModule(module);
//        registerModule(new JodaModule());
//        setDateFormat(new ISO8601DateFormat());
        
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}