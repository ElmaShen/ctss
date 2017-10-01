package proj.ctworld.ctss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import proj.ctworld.ctss.interceptor.AdminApiAudit;
import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.service.AdminAuthService;
import proj.ctworld.ctss.service.AnnouncementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import proj.ctworld.core.facade.*;


@RestController
@RequestMapping("/api/admin/announcement")
public class AnnouncementController {
	
	private static Logger logger = Logger.getLogger(AnnouncementController.class);
	
	@Autowired
	private AdminAuthService authService;
	
	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private HttpServletRequest request;

	
	/**
	 * ajax api，已登入才能使用的API，取出該筆公告功能
	 * @param id
	 * @return 回傳公告訊息
	 * @throws Exception
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@AdminApiAudit
	public HashMap<String, Object> getAllAnnounce(@PathVariable String id) throws Exception
	{
		logger.debug("getAllAnnounce");
		Long token = authService.getToken(request);
		
		AdminEmpSession aes = authService.getAdminEmpSession(token);

		String userId = aes.getAesUserId();
		
		HashMap<String,Object> ret = authService.getMessage(Code.SUCCESS);
		
		ret.put("message",announcementService.getAnnouncement(id) );
		
		return ret;
	}
	
	/**
	 * ajax api，已登入才能使用的API，標記文章為已讀取
	 * @param value value中的ids為字串陣列，是文章的Id
	 * @return 回傳成功(0)或失敗(Input Error)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateRead" ,method = RequestMethod.POST)
	@AdminApiAudit
	@ResponseBody
	public HashMap<String,Object> updateRead(@RequestParam(value="ids") String ids) {
		logger.debug("### updateRead ids="+ids);
		ArrayList<String> messageIds = new ArrayList<String>();
		ObjectMapper objMapper = new ObjectMapper();
		boolean result = true;
		String msg = "";
		try {
			messageIds =  objMapper.readValue(ids, ArrayList.class);
			
			logger.debug("### messageIds="+messageIds);
			
			if (messageIds.size() == 0)
				return authService.getMessage(Code.INPUT_ERROR);
			
			Long token = authService.getToken(request);
			
			
			AdminEmpSession session = authService.getAdminEmpSession(token);
			
			String userId = session.getAesUserId();
			String userName = session.getAesUserName();
			String ip = request.getHeader("X-FORWARDED-FOR")==null?request.getRemoteAddr():request.getHeader("X-FORWARDED-FOR");
			
			announcementService.updateRead(userId, messageIds, ip, userName);
            
        } catch (Exception e) {
            result = false;
            msg = "更新失敗!";
            e.printStackTrace();
            logger.error("updateRead Error !!!", e);
            
        } finally {
            if(result) {
                msg = "更新成功";
            } 
        }
		
		Long token = authService.getToken(request);

		AdminEmpSession aes = authService.getAdminEmpSession(token);
		String userId = aes.getAesUserId();
		
		HashMap<String,Object> ret = authService.getMessage(Code.SUCCESS);
		ret.put("message", msg);
		ret.put("asis", announcementService.getUnread(userId));
		
		return ret;
		
	}
	
}
