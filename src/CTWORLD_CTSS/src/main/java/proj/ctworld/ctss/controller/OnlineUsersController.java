package proj.ctworld.ctss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import proj.ctworld.core.service.SessionRegistry;
import proj.ctworld.ctss.facade.AdminFacade;
import proj.ctworld.ctss.interceptor.AdminAudit;
import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.service.AdminAuthService;

/**
 * 【程式名稱】: OnlineUsersController <br/>
 * 【功能名稱】: 線上使用者查詢 <br/>
 * 【功能說明】: For 線上使用者查詢 使用<br/>
 * 【建立日期】: 2017/08/08 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author EnnisHong <br/>
 * 
 */
@Controller
public class OnlineUsersController{
    
    private static Logger logger = Logger.getLogger(OnlineUsersController.class);
    
    @Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
	@Autowired
	private SessionRegistry sessionRegistry;
    
    /**
     * announceMsg 說明: 線上使用者查詢.<br/>
     * <br/>   
     * @param locale
     * @param model
     * @return String
     * @throws Exception 
     * @author Ennis Hong 2017/08/01
     */
	@RequestMapping(value = "/admin/onlineUsers", method = RequestMethod.GET)
    @AdminAudit
    public String announceMsg(Locale locale, Model model, @RequestParam(value="userPlaceId", required=false) String userPlaceId) throws Exception {
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
        
        model.addAttribute("upmis",upmis);
        model.addAttribute("upId", StringUtils.isEmpty(userPlaceId) ? upmis.get(0) : userPlaceId);
        
        
        List<AdminEmpSession> onlineUsers = new ArrayList<>();
        Map<String, List<String>> userPlaceList = new HashMap<>();
        Map<Long, String> activeIpList = new HashMap<>();
        List<UserPlaceMappingInfo> placeMap;
        
        List<HttpSession> sessions = sessionRegistry.getSessions();
		for (HttpSession session : sessions) {
			Long activeToken = (Long) session.getAttribute(AdminFacade.ADMIN_TOKEN);
			AdminEmpSession activeUser = authService.getAdminEmpSession(activeToken);
			if (activeUser.getAesUserType().equals("user")) {
				placeMap = authService.getUserPlaceMappingInfo(aes.getAesUserId());
				List<String> placeList = new ArrayList<>();
				for (UserPlaceMappingInfo userPlaceInfo : placeMap) {
					placeList.add(userPlaceInfo.getPlace());
				}
				if (placeList != null) {
					userPlaceList.put(activeUser.getAesUserId(), placeList);
				}
			}
			onlineUsers.add(activeUser);
			activeIpList.put(activeUser.getAesId(), (String) session.getAttribute("ip"));
		}
        
        model.addAttribute("onlineUsers", onlineUsers);
        model.addAttribute("userPlaceList", userPlaceList);  
        model.addAttribute("activeIpList", activeIpList);
        
        return "admin/onlineUsers";
    }
}
