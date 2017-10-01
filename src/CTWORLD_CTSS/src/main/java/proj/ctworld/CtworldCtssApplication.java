package proj.ctworld;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import proj.ctworld.core.lib.MyDateTimeSerializer;
import proj.ctworld.core.lib.MyLocalDateTimeSerializer;
import proj.ctworld.ctss.interceptor.AdminAuditInterceptor;

@SpringBootApplication
@EnableWebMvc
public class CtworldCtssApplication extends WebMvcConfigurerAdapter  {

	public static void main(String[] args) {
		SpringApplication.run(CtworldCtssApplication.class, args);
	}
	
	@Bean
	public AdminAuditInterceptor getAdminAuditInterceptor() {
	    return new AdminAuditInterceptor();
	}
	
	/**
	 * add serializer to spring rx platform
	 * @return
	 */
	@Bean
    public MappingJackson2HttpMessageConverter jacksonConverter()
    {
        MappingJackson2HttpMessageConverter converter =
            new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(DateTime.class, new MyDateTimeSerializer());
        module.addSerializer(LocalDateTime.class, new MyLocalDateTimeSerializer());

        mapper.registerModule(module);
        converter.setObjectMapper(mapper);

        return converter;
    }
	
	/** 
     * 配置拦截器 
     * @author lance 
     * @param registry 
     */  
    public void addInterceptors(InterceptorRegistry registry) {
    	//加入 MemberLogin的 Interceptor
    	AdminAuditInterceptor aai = getAdminAuditInterceptor();
    	registry.addInterceptor(aai).addPathPatterns("/admin/**");
    	registry.addInterceptor(aai).addPathPatterns("/api/**");
//        registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/user/**");  
    }  
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
