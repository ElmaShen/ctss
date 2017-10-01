package proj.ctworld.core.lib;

import java.io.IOException;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 【程式名稱】: MyLocalDateTimeDeserializer <br/>
 * 【功能名稱】: LocalDateTime Deserializer <br/>
 * 【功能說明】: Json Deserializer for LocalDateTime <br/>
 * 【建立日期】: 2017/08/13 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
public class MyLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException,
            JsonProcessingException {
        
        return LocalDateTime.parse(arg0.getText());
    }

}
