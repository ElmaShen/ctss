package proj.ctworld.ctss.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import proj.ctworld.core.facade.Code;
import proj.ctworld.ctss.interceptor.AdminAudit;
import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.AnnouncementReadRecord;
import proj.ctworld.ctss.orm.AnnouncementSetInfo;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.service.AdminAuthService;
import proj.ctworld.ctss.service.AnnouncementService;

import org.apache.log4j.Logger;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController  {
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private AdminAuthService authService;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AnnouncementService announcementService;
	
	
	/**
	 * 登入頁我，若已登入則轉導至首頁
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public String login1(Locale locale, Model model) throws Exception {

		Long token = authService.getToken(request);
		
		if ( token != null )
			return "redirect:index";
        
		return "admin/login";
	}
	
	/**
	 * 登出頁，登出並直接轉道至登入頁
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
	public String logout(Locale locale, Model model) throws Exception {
		
		authService.removeToken(request);

		return "redirect:login";
	}
	
	
	/**
	 * 系統首頁的內容，會先由 @AdminAudit擋在外面
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/index", method = RequestMethod.GET)
	@AdminAudit
	public String index(Locale locale, Model model, @RequestParam(value="userPlaceId", required=false) String userPlaceId) throws Exception {
		
		Long token = authService.getToken(request);
		AdminEmpSession aes = authService.getAdminEmpSession(token);
		
		logger.debug("UserId:"+aes.getAesUserId());
		logger.debug("UserName:"+aes.getAesUserName());
		logger.debug("UserType:"+aes.getAesUserType());
        logger.debug("UserIp:"+request.getRemoteAddr());
		
		model.addAttribute("UserId",aes.getAesUserId());
		model.addAttribute("UserName",aes.getAesUserName());
		model.addAttribute("UserType",aes.getAesUserType());
        model.addAttribute("UserIp", request.getRemoteAddr());
		
		List<UserPlaceMappingInfo> upmis;
        if("admin".equals(aes.getAesUserType())) {
            upmis = authService.getUserPlaceMappingInfo();
        } else {
            upmis = authService.getUserPlaceMappingInfo(aes.getAesUserId());
        }
		
		logger.debug("upmis.size="+upmis.size());
		
		for (UserPlaceMappingInfo userPlaceMappingInfo : upmis) {
			logger.debug("upmi.placeId="+userPlaceMappingInfo.getPlaceId() );
		}
		
		model.addAttribute("upmis",upmis);
        model.addAttribute("upId", StringUtils.isEmpty(userPlaceId) ? upmis.get(0) : userPlaceId);
		
		List<AnnouncementSetInfo> asis = announcementService.getUnread(aes.getAesUserId());
		
		logger.debug("asis.size="+asis.size());
		
		model.addAttribute("asis",asis);
		
		return "admin/index";
	}
	
	
}
