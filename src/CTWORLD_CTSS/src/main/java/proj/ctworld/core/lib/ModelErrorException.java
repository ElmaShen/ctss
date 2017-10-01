package proj.ctworld.core.lib;

import org.apache.log4j.Logger;

public class ModelErrorException extends NullPointerException{
//public class ModelErrorException extends IllegalArgumentException{
	
	private static Logger logger = Logger.getLogger(ModelErrorException.class);
		
	private long rtnCode;
	private String rtnMsg;
	
	public ModelErrorException() {
		logger.debug("Ex1");
	}
	
	public ModelErrorException(Long rtnCode, String rtnMsg) {
		logger.debug("Ex2");
		this.rtnCode = rtnCode;
		this.rtnMsg = rtnMsg;
	}
	
	/**
	 * create a exception which will auto-generate an return map with custom message
	 * @param rtnMsg 
	 */
	public ModelErrorException(String rtnMsg) {
		super(rtnMsg);
		logger.debug("Ex3");
		//this.code= Code.INPUT_ERROR;
		this.rtnMsg = rtnMsg;
	}
	
	/**
	 * create a exception which will auto-generate an return map with custom code
	 * @param code as rtnCode
	 */
	public ModelErrorException(Long code) {
		super("code=" + code);
		this.rtnCode = code;
		this.rtnMsg = "";
	}
	
	public String toString() {
		return rtnMsg == null ? "" : rtnMsg;
	}
	
	public long getCode() {
		return rtnCode;
	}
	
	public String getMessage() {
		return rtnMsg == null ? "" : rtnMsg;
	}
	
	
}
