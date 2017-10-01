package proj.ctworld.core.lib;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


/**
 * serializer DateTime for jax rs
 * @author OhDigest
 *
 */
public class MyDateTimeSerializer extends StdSerializer<DateTime> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3651376628973735846L;
	
	private static DateTimeFormatter dtf = DateTimeFormat.forPattern(Utils.FORMAT_DATETIME_ISO);

	public MyDateTimeSerializer() {
        this(null);
    }
   
    public MyDateTimeSerializer(Class<DateTime> t) {
        super(t);
    }

	@Override
	public void serialize(DateTime dt, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		// TODO Auto-generated method stub
//		jgen.writeStartObject();
		String dtStr = dtf.print(dt);
		jgen.writeString(dtStr);
//        jgen.writeEndObject();
	}
}