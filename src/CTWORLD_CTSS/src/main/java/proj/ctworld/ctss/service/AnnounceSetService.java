package proj.ctworld.ctss.service;

import java.util.List;
import java.util.Map;

import proj.ctworld.ctss.orm.AnnouncementSetInfo;

/**
 * 【程式名稱】: AnnounceSetService <br/>
 * 【功能名稱】: Service for 公告訊息設定表<br/>
 * 【功能說明】: 執行 公告訊息設定表 相關動作<br/>
 * 【建立日期】: 2017/08/11 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
public interface AnnounceSetService {

    /**
     * getAllDatas 說明: 取得所有公告訊息設定表(announcement_set_info)資料.<br/>
     * <br/>   
     * @return List<AnnouncementSetInfo>
     * @author JoyceLai 2017/08/11
     */
    public List<AnnouncementSetInfo> getAllDatas() ;
    
    /**
     * changeDatas 說明: 新增更新或刪除公告訊息設定表(announcement_set_info)資料.<br/>
     * <br/>   
     * @param updateType    更新類型：Insert/Update/Delete
     * @param updateDatas   異動的資料
     * @param userId        使用者Id
     * @param userName      使用者姓名
     * @param userIp        使用者Ip
     * @return Map<String, Object>
     * @author JoyceLai 2017/08/11
     */
    public Map<String, Object> changeDatasByType(String updateType, List<Map<String, Object>> updateDatas, String userId, String userName, String userIp);
    
}
