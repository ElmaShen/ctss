package proj.ctworld;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import proj.ctworld.core.facade.Code;
import proj.ctworld.core.facade.CoreFacade;
import proj.ctworld.core.lib.ModelErrorException;


@EnableWebMvc
@ControllerAdvice
public class MyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(MyResponseEntityExceptionHandler.class);

	@Autowired
	private CoreFacade smService;
	
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
    	logger.info("handle exception");
    	
    	Map<String, Object> ret;
    	try {
    		logger.error("Caught exception", ex);
    		
            if(ex instanceof ServiceException) {
            	logger.info("ServiceException");
            	ex.printStackTrace();
            }
    		
    		if (ex instanceof ModelErrorException) {
    			logger.info("get ModelErrorException");
    			
				if (ex.toString().isEmpty())
					ret = smService.getMessage((int) ((ModelErrorException) ex).getCode());
				else
					ret = smService.getMessage((int) ((ModelErrorException) ex).getCode(), ex.toString());
    		} else {
    			logger.info("get " + ex.getClass().getName());
    			
    			ret = smService.getMessage(Code.SYSTEM_ERROR);
    			StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				ret.put("errorMsg", sw.getBuffer().toString());
    		}
    	} catch(Exception handlerException) {
    		logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
    		
    		ret = new HashMap<String, Object>();
    		ret.put("rtnCdoe", Code.SYSTEM_ERROR);
    		ret.put("rtnMsg", "Handling of [" + ex.getClass().getName() + "] resulted in Exception");
    	}
        
        return new ResponseEntity<Object>(ret, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("path", request.getContextPath());
        responseBody.put("message", "The URL you have reached is not in service at this time (404).");
        return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_FOUND);
    }

}
