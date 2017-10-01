package proj.ctworld.ctss.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import proj.ctworld.ctss.orm.FireCheckItem;
import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.orm.dto.FireCheckRecordDto;

/**
 * 【程式名稱】: FireCheckService <br/>
 * 【功能名稱】: Service for 安檢登錄/調閱 <br/>
 * 【功能說明】: 執行 安檢登錄/調閱 相關動作 <br/>
 * 【建立日期】: 2017/07/25 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
public interface FireCheckService {

    /**
     * getCheckType 說明: 取得自行檢查記錄設定表(fire_check_item)中的記錄別.<br/>
     * <br/>   
     * @return List<FireCheckItem>
     * @author JoyceLai 2017/07/25
     */
    public List<FireCheckItem> getCheckType(String place);
    
    /**
     * getCheckFrequenceMonthlyByCheckType 說明: 依據記錄別取得自行檢查記錄設定表(fire_check_item)中每月安檢頻率.<br/>
     * <br/>   
     * @param checkType 記錄別
     * @return long
     * @author JoyceLai 2017/07/26
     */
    public long getCheckFrequenceMonthlyByCheckType(String checkType);
    
    /**
     * getCheckYear 說明: 取得檢查記錄表(fire_check_record)中的檢查年.<br/>
     * 1. 再加入本年度與下一年度的西元年值<br/>  
     * @param userType  使用者身份 
     * @param placeId   場所Id
     * @param checkType 記錄別
     * @return List<String>
     * @author JoyceLai 2017/07/27
     */
    public List<String> getCheckYear(String userType, String placeId, String checkType);
    
    
    /**
     * getCheckMonth 說明: 依據條件取得檢查記錄表(fire_check_record)中的檢查月.<br/>
     * <br/>
     * @param userType  使用者身份 
     * @param placeId   場所Id
     * @param checkType 記錄別
     * @param checkYear 年度
     * @return List<String>
     * @author JoyceLai 2017/08/03
     */
    public List<String> getCheckMonth(String userType, String placeId, String checkType, String checkYear);
    
    /**
     * getCheckDate 說明: 依據條件查詢取得自行檢查記錄設定表(fire_check_item)中的檢查日期.<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @param checkTimesMark    當月次數
     * @return String
     * @author JoyceLai 2017/07/27
     */
    public String getCheckDate(String checkYear, String checkMonth, String placeId, String checkType, long checkTimesMark);
    
    /**
     * getCheckRecordData 說明: 依據條件查詢取得自行檢查記錄設定表(fire_check_item)記錄.<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @param checkTimesMark    當月次數
     * @param checkDate         檢查日期
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/7/28
     */
    public List<FireCheckRecord> getCheckRecordData(String checkYear, String checkMonth, String placeId, String checkType, long checkTimesMark, String checkDate);
    
    /**
     * queryCheckItemAndCreateRecord 說明: 依據記錄別查詢檢查記錄設定表(fire_check_item)後新增資料到檢查記錄表(fire_check_record).<br/>
     * <br/>   
     * @param checkType 記錄別
     * @param recordData   record資料
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/07/28
     */
    public List<FireCheckRecord> getCheckItemAndCreateRecord(String checkType, FireCheckRecord recordData);
    
    /**
     * getUserPlaceMappingInfoByUserIdPlaceId 說明: 依據使用者Id與場所Id取得場所人員對照表(user_place_mapping_info)資料.<br/>
     * <br/>   
     * @param userId    使用者Id
     * @param placeId   場所Id
     * @return UserPlaceMappingInfo
     * @author JoyceLai 2017/07/28
     */
    public UserPlaceMappingInfo getUserPlaceMappingInfoByUserIdPlaceId(String userId, String placeId);
    
    /**
     * getCheckRecordDatas 說明: 組合欲新增或更新到檢查記錄表中的資料.<br/>
     * <br/>   
     * @param userId            使用者 Id
     * @param userName          使用者姓名
     * @param userIp            使用者 Ip
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @param checkTimesMark    當月次數
     * @param checkDate         檢查日期
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/07/29
     */
    public List<FireCheckRecord> getCheckRecordDatas(String userId, String userName, String userIp, String checkYear, String checkMonth, String placeId, String checkType, long checkTimesMark, String checkDate) ;
    
    /**
     * insertCheckRecordData 說明: 寫入資料到檢查記錄表(fire_check_record)中.<br/>
     * <br/>   
     * @param allCheckDatas 所有檢查記錄(檢查記錄設定)
     * @param updateDatas   異動的檢查記錄資料
     * @param managerSign   主管簽章
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/07/29
     */
    public List<FireCheckRecord> insertCheckRecordData(List<FireCheckRecord> allCheckDatas, List<Map<String, Object>> updateDatas, String managerSign);

    /**
     * getFirePreventionFacilities 說明: 取得防火避難設施檢查記錄表.<br/>
     * <br/>   
     * @param placeId       場所Id
     * @param checkType     記錄別
     * @param checkYear     檢查年
     * @param checkMonth    檢查月
     * @return List<FireCheckRecordDto>
     * @author JoyceLai 2017/07/30
     */
    public List<FireCheckRecordDto> getFirePreventionFacilities(String placeId, String checkType, String checkYear, String checkMonth);
    
    /**
     * getFireSafetyEquipment 說明: 取得消防安全設備檢查記錄表.<br/>
     * <br/>   
     * @param placeId       場所Id
     * @param checkType     記錄別
     * @param checkYear     檢查年
     * @param checkMonth    檢查月
     * @return List<FireCheckRecordDto>
     * @author JoyceLai 2017/07/31
     */
    public List<FireCheckRecordDto> getFireSafetyEquipment(String placeId, String checkType, String checkYear, String checkMonth);
    
    /**
     * getDailyFireSource 說明: 取得日常火源檢查記錄表.<br/>
     * <br/>   
     * @param placeId       場所Id
     * @param checkType     記錄別
     * @param checkYear     檢查年
     * @param checkMonth    檢查月
     * @return List<FireCheckRecordDto>
     * @author JoyceLai 2017/08/07
     */
    public List<FireCheckRecordDto> getDailyFireSource(String placeId, String checkType, String checkYear, String checkMonth);
    
    /**
     * updateCheckRecordData 說明: 更新資料到檢查記錄表(fire_check_record)中.<br/>
     * <br/>   
     * @param oldCheckDatas         既有的資料
     * @param updateDatas           更新的資料
     * @param managerSign           主管簽章
     * @param userId                使用者Id
     * @param userName              使用者姓名
     * @param userIp                使用者Ip
     * @return 
     * @author JoyceLai 2017/08/02
     */
    public void updateCheckRecordData(List<FireCheckRecord> oldCheckDatas, List<Map<String, Object>> updateDatas, String managerSign, String userId, String userName, String userIp);

    /**
     * createExcelContent 說明: 建立 Excel 內容.<br/>
     * <br/>   
     * @param dataMap
     * @return Workbook
     * @author JoyceLai 2017/08/04
     */
    public HSSFWorkbook createExcelContent(Map<String, Object> dataMap);
    
    /**
     * getExcelData1 說明: 取得匯出Excel的相關資料內容(防火避難設施).<br/>
     * <br/>   
     * @param checkYear             檢查年
     * @param checkMonth            檢查月
     * @param placeId               場所Id
     * @param place                 場所
     * @param checkType             記錄別
     * @param checkTimesMark        當月次數
     * @param managerSign           主管簽章
     * @return Map<String,Object>
     * @author JoyceLai 2017/08/04
     */
    public Map<String, Object> getExcelData1(String checkYear, String checkMonth, String placeId, String place, String checkType, long checkTimesMark, String managerSign);
    
    /**
     * getExcelData2 說明: 取得匯出Excel的相關資料內容(防火避難設施、電器安全).<br/>
     * <br/>   
     * @param checkYear             檢查年
     * @param checkMonth            檢查月
     * @param placeId               場所Id
     * @param place                 場所
     * @param checkType             記錄別
     * @param checkTimesMark        當月次數
     * @param managerSign           主管簽章
     * @return Map<String,Object>
     * @author JoyceLai 2017/08/04
     */
    public Map<String, Object> getExcelData2(String checkYear, String checkMonth, String placeId, String place, String checkType, long checkTimesMark, String managerSign);
    
    /**
     * getExcelData4 說明: 取得匯出Excel的相關資料內容(日常火源).<br/>
     * <br/>   
     * @param checkYear
     * @param checkMonth
     * @param placeId
     * @param place
     * @param checkType
     * @param managerSign
     * @return Map<String,Object>
     * @author JoyceLai 2017/8/7
     */
    public Map<String, Object> getExcelData4(String checkYear, String checkMonth, String placeId, String place, String checkType, String managerSign);
    
    /**
     * getCheckTimesMarks 說明: 依據場所、記錄別、檢查年月取得當月次數List.<br/>
     * <br/>   
     * @param placeId       場所Id
     * @param checkType     記錄別
     * @param checkYear     檢查年
     * @param checkMonth    檢查月
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/09/03
     */
    public List<FireCheckRecord> getCheckTimesMarks(String placeId, String checkType, String checkYear, String checkMonth);
}
