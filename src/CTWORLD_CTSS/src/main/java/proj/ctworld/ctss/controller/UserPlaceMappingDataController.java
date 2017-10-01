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
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.service.AdminAuthService;
import proj.ctworld.ctss.service.UserPlaceMappingService;

/**
 * 【程式名稱】: UserPlaceMappingDataController <br/>
 * 【功能名稱】: 場所人員對應表 Controller for 存取 Data 用<br/>
 * 【功能說明】: <br/>
 * 【建立日期】: 2017/08/08 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
@RestController
@RequestMapping(value = "/api/admin/userPlaceMapping")
public class UserPlaceMappingDataController {
    
    private static Logger logger = Logger.getLogger(UserPlaceMappingDataController.class);
    
    @Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private UserPlaceMappingService userPlaceMappiingService;
    
    
    /**
     * getAllDataList 說明: 取得所有場所人員對應表資料.<br/>
     * <br/>   
     * @return List<UserPlaceMappingInfo>
     * @author JoyceLai 2017/08/08
     */
    @RequestMapping(value = "/allDataList", method = RequestMethod.GET)
    @ResponseBody
    public List<UserPlaceMappingInfo> getAllDataList() {
        return userPlaceMappiingService.getAllDatas();
    }
    

    /**
     * updateData 說明: 新增更新或刪除場所人員對應表資料.<br/>
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
        logger.debug("### UserPlaceMappingDataController.updateData Start...");
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ObjectMapper objMapper = new ObjectMapper();
        List<Map<String, Object>> updateDataList = null;
      
        try {
            logger.debug("### UserPlaceMappingDataController.updateData updateDatas="+updateDatas);
            
            updateDataList = objMapper.readValue(updateDatas, List.class);
            Long token = authService.getToken(request);
            AdminEmpSession aes = authService.getAdminEmpSession(token);
            
            resultMap = userPlaceMappiingService.changeDatasByType(updateType, updateDataList, aes.getAesUserId(), aes.getAesUserName(), request.getRemoteAddr());

        } catch (Exception e) {
            logger.error("### UserPlaceMappingDataController.updateData Error: " + e.getMessage(), e);
            
        } finally {
            logger.debug("### UserPlaceMappingDataController.updateData End...");
            
        }
        
        return resultMap;
    }
    

    /**
     * getPlaceTypeList 說明: 取得場所分類清單.<br/>
     * <br/>   
     * @return List<String>
     * @author JoyceLai 2017/08/10
     */
    @RequestMapping(value = "/placeTypeList", method = RequestMethod.GET)
    public List<String> getPlaceTypeList() {
        return userPlaceMappiingService.getPlaceTypeList();
    }

}
