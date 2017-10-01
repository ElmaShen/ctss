package proj.ctworld.ctss.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import proj.ctworld.core.facade.Code;
import proj.ctworld.core.facade.CoreFacade;
import proj.ctworld.core.lib.ModelErrorException;
import proj.ctworld.core.service.SessionRegistry;
import proj.ctworld.ctss.facade.AdminFacade;
import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.AdminInfo;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.repository.AdminEmpSessionRepostiory;
import proj.ctworld.ctss.repository.AdminInfoRepostiory;
import proj.ctworld.ctss.repository.UserPlaceMappingInfoRepository;

@Service("AdminAuthService")
@Transactional(rollbackFor=Exception.class)
public class AdminAuthServiceADAdapter implements AdminAuthService{
	
	private static Logger logger = Logger.getLogger(AdminAuthServiceADAdapter.class);
	
	@Autowired
	private CoreFacade coreFacade;
	
	@Autowired
	private UserPlaceMappingInfoRepository upmiRepo;
	
	@Autowired
	private AdminInfoRepostiory aiRepo;
	
	@Autowired
	private AdminEmpSessionRepostiory aesRepo;
	
	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public int login(String account, String password, String userType) throws ModelErrorException {
		String ldap_url = coreFacade.getSystemSetting("7000");
		
		logger.debug("ldap url="+ldap_url);
		
		//發動 AD 驗證
		int code = WindowsADService.LDAP_AUTH_AD(ldap_url, account, password);
//		int code = 1;	// JoyceLai Test
		
		if (code < 0)
			return Code.LOGIN_WRONG_ACCOUNT_PASSWORD;
		
		logger.debug("userType="+userType);
		if (userType.equals("admin")) {
			
			AdminInfo ai =  getAdminInfo(account);
			
			if (ai==null) return Code.NO_PERMISSION;
			
			logger.debug("AdminInfo="+ai.getUserId());
		}
		else if (userType.equals("user")) {
			List<UserPlaceMappingInfo> upmiList =  getUserPlaceMappingInfo(account);
			
			if (upmiList.size() <= 0) return Code.NO_PERMISSION;
			
			logger.debug("UserPlaceMappingInfo.size="+upmiList.size());
		}
		
		return Code.SUCCESS;
		
	}


	@Override
	public AdminInfo getAdminInfo(Long aiId) {
		return aiRepo.findOne(aiId);
	}


	@Override
	public AdminInfo getAdminInfo(String userId) {
		List<AdminInfo> list = aiRepo.getUserByUserId(userId);
		return list.size() > 0 ? list.get(0) : null; 
	}

	@Override
    public List<UserPlaceMappingInfo> getUserPlaceMappingInfo() {
        return upmiRepo.getAllUserPlace();
    }

	@Override
	public List<UserPlaceMappingInfo> getUserPlaceMappingInfo(String userId) {
		return upmiRepo.getUserPlaceByUserId(userId);
	}



	@Override
	public HashMap<String, Object> getMessage(Integer rtnCode, String message) {
		return coreFacade.getMessage(rtnCode,message);
	}


	@Override
	public HashMap<String, Object> getMessage(Integer rtnCode) {
		return coreFacade.getMessage(rtnCode);
	}


	@Override
	public String getSystemSetting(String ssCode) {
		return coreFacade.getSystemSetting(ssCode);
	}


	@Override
	public Long getToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (Long) session.getAttribute(AdminFacade.ADMIN_TOKEN);
	}
	
	@Override
	public void createToken(HttpServletRequest request,String userType,String userId) {
		HttpSession session = request.getSession();
		
		LocalDateTime now = new LocalDateTime();
		
		AdminEmpSession aes = new AdminEmpSession();
		aes.setAesIsActive("Y");
		aes.setAesLastAccessTime(now);
		aes.setAesLoginTime(now);
		aes.setAesRemoveReason("");
		aes.setAesUserId(userId);
		aes.setAesUserType(userType);
		
		if (userType.equals("admin")) {
			String userName = getAdminInfo(userId).getUserName();
			aes.setAesUserName(userName);
		}
		else if (userType.equals("user")) {
			UserPlaceMappingInfo upmi = getUserPlaceMappingInfo(userId).get(0);
			String userName = "";
			if (upmi.getManagerId().equals(userId))
				userName = upmi.getManager();
			else if (upmi.getExaminer1Id().equals(userId))
				userName = upmi.getExaminer1();
			else if (upmi.getExaminer2Id().equals(userId))
				userName = upmi.getExaminer2();
			
			aes.setAesUserName(userName);
		}
		
		aesRepo.save(aes);
		
		session.setAttribute(AdminFacade.ADMIN_TOKEN, aes.getAesId());
		sessionRegistry.addSession(session);
	}
	
	@Override
	public void removeToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(AdminFacade.ADMIN_TOKEN);
		session.invalidate();
	}

	@Override
	public AdminEmpSession getAdminEmpSession(Long aesId) {
		return aesRepo.findOne(aesId);
	}
	
	@Override
	public boolean isLoginAndUpdateSession(Long token) {
		
		if (token == null)
			return false;
		
		
		AdminEmpSession aes = getAdminEmpSession(token);

		if (aes == null) {
			return false;
		}
		
		aes.setAesLastAccessTime(new LocalDateTime());
		
		aesRepo.save(aes);
		
		return true;
	}
}
