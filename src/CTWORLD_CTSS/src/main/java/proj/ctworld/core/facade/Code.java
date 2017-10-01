package proj.ctworld.core.facade;

public class Code {
	
	public static final int SUCCESS = 0;
	
	public static final int DATE_RANGE_ERROR = -8000; //日期範圍錯誤
	
	//verification code
	public static final int WRONG_VERIFICATION_CODE = -9800;
	public static final int EXPIRED_VERIFICATION_CODE = -9801;
	public static final int WRONG_CAPTCHA = -9802;
	
	//auth
	public static final int NON_LOGIN = -9900;
	public static final int LOGIN_WRONG_ACCOUNT = -9901;
	public static final int LOGIN_WRONG_PASSWORD = -9902;
	public static final int LOGIN_EXPIRED = -9903;
	public static final int MULTI_LOGIN = -9904;
	public static final int LOGIN_WRONG = -9905;
    public static final int LOGIN_WRONG_ACCOUNT_PASSWORD = -9906;
    public static final int NO_PERMISSION = -9907;
	
	
	//system message
	public static final int INPUT_ERROR = -9997;
	public static final int INVALID_OPERATION = -9998;
	public static final int SYSTEM_ERROR = -9999;

}
