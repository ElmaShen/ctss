package proj.ctworld.ctss.service;

import java.util.List;
import java.util.Map;

import proj.ctworld.ctss.orm.UserPlaceMappingInfo;

/**
 * 【程式名稱】: UserPlaceMappingService <br/>
 * 【功能名稱】: Service for 場所人員對應表<br/>
 * 【功能說明】: 執行 場所人員對應表 相關動作<br/>
 * 【建立日期】: 2017/08/08 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
public interface UserPlaceMappingService {
    
    /**
     * getAllDatas 說明: 取得所有場所人員對應表(user_place_mapping_info)資料.<br/>
     * <br/>   
     * @return List<UserPlaceMappingInfo>
     * @author JoyceLai 2017/8/8
     */
    public List<UserPlaceMappingInfo> getAllDatas() ;
    
    /**
     * changeDatas 說明: 新增更新或刪除場所人員對應表(user_place_mapping_info)資料.<br/>
     * <br/>   
     * @param updateType    更新類型：Insert/Update/Delete
     * @param updateDatas   異動的資料
     * @param userId        使用者Id
     * @param userName      使用者姓名
     * @param userIp        使用者Ip
     * @return Map<String, Object>
     * @author JoyceLai 2017/08/09
     */
    public Map<String, Object> changeDatasByType(String updateType, List<Map<String, Object>> updateDatas, String userId, String userName, String userIp);
    
    /**
     * getPlaceTypeList 說明: 取得場所分類清單.<br/>
     * <br/>   
     * @return List<String>
     * @author JoyceLai 2017/08/10
     */
    public List<String> getPlaceTypeList();
}
