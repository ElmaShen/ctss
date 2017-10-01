package proj.ctworld.ctss.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

import proj.ctworld.core.lib.ModelErrorException;
import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.service.AdminAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import proj.ctworld.core.facade.*;


@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {
	
	private static Logger logger = Logger.getLogger(AdminAuthController.class);
	
	@Autowired
	private AdminAuthService authService;

	@Autowired
	private HttpServletRequest request;


	
	@RequestMapping(method = RequestMethod.GET)
	public HashMap<String, Object> statusCheck() throws Exception
	{
//		String ldapURL = "ldap://localhost:389";
//		String account = "matthewhui";
//		String password = "C0ncept!23";
//		
//		WindowsADService.LDAP_AUTH_AD(ldapURL, account, password);
//		adminSession = getAdminSession();
		
		Long token = authService.getToken(request);
		
		logger.debug("token="+token);
		
		
		if (token == null)
			return authService.getMessage(Code.NON_LOGIN);
		
		AdminEmpSession aes = authService.getAdminEmpSession(token);
		
		if (aes == null)
			return authService.getMessage(Code.NON_LOGIN);
		
		String userType = aes.getAesUserType();
		String userId = aes.getAesUserId();
		
		HashMap<String,Object> ret = authService.getMessage(Code.SUCCESS);
		
		ret.put("userType", userType);
		if (userType.equals("admin")) 
			ret.put("user", authService.getAdminInfo(userId));
		else
			ret.put("user", authService.getUserPlaceMappingInfo(userId));
		
//		ret.put("menu", value);
		return ret;
//		int code = Code.NON_LOGIN;
//		if (adminSession == null || (code = adminSession.isAvailable(accessTime)) < 0) //NON_LOGIN
//			return getMessage(code);
//		else {
//			HashMap<String, Object> ret = getMessage(code);
//			ret.put("adminUser", authService.getUser(adminSession.getUserId()));
//			ret.put("menu", authService.getMenu(adminSession.getUserId()));
//			return ret;
//		}
	}

	/**
	 * ajax api，由登入頁發動登入API
	 * @param value 傳送account、password 和 userType
	 * @return 回傳 成功（0）或 失敗（<0)
	 * @throws ModelErrorException
	 */
	@RequestMapping(method = RequestMethod.POST, produces = { "application/json", "application/xml" } , consumes = "application/json")
	public HashMap<String, Object> login(@RequestBody HashMap value) throws ModelErrorException
	{
		
		String account = (String)value.get("account");
		String password = (String)value.get("password");
		String userType = (String)value.get("userType");
		
		logger.debug("讀取parameter account="+account+" and pass userType="+userType);
		
		//檢核必要的參數
		if (account == null || account.isEmpty() )
			throw new ModelErrorException("缺少必填的參數");
		
		if (password == null || password.isEmpty())
			throw new ModelErrorException("缺少必填的參數");
		
		if (userType == null || userType.isEmpty())
			throw new ModelErrorException("缺少必填的參數");
		
		int code = authService.login(account,password,userType);
		
		logger.debug("code="+code);
		
		if (code < 0 )
			return  authService.getMessage(code);
		
		//建立session
		authService.createToken(request, userType, account);

		return authService.getMessage(Code.SUCCESS);
		
	}
	
}
