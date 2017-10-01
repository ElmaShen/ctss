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

import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.repository.UserPlaceMappingInfoRepository;

/**
 * 【程式名稱】: UserPlaceMappingServiceImpl <br/>
 * 【功能名稱】: 實作 UserPlaceMappingService <br/>
 * 【功能說明】: 執行 場所人員對應表 相關動作<br/>
 * 【建立日期】: 2017/08/08 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
@Service("UserPlaceMappingService")
@Component
public class UserPlaceMappingServiceImpl implements UserPlaceMappingService {
    
    private static Logger logger = Logger.getLogger(UserPlaceMappingServiceImpl.class);

    @Autowired
    UserPlaceMappingInfoRepository userPlaceMappingInfoRepository;
    
    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.UserPlaceMappingService#getAllDatas()
     */
    @Override
    public List<UserPlaceMappingInfo> getAllDatas() {
        
        return userPlaceMappingInfoRepository.findAll();
    }

    /**
     * getNextPlaceIdByPlaceType 說明: 依據場所分類取得場所識別碼.<br/>
     * 如:A00001， A主體單位,B週邊單位,C精舍(A/B/C+流水號5碼=共6碼) <br/>   
     * @param placeType     場所分類
     * @param prePlaceId    該場所分類的上一個 placeId
     * @return String
     * @author JoyceLai 2017/8/12
     */
    private String getNextPlaceIdByPlaceType (String placeType, String prePlaceId) {
        int nextPlaceIdNum = 1;
        String prefix = "";
        
        if(!StringUtils.isEmpty(prePlaceId)) {
            prefix = prePlaceId.substring(0, 1);
            nextPlaceIdNum = Integer.parseInt(prePlaceId.substring(1)) + 1;
        } else {
            if("主體單位".equals(placeType)) {
                prefix = "A";
            } else if("週邊單位".equals(placeType)) {
                prefix = "B";
            } if("精舍單位".equals(placeType)) {
                prefix = "C";
            }
        }
        
        String sNextPlaceIdNum = String.valueOf(nextPlaceIdNum);
        int nextPlaceIdLen = sNextPlaceIdNum.length();
        
        for(int i = 0; i < 5-nextPlaceIdLen; i++) {
            sNextPlaceIdNum = "0" + sNextPlaceIdNum;
        }
        
        return prefix.concat(sNextPlaceIdNum);
    }
    /**
     * transferData2Upmi 說明: 轉換資料到 UserPlaceMappingInfo.<br/>
     * <br/>   
     * @param updateType    更新類型：Insert/Update/Delete
     * @param updateDatas   異動的資料
     * @param userId        使用者Id
     * @param userName      使用者姓名
     * @param userIp        使用者Ip
     * @return List<UserPlaceMappingInfo>
     * @author JoyceLai 2017/08/09
     */
    private List<UserPlaceMappingInfo> transferData2Upmi(String updateType, List<Map<String, Object>> updateDatas, String userId, String userName, String userIp) {
        List<UserPlaceMappingInfo> resultList = new ArrayList<UserPlaceMappingInfo>();
        if(CollectionUtils.isNotEmpty(updateDatas)) {
            LocalDateTime currentTime = LocalDateTime.now();
            for(Map<String, Object> map : updateDatas) {
                UserPlaceMappingInfo upmi = new UserPlaceMappingInfo();
                
                if("Insert".equals(updateType)) {
                    String placeType = map.get("placeType") == null ? "" : String.valueOf(map.get("placeType"));
                    upmi.setPlaceType(placeType);
                    // placeId 編碼規則： 該 placeType 最大的一個placeId再加1，如：A10001
                    UserPlaceMappingInfo upmInfo = userPlaceMappingInfoRepository.findFirstByPlaceTypeOrderByPlaceIdDesc(placeType);
                    String placeId = getNextPlaceIdByPlaceType(placeType, upmInfo.getPlaceId());
                    logger.debug("### UserPlaceMappingServiceImpl transferData2Upmi placeId=" + placeId);
                    upmi.setPlaceId(placeId);
                    upmi.setPlace(map.get("place") == null ? "" : String.valueOf(map.get("place")));
                    upmi.setCreatorId(userId);
                    upmi.setCreatorName(userName);
                    upmi.setCreatorIp(userIp);
                    upmi.setCreateDt(currentTime);
                    
                } else {
                    upmi.setUpmiId(Long.parseLong(map.get("upmiId") == null ? null : String.valueOf(map.get("upmiId"))));
                    
                }
                
                upmi.setManager(map.get("manager") == null ? "" : String.valueOf(map.get("manager")));
                upmi.setManagerId(map.get("managerId") == null ? "" : String.valueOf(map.get("managerId")));
                upmi.setExaminer1(map.get("examiner1") == null ? "" : String.valueOf(map.get("examiner1")));
                upmi.setExaminer1Id(map.get("examiner1Id") == null ? "" : String.valueOf(map.get("examiner1Id")));
                upmi.setExaminer2(map.get("examiner2") == null ? "" : String.valueOf(map.get("examiner2")));
                upmi.setExaminer2Id(map.get("examiner2Id") == null ? "" : String.valueOf(map.get("examiner2Id")));
                Boolean isNdcf = map.get("needDailyCheckFire") == null || StringUtils.isEmpty(String.valueOf(map.get("needDailyCheckFire")))? null : Boolean.parseBoolean(String.valueOf(map.get("needDailyCheckFire")));
                long isNeedDailyCheckFire = isNdcf == null ? null : (isNdcf ? 1 : 0);
                upmi.setIsNeedDailyCheckFire(isNeedDailyCheckFire);
                Boolean isE = map.get("enabled") == null || StringUtils.isEmpty(String.valueOf(map.get("enabled"))) ? null : Boolean.parseBoolean(String.valueOf(map.get("enabled")));
                long isEnabled = isE == null ? null : (isE ? 1 : 0);
                upmi.setIsEnabled(isEnabled);
                upmi.setUnitCode(map.get("unitCode") == null ? "" : String.valueOf(map.get("unitCode")));
                
                upmi.setUpdatorId(userId);
                upmi.setUpdatorName(userName);
                upmi.setUpdatorIp(userIp);
                upmi.setUpdateDt(currentTime);
                
                resultList.add(upmi);
            }
        }    
        
        return resultList;
    }
    
    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.UserPlaceMappingService#changeDatasByType(java.lang.String, java.util.List, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> changeDatasByType(String updateType, List<Map<String, Object>> updateDatas, String userId, String userName, String userIp) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
//        List<UserPlaceMappingInfo> resultList = new ArrayList<UserPlaceMappingInfo>();
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
            
            List<UserPlaceMappingInfo> upmiDatas = transferData2Upmi(updateType, updateDatas, userId, userName, userIp);
            
            if(CollectionUtils.isNotEmpty(upmiDatas)) {
                if("Insert".equals(updateType)) {
                    userPlaceMappingInfoRepository.save(upmiDatas);
                    
                } else {
                    for(UserPlaceMappingInfo upmi : upmiDatas) {
                        if("Update".equals(updateType)) {
                            userPlaceMappingInfoRepository.updateData(upmi.getUpmiId(), upmi.getManager(), upmi.getManagerId(), upmi.getExaminer1(), upmi.getExaminer1Id(), upmi.getExaminer2(), upmi.getExaminer2Id(), upmi.getIsNeedDailyCheckFire(), upmi.getIsEnabled(), upmi.getUnitCode(), userId, userName, userIp);
                        
                        } else if("Delete".equals(updateType)) {
                            userPlaceMappingInfoRepository.delete(upmi.getUpmiId());
                            
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
            
//            resultList = userPlaceMappingInfoRepository.findAll();
            
            resultMap.put("success", resultStr);
            resultMap.put("msg", msg);
//            resultMap.put("resultList", resultList);
        }
        
        return resultMap;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.UserPlaceMappingService#getPlaceTypeList()
     */
    @Override
    public List<String> getPlaceTypeList() {
        return userPlaceMappingInfoRepository.queryPlaceTypes();
    }

}
