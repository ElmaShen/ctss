package proj.ctworld.ctss.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import proj.ctworld.ctss.orm.AnnouncementSetInfo;
import proj.ctworld.ctss.service.AnnounceMsgService;

/**
 * 【程式名稱】: AnnounceMsgDataController <br/>
 * 【功能名稱】: <br/>
 * 【功能說明】: <br/>
 * 【建立日期】: 2017/08/01 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author EnnisHong <br/>
 * 
 */
@RestController
@RequestMapping(value = "/api/admin/announceMsg")
public class AnnounceMsgDataController {
    private static Logger logger = Logger.getLogger(AnnounceMsgDataController.class);
    
    
    @Autowired
    private AnnounceMsgService announceMsgService;

    /**
     * getAllAnnounceMsg 說明: 取得公告訊息清單資料.<br/>
     * <br/>  
     * @return List<AnnouncementSetInfo>
     * @author JoyceLai 2017/7/26
     */
    @RequestMapping(value = "/getAllAnnounceMsg", method = RequestMethod.POST)
    @ResponseBody
    public List<AnnouncementSetInfo> getAllAnnounceMsg() {
    	List<AnnouncementSetInfo> resultList = announceMsgService.getAllAnnounceMsg();
        logger.debug("### resultList.size() =" + resultList.size());
        
        return resultList;
    }
}
