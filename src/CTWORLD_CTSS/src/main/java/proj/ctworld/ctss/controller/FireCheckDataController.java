package proj.ctworld.ctss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.FireCheckItem;
import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.orm.dto.FireCheckRecordDto;
import proj.ctworld.ctss.service.AdminAuthService;
import proj.ctworld.ctss.service.FireCheckService;

/**
 * 【程式名稱】: FireCheckDataController <br/>
 * 【功能名稱】: <br/>
 * 【功能說明】: <br/>
 * 【建立日期】: 2017/07/26 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
@RestController
@RequestMapping(value = "/api/admin/fireCheck")
public class FireCheckDataController {
    private static Logger logger = Logger.getLogger(FireCheckDataController.class);
    
    @Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private FireCheckService fireCheckService;

    /**
     * getCheckTypeList 說明: 依據場所取得記錄別清單資料.<br/>
     * <br/>   
     * @param placeId
     * @return List<FireCheckItem>
     * @author JoyceLai 2017/7/26
     */
    @RequestMapping(value = "/checkTypeList/{placeId}", method = RequestMethod.POST)
    @ResponseBody
    public List<FireCheckItem> getCheckTypeList(@PathVariable String placeId) {
        logger.debug("### getCheckTypeList: placeId=" + placeId);
        List<FireCheckItem> resultList = fireCheckService.getCheckType(placeId);
        
        return resultList;
    }
    
    /**
     * getCheckFrequenceMonthly 說明: 依據記錄別取得每月安檢頻率.<br/>
     * <br/>   
     * @param checkType 記錄別
     * @return long
     * @author JoyceLai 2017/7/26
     */
    @RequestMapping(value = "/checkFrequenceMonthly/{checkType}", method = RequestMethod.POST)
    @ResponseBody
    public long getCheckFrequenceMonthly(@PathVariable String checkType) {
        return fireCheckService.getCheckFrequenceMonthlyByCheckType(checkType);
        
    }
    
    /**
     * getCheckYear 說明: 取得檢查記錄中的檢查年清單資料.<br/>
     * 1. 檢查年再加入本年度與下一年度西元年<br/>   
     * @return List<String>
     * @author JoyceLai 2017/07/27
     */
    @RequestMapping(value = "/checkYearList", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getCheckYear(@RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType) {
        Long token = authService.getToken(request);
        AdminEmpSession aes = authService.getAdminEmpSession(token);
        
        List<String> resultList = fireCheckService.getCheckYear(aes.getAesUserType(), placeId, checkType);
        logger.debug("getCheckYear() resultList="+ resultList);
        
        return resultList;
    }
    
    /**
     * getCheckMonth 說明: 取得檢查記錄中的檢查月清單資料.<br/>
     * <br/>   
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/08/03
     */
    @RequestMapping(value = "/checkMonthList", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getCheckMonth(@RequestParam(value="checkYear") String checkYear, @RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType) {
        Long token = authService.getToken(request);
        AdminEmpSession aes = authService.getAdminEmpSession(token);
        
        List<String> resultList = fireCheckService.getCheckMonth(aes.getAesUserType(), placeId, checkType, checkYear);
        logger.debug("getCheckMonth() resultList="+ resultList);
        
        return resultList;
    }
    
    /**
     * getCheckDate 說明: 取得檢查記錄中的檢查日期清單資料.<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @param checkTimesMark    當月次數
     * @return String
     * @author JoyceLai 2017/07/27
     */
    @RequestMapping(value = "/checkDateList", method = RequestMethod.POST)
    @ResponseBody
    public String[] getCheckDate(@RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth, @RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType, @RequestParam(value="checkTimesMark") long checkTimesMark) {
        logger.debug("### getCheckDate: checkYear, checkMonth, placeId, checkType, checkTimesMark=" + checkYear+","+checkMonth+","+placeId+","+checkType+","+checkTimesMark);
        String checkDate= fireCheckService.getCheckDate(checkYear, checkMonth, placeId, checkType, checkTimesMark);
        String[] result = new String[]{checkDate};
        return result;
    }
    
    /**
     * getCheckRecordData 說明: 依據檢查日期取得相關檢查記錄.<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @param checkTimesMark    當月次數
     * @param checkDate         檢查日期
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/07/27
     */
    @RequestMapping(value = "/checkRecord", method = RequestMethod.POST)
    @ResponseBody
    public List<FireCheckRecord> getCheckRecordData(@RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth, @RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType, @RequestParam(value="checkTimesMark") long checkTimesMark, @RequestParam(value="checkDate") String checkDate) {
        logger.debug("### getCheckRecordData: checkYear, checkMonth, placeId, checkType, checkTimesMark, checkDate=" + checkYear+","+checkMonth+","+placeId+","+checkType+","+checkTimesMark+","+checkDate);
        List<FireCheckRecord> resultList = fireCheckService.getCheckRecordData(checkYear, checkMonth, placeId, checkType, checkTimesMark, checkDate);
        for(FireCheckRecord fcr : resultList) {
            fcr.setUpdateDtYYYYMMDDHHMMSS(fcr.getUpdateDt());
        }
        
        return resultList;
    }
    
    /**
     * queryAndCreateCheckRecordData 說明: 新建檢查記錄.<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param place             記錄別
     * @param checkTimesMark    當月次數
     * @param checkDate         檢查日期
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/07/28
     */
    @RequestMapping(value = "/createNewRecord", method = RequestMethod.POST)
    @ResponseBody
    public List<FireCheckRecord> queryAndCreateCheckRecordData(@RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth, @RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType, @RequestParam(value="checkTimesMark") long checkTimesMark, @RequestParam(value="checkDate") String checkDate) {
        logger.debug("### queryAndCreateCheckRecordData: checkYear, checkMonth, placeId, checkType, checkTimesMark, checkDate=" + checkYear+","+checkMonth+","+placeId+","+checkType+","+checkTimesMark+","+checkDate);
        List<FireCheckRecord> resultList = new ArrayList<FireCheckRecord>();
        FireCheckRecord record = new FireCheckRecord();
                
        Long token = authService.getToken(request);
        AdminEmpSession aes = authService.getAdminEmpSession(token);
        UserPlaceMappingInfo userPlaceInfo = fireCheckService.getUserPlaceMappingInfoByUserIdPlaceId(aes.getAesUserId(), placeId);
        record.setPlaceType(userPlaceInfo.getPlaceType());
        record.setManagerId(userPlaceInfo.getManagerId());
        record.setManager(userPlaceInfo.getManager());
        record.setExaminer1Id(userPlaceInfo.getExaminer1Id());
        record.setExaminer1(userPlaceInfo.getExaminer1());
        record.setExaminer2Id(userPlaceInfo.getExaminer2Id());
        record.setExaminer2(userPlaceInfo.getExaminer2());
        
        record.setCheckDate(LocalDateTime.parse(checkDate));
        record.setCheckYear(checkYear);
        record.setCheckMonth(checkMonth);
        record.setPlaceId(placeId);
        record.setPlace(userPlaceInfo.getPlace());
        record.setCheckTimesMark(checkTimesMark);
        
        LocalDateTime currentTime = LocalDateTime.now();
        String localIp = request.getRemoteAddr();
        record.setCreateDt(currentTime);
        record.setCreatorId(aes.getAesUserId());
        record.setCreatorName(aes.getAesUserName());
        record.setCreatorIp(localIp);
        record.setUpdateDt(currentTime);
        record.setUpdatorId(aes.getAesUserId());
        record.setUpdatorName(aes.getAesUserName());
        record.setUpdatorIp(localIp);
        
        resultList = fireCheckService.getCheckItemAndCreateRecord(checkType, record);
        
        return resultList;
    }
    
    /**
     * createNewRecordAndSave 說明: 儲存檢查記錄表.<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @param checkTimesMark    當月次數
     * @param checkDate         檢查日期
     * @param managerSign       主管簽章
     * @param updateDatas       更新的資料
     * @return Map<String,Object>
     * @author JoyceLai 2017/7/30
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/saveAndCreateNewRecord", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createNewRecordAndSave(@RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth, @RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType, @RequestParam(value="checkTimesMark") long checkTimesMark, @RequestParam(value="checkDate") String checkDate, @RequestParam(value="managerSign") String managerSign, @RequestParam(value="recordDatas") String updateDatas) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ObjectMapper objMapper = new ObjectMapper();
        List<Map<String, Object>> updateRecordDataList = null;
        List<FireCheckRecord> resultRecordList = new ArrayList<FireCheckRecord>();
        boolean result = false;
        String msg = "";
        String resultStr = "";
        try {
            updateRecordDataList = objMapper.readValue(updateDatas, List.class);
            Long token = authService.getToken(request);
            AdminEmpSession aes = authService.getAdminEmpSession(token);
            
            List<FireCheckRecord> oldResultList = fireCheckService.getCheckRecordData(checkYear, checkMonth, placeId, checkType, checkTimesMark, checkDate);
            
            // Create Or Update
            if(CollectionUtils.isEmpty(oldResultList)) {
                List<FireCheckRecord> allCheckDatas = fireCheckService.getCheckRecordDatas(aes.getAesUserId(), aes.getAesUserName(), request.getRemoteAddr(), checkYear, checkMonth, placeId, checkType, checkTimesMark, checkDate); 
                resultRecordList = fireCheckService.insertCheckRecordData(allCheckDatas, updateRecordDataList, managerSign);
            } else {
                fireCheckService.updateCheckRecordData(oldResultList, updateRecordDataList, managerSign, aes.getAesUserId(), aes.getAesUserName(), request.getRemoteAddr());
                resultRecordList = fireCheckService.getCheckRecordData(checkYear, checkMonth, placeId, checkType, checkTimesMark, checkDate);
            }
            
            result = true;
            
        } catch (Exception e) {
            result = false;
            msg = "儲存失敗，錯誤訊息：\n" + e.getMessage();
            e.printStackTrace();
            logger.debug("createNewRecordAndSave Error: " + e.getMessage());
            
        } finally {
            if(result) {
                resultStr = "Y";
                msg = "儲存成功";
            } else {
                resultStr = "N";
            }
            resultMap.put("success", resultStr);
            resultMap.put("msg", msg);
            resultMap.put("resultList", resultRecordList);
        }
        
        return resultMap;
    }
    
    /**
     * getExportData1 說明: .<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @param checkTimesMark    當月次數
     * @param checkDate         檢查日期
     * @return Map<String,Object>
     * @author JoyceLai 2017/07/30
     */
    @RequestMapping(value = "/export1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getExportData1(@RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth, @RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType, @RequestParam(value="checkTimesMark") long checkTimesMark, @RequestParam(value="checkDate") String checkDate) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<FireCheckRecordDto> fcrDtoList = fireCheckService.getFirePreventionFacilities(placeId, checkType, checkYear, checkMonth);
        String managerSign = "";
        String manager = "";
        String checkDate1Str = "";
        String checkDate2Str = "";
        
        if(!CollectionUtils.isEmpty(fcrDtoList)) {
            FireCheckRecordDto record = fcrDtoList.get(0);
            manager = StringUtils.isEmpty(record.getManager()) ? "" : record.getManager();
            checkDate1Str = StringUtils.isEmpty(record.getCheckDate1Str()) ? "" : record.getCheckDate1Str();
            checkDate2Str = StringUtils.isEmpty(record.getCheckDate2Str()) ? "" : record.getCheckDate2Str();
            managerSign = record.getManagerSign();
        }

        resultMap.put("managerSign", managerSign);
        resultMap.put("manager", manager);
        resultMap.put("checkDate1Str", checkDate1Str);
        resultMap.put("checkDate2Str", checkDate2Str);
        resultMap.put("resultList", fcrDtoList);
        
        return resultMap;
    }
   
    /**
     * getExportData1 說明: .<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @param checkTimesMark    當月次數
     * @param checkDate         檢查日期
     * @return Map<String,Object>
     * @author JoyceLai 2017/07/30
     */
    @RequestMapping(value = "/export2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getExportData2(@RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth, @RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType, @RequestParam(value="checkTimesMark") long checkTimesMark, @RequestParam(value="checkDate") String checkDate) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String managerSign = "";
        String manager = "";
        String checkDateStr = "";
        
        List<FireCheckRecordDto> fcrDtoList = fireCheckService.getFireSafetyEquipment(placeId, checkType, checkYear, checkMonth);
        if(!CollectionUtils.isEmpty(fcrDtoList)) {
            FireCheckRecordDto fcrDto = fcrDtoList.get(0);
            manager = fcrDto.getManager();
            checkDateStr = fcrDto.getCheckDateYYYYMMDD();
            managerSign = StringUtils.isEmpty(fcrDto.getManagerSign()) ? "" : fcrDto.getManagerSign();
        }

        resultMap.put("managerSign", managerSign);
        resultMap.put("manager", manager);
        resultMap.put("checkDateYYYYMMDD", checkDateStr);
        resultMap.put("resultList", fcrDtoList);
        
        return resultMap;
    }    
    
    /**
     * getExportData4 說明: .<br/>
     * <br/>   
     * @param checkYear         檢查年
     * @param checkMonth        檢查月
     * @param placeId           場所Id
     * @param checkType         記錄別
     * @return Map<String,Object>
     * @author JoyceLai 2017/8/7
     */
    @RequestMapping(value = "/export4", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getExportData4(@RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth, @RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String managerSign = "";
        String manager = "";
        
        
        List<FireCheckRecordDto> fcrDtoList = fireCheckService.getDailyFireSource(placeId, checkType, checkYear, checkMonth);
        if(!CollectionUtils.isEmpty(fcrDtoList)) {
            FireCheckRecordDto fcrDto = fcrDtoList.get(0);
            manager = fcrDto.getManager();
            managerSign = StringUtils.isEmpty(fcrDto.getManagerSign()) ? "" : fcrDto.getManagerSign();
        }

        resultMap.put("managerSign", managerSign);
        resultMap.put("manager", manager);
        resultMap.put("resultList", fcrDtoList);
        
        return resultMap;
    }
    
    /**
     * getCheckTimesMarks 說明: 依據場所、記錄別、檢查年月取得當月次數.<br/>
     * <br/>   
     * @param placeId       場所Id
     * @param checkType     記錄別
     * @param checkYear     檢查年
     * @param checkMonth    檢查月
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/09/03
     */
    @RequestMapping(value = "/getCheckTimesMarks", method = RequestMethod.POST)
    @ResponseBody
    public List<FireCheckRecord> getCheckTimesMarks(@RequestParam(value="placeId") String placeId, @RequestParam(value="checkType") String checkType, @RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth) {
        return fireCheckService.getCheckTimesMarks(placeId, checkType, checkYear, checkMonth);
        
    }
}
