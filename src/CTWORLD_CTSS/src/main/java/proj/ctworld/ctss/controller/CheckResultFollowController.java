package proj.ctworld.ctss.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import proj.ctworld.ctss.interceptor.AdminAudit;
import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.service.AdminAuthService;

/**
 * 
 * 【程式名稱】: CheckResultFollowController <br/>
 * 【功能名稱】: 進度追蹤<br/>
 * 【功能說明】: <br/>
 * 【建立日期】: 2017/08/04 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
@Controller
public class CheckResultFollowController {
    
    private static Logger logger = Logger.getLogger(CheckResultFollowController.class);
    
    @Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
    /**
     * 
     * checkResultFollow 說明: 取得進度追蹤初始化資料並跳轉頁面.<br/>
     * <br/>   
     * @param locale
     * @param model
     * @return
     * @throws Exception 
     * @return String
     * @author Tom Lai 2017/08/04
     */
    @RequestMapping(value = "/admin/checkResultFollow", method = RequestMethod.GET)
    @AdminAudit
    public String checkResultFollow(Locale locale, Model model, @RequestParam(value="userPlaceId", required=false) String userPlaceId) throws Exception {
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
        
//        for (UserPlaceMappingInfo userPlaceMappingInfo : upmis) {
//            logger.debug("upmi.placeId="+userPlaceMappingInfo.getPlaceId() );
//        }
        
        model.addAttribute("upmis",upmis);
        model.addAttribute("upId", StringUtils.isEmpty(userPlaceId) ? upmis.get(0) : userPlaceId);
        
        return "admin/checkResultFollow";
    }

}
