package proj.ctworld.ctss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.AnnouncementSetInfo;
import proj.ctworld.ctss.service.AdminAuthService;
import proj.ctworld.ctss.service.AnnounceSetService;

/**
 * 【程式名稱】: AnnounceSetDataController <br/>
 * 【功能名稱】: 公告訊息設定 Controller for 存取 Data 用<br/>
 * 【功能說明】: <br/>
 * 【建立日期】: 2017/08/11 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
@RestController
@RequestMapping(value = "/api/admin/announceSet")
public class AnnounceSetDataController {
    
    private static Logger logger = Logger.getLogger(AnnounceSetDataController.class);


    @Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private AnnounceSetService announceSetService;
    
    
    /**
     * getAllDataList 說明: 取得所有公告訊息設定資料.<br/>
     * <br/>   
     * @return List<AnnouncementSetInfo>
     * @author JoyceLai 2017/08/11
     */
    @RequestMapping(value = "/allDataList", method = RequestMethod.GET)
    @ResponseBody
    public List<AnnouncementSetInfo> getAllDataList() {
        return announceSetService.getAllDatas();
    }
    
    /**
     * updateData 說明: 新增更新或刪除公告訊息設定表資料.<br/>
     * <br/>   
     * @param updateType    更新類型：Insert/Update/Delete
     * @param updateDatas   異動的資料
     * @return Map<String,Object>
     * @author JoyceLai 2017/08/09
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateData(@RequestParam(value="updateType") String updateType, @RequestParam(value="updateDatas") String updateDatas) {
        logger.debug("### AnnounceSetDataController.updateData Start...");
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ObjectMapper objMapper = new ObjectMapper();
        List<Map<String, Object>> updateDataList = null;
      
        try {
            logger.debug("### AnnounceSetDataController.updateData updateDatas="+updateDatas);
            
            updateDataList = objMapper.readValue(updateDatas, List.class);
            Long token = authService.getToken(request);
            AdminEmpSession aes = authService.getAdminEmpSession(token);
            
            resultMap = announceSetService.changeDatasByType(updateType, updateDataList, aes.getAesUserId(), aes.getAesUserName(), request.getRemoteAddr());

        } catch (Exception e) {
            logger.error("### AnnounceSetDataController.updateData Error: " + e.getMessage(), e);
            
        } finally {
            logger.debug("### AnnounceSetDataController.updateData End...");
            
        }
        
        return resultMap;
    }
}
