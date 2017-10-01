package proj.ctworld.ctss.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import proj.ctworld.ctss.orm.AnnouncementSetInfo;
import proj.ctworld.ctss.repository.AnnouncementSetInfoRepository;

/**
 * 【程式名稱】: AnnounceSetServiceImpl <br/>
 * 【功能名稱】: 實作 AnnounceSetService <br/>
 * 【功能說明】: 執行 公告訊息設定表 相關動作<br/>
 * 【建立日期】: 2017/8/11 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
@Service("AnnounceSetService")
@Component
public class AnnounceSetServiceImpl implements AnnounceSetService {
    
    private static Logger logger = Logger.getLogger(AnnounceSetServiceImpl.class);

    @Autowired
    AnnouncementSetInfoRepository announcementSetInfoRepository;

    @Override
    public List<AnnouncementSetInfo> getAllDatas() {
        return announcementSetInfoRepository.findByOrderByAnnouncementDtDesc();
    }

    /**
     * transferData2Asi 說明: 轉換資料到 AnnouncementSetInfo.<br/>
     * <br/>   
     * @param updateType    更新類型：Insert/Update/Delete
     * @param updateDatas   異動的資料
     * @param userId        使用者Id
     * @param userName      使用者姓名
     * @param userIp        使用者Ip
     * @return List<AnnouncementSetInfo>
     * @author JoyceLai 2017/08/11
     */
    private List<AnnouncementSetInfo> transferData2Asi(String updateType, List<Map<String, Object>> updateDatas, String userId, String userName, String userIp) {
        List<AnnouncementSetInfo> resultList = new ArrayList<AnnouncementSetInfo>();
        if(CollectionUtils.isNotEmpty(updateDatas)) {
            LocalDateTime currentTime = LocalDateTime.now();
            
            for(Map<String, Object> map : updateDatas) {
                AnnouncementSetInfo asi = new AnnouncementSetInfo();
                
                if("Insert".equals(updateType)) {
                    AnnouncementSetInfo asInfo = announcementSetInfoRepository.findFirstByOrderByMessageIdDesc();
                    asi.setMessageId(getNextMessageId(asInfo == null ? "" : asInfo.getMessageId()));
                    asi.setCreatorId(userId);
                    asi.setCreatorName(userName);
                    asi.setCreatorIp(userIp);
                    asi.setCreateDt(currentTime);
                    
                } else {
                    asi.setAsiId(Long.parseLong(map.get("asiId") == null ? null : String.valueOf(map.get("asiId"))));
                    
                }
                
                Boolean isE = map.get("enabled") == null || StringUtils.isEmpty(String.valueOf(map.get("enabled"))) ? null : Boolean.parseBoolean(String.valueOf(map.get("enabled")));
                int isEnabled = isE == null ? 0 : (isE ? 1 : 0);
                asi.setIsEnabled(isEnabled);
                asi.setAnnouncementSubject(map.get("announcementSubject") == null ? "" : String.valueOf(map.get("announcementSubject")));
                asi.setAnnouncementContent(map.get("announcementContent") == null ? "" : String.valueOf(map.get("announcementContent")));
                asi.setAnnouncementDt(currentTime);
                
                asi.setUpdatorId(userId);
                asi.setUpdatorName(userName);
                asi.setUpdatorIp(userIp);
                asi.setUpdateDt(currentTime);
                
                resultList.add(asi);
            }
        } 
        return resultList;
    }
    
    /**
     * getNextMessageId 說明: 取得下一個 MessageId.<br/>
     * messageId 編碼規則： "M" + 系統民國年月 + 該 messageId 最大的值再加1，如：M10606009，共9碼<br/>   
     * @param preMessageId  上一個 MessageId
     * @return String
     * @author JoyceLai 2017/08/11
     */
    private String getNextMessageId(String preMessageId) {
        
        int nextMessageNum = 1;
        LocalDateTime currentTime = LocalDateTime.now();
        int year = currentTime.getYear()-1911;
        int month = currentTime.getMonthOfYear();
        
        if(!StringUtils.isEmpty(preMessageId) && year == Integer.parseInt(preMessageId.substring(1, 4)) && month == Integer.parseInt(preMessageId.substring(4, 6))) {
            nextMessageNum = Integer.parseInt(preMessageId.substring(6)) + 1;
        }
        
        String sYear = String.valueOf(year);
        String sMonth = String.valueOf(month);
        String sNextMessageNum = String.valueOf(nextMessageNum);
        int messageNumLeng = sNextMessageNum.length();
        
        for(int i = 0; i < 3-sYear.length(); i++) {
            sYear = "0" + sYear;
        }
        for(int i = 0; i < 2-sMonth.length(); i++) {
            sMonth = "0" + sMonth;
        }
        for(int i = 0; i < 3-messageNumLeng; i++) {
            sNextMessageNum = "0" + sNextMessageNum;
            
        }
        
        String nextMessageId = "M".concat(sYear).concat(sMonth).concat(sNextMessageNum);
        
        return nextMessageId;
    }
  
    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.AnnounceSetService#changeDatasByType(java.lang.String, java.util.List, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> changeDatasByType(String updateType, List<Map<String, Object>> updateDatas,
            String userId, String userName, String userIp) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
//        List<AnnouncementSetInfo> resultList = new ArrayList<AnnouncementSetInfo>();
        boolean result = false;
        String resultStr = "N";
        String msg = "";
        try {
            
            if("Insert".equals(updateType)) {
                msg = "新增";
            } else if("Update".equals(updateType)) {
                msg = "更新";
            } else if("Delete".equals(updateType)) {
                msg = "刪除";
            } 
            
            List<AnnouncementSetInfo> asiDatas = transferData2Asi(updateType, updateDatas, userId, userName, userIp);
            
            if(CollectionUtils.isNotEmpty(asiDatas)) {
                if("Insert".equals(updateType)) {
                    announcementSetInfoRepository.save(asiDatas);
                    
                } else {
                    for(AnnouncementSetInfo asi : asiDatas) {
                        if("Update".equals(updateType)) {
                            announcementSetInfoRepository.updateData(asi.getAsiId(), asi.getAnnouncementSubject(), asi.getAnnouncementContent(), asi.getIsEnabled(), userId, userName, userIp);
                        
                        } else if("Delete".equals(updateType)) {
                            announcementSetInfoRepository.delete(asi.getAsiId());
                            
                        }
                    }
                } 
            }
            
            result = true;
            
        } catch (Exception e) {
            result = false;
            msg += "失敗，錯誤訊息：\n" + e.getMessage();
            logger.error("### changeDatasByType 異動資料發生錯誤：" + e.getMessage(), e);
            
        } finally {
            
            if(result) {
                resultStr = "Y";
                msg += "成功";
                
            } else {
                resultStr = "N";
            }
            
//            resultList = announcementSetInfoRepository.findAll();
            
            resultMap.put("success", resultStr);
            resultMap.put("msg", msg);
//            resultMap.put("resultList", resultList);
        }
        
        return resultMap;
    }
    
    

}
