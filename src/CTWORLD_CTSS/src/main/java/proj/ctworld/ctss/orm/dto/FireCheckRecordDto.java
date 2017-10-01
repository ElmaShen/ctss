package proj.ctworld.ctss.orm.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import proj.ctworld.core.lib.MyLocalDateTimeDeserializer;
import proj.ctworld.core.lib.MyLocalDateTimeSerializer;

/**
 * 【程式名稱】: FireCheckRecordDto <br/>
 * 【功能名稱】: <br/>
 * 【功能說明】: <br/>
 * 【建立日期】: 2017/8/3 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
public class FireCheckRecordDto implements Serializable {

    private static final long serialVersionUID = 7671915542667655104L;
    
    private static final String FORMATTER_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMATTER_YYYYMMDD = "yyyy-MM-dd";
    
    private Long fcrId;
    private String placeId;
    private String placeType;
    private String place;
    private String manager;
    private String managerId;
    private String examiner1;
    private String examiner1Id;
    private String examiner2;
    private String examiner2Id;
    private String checkType;
    private long checkFrequenceMonthly;
    private String checkId;
    private String deviceType;
    private String checkContent;
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime checkDate;
    private String checkYear;
    private String checkMonth;
    private String checkSymbol;
    private String checkResult;
    private long checkTimesMark;
    private String ctNote;
    private boolean isWaitUpdateToSummary;
    private String managerSign;
    private String checkStatus;
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime createDt;
    private String creatorName;
    private String creatorId;
    private String creatorIp;
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime updateDt;
    private String updatorName;
    private String updatorId;
    private String updatorIp;
    private String checkDateStr;
    private String createDtStr;
    private String updateDtStr;
    private String checkDate1Str;
    private String checkDate2Str;
    private String checkSymbol1;
    private String checkSymbol2;
    private String checkResult1;
    private String checkResult2;
    private String checkSymbol3;
    private String checkSymbol4;
    private String checkSymbol5;
    private String checkSymbol6;
    private String day;
    private String dayOfWeek;
    

    public FireCheckRecordDto(){};
    public FireCheckRecordDto(Long fcrId, String placeId, String placeType, String place, String manager, String managerId, String examiner1, String examiner1Id, String examiner2, String examiner2Id, String checkType, long checkFrequenceMonthly, String checkId, String deviceType, String checkContent, LocalDateTime checkDate, String checkYear, String checkMonth, String checkSymbol, String checkResult, long checkTimesMark, String ctNote, boolean isWaitUpdateToSummary, String managerSign, String checkStatus, LocalDateTime createDt, String creatorName, String creatorId, String creatorIp, LocalDateTime updateDt, String updatorName, String updatorId, String updatorIp, String checkDateStr, String createDtStr, String updateDtStr, String checkDate1Str, String checkDate2Str, String checkSymbol1, String checkSymbol2, String checkResult1, String checkResult2, String checkSymbol3, String checkSymbol4, String checkSymbol5, String checkSymbol6, String day, String dayOfWeek) {
        this.fcrId = fcrId;
        this.placeId = placeId;
        this.placeType = placeType;
        this.place = place;
        this.manager = manager;
        this.managerId= managerId;
        this.examiner1= examiner1;
        this.examiner1Id= examiner1Id;
        this.examiner2 = examiner2;
        this.examiner2Id= examiner2Id;
        this.checkType = checkType;
        this.checkFrequenceMonthly = checkFrequenceMonthly;
        this.checkId = checkId;
        this.deviceType = deviceType;
        this.checkContent = checkContent;
        this.checkDate = checkDate;
        this.checkYear = checkYear;
        this.checkMonth = checkMonth;
        this.checkSymbol = checkSymbol;
        this.checkResult = checkResult;
        this.checkTimesMark = checkTimesMark;
        this.ctNote = ctNote;
        this.isWaitUpdateToSummary = isWaitUpdateToSummary;
        this.managerSign = managerSign;
        this.checkStatus = checkStatus;
        this.createDt = createDt;
        this.creatorName = creatorName;
        this.creatorId = creatorId;
        this.creatorIp = creatorIp;
        this.updateDt = updateDt;
        this.updatorName = updatorName;
        this.updatorId = updatorId;
        this.updatorIp = updatorIp;
        this.checkDateStr = checkDateStr;
        this.createDtStr = createDtStr;
        this.updateDtStr = updateDtStr;
        this.checkDate1Str = checkDate1Str;
        this.checkDate2Str = checkDate2Str;
        this.checkSymbol1 = checkSymbol1;
        this.checkSymbol2 = checkSymbol2;
        this.checkResult1 = checkResult1;
        this.checkResult2 = checkResult2;
        this.checkSymbol3 = checkSymbol3;
        this.checkSymbol4 = checkSymbol4;
        this.checkSymbol5 = checkSymbol5;
        this.checkSymbol6 = checkSymbol6;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        
    }
    
    public FireCheckRecordDto (String manager, String managerSign,  String checkContent, long checkTimesMark, String updatorName, String checkSymbol1, String checkResult1, String checkDate1Str, String checkSymbol2, String checkResult2, String checkDate2Str) {
        this.manager = manager;
        this.managerSign = managerSign;
        this.checkContent = checkContent;
        this.checkTimesMark = checkTimesMark;
        this.updatorName = updatorName;
        this.checkSymbol1 = checkSymbol1;
        this.checkResult1 = checkResult1;
        this.checkDate1Str = checkDate1Str;
        this.checkSymbol2 = checkSymbol2;
        this.checkResult2 = checkResult2;
        this.checkDate2Str = checkDate2Str;
    }
    
    public FireCheckRecordDto (String manager, String managerSign,  String deviceType, String checkContent, String updatorName, String checkSymbol, String checkResult, LocalDateTime checkDate) {
        this.manager = manager;
        this.managerSign = managerSign;
        this.deviceType = deviceType;
        this.checkContent = checkContent;
        this.updatorName = updatorName;
        this.checkSymbol = checkSymbol;
        this.checkResult = checkResult;
        this.checkDate = checkDate;
    }
    
    public FireCheckRecordDto (String manager, String managerSign, String day, String dayOfWeek, String checkSymbol1, String checkSymbol2, String checkSymbol3, String checkSymbol4, String checkSymbol5, String checkSymbol6, String updatorName ) {
        this.manager = manager;
        this.managerSign = managerSign;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.checkSymbol1 = checkSymbol1;
        this.checkSymbol2 = checkSymbol2;
        this.checkSymbol3 = checkSymbol3;
        this.checkSymbol4 = checkSymbol4;
        this.checkSymbol5 = checkSymbol5;
        this.checkSymbol6 = checkSymbol6;
        this.updatorName = updatorName;
    }
    
    /**
     * @return the fcrId
     */
    public Long getFcrId() {
        return fcrId;
    }

    /**
     * @param fcrId the fcrId to set
     */
    public void setFcrId(Long fcrId) {
        this.fcrId = fcrId;
    }

    /**
     * @return the placeId
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * @param placeId the placeId to set
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * @return the placeType
     */
    public String getPlaceType() {
        return placeType;
    }

    /**
     * @param placeType the placeType to set
     */
    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    /**
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @return the manager
     */
    public String getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * @return the managerId
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * @param managerId the managerId to set
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    /**
     * @return the examiner1
     */
    public String getExaminer1() {
        return examiner1;
    }

    /**
     * @param examiner1 the examiner1 to set
     */
    public void setExaminer1(String examiner1) {
        this.examiner1 = examiner1;
    }

    /**
     * @return the examiner1Id
     */
    public String getExaminer1Id() {
        return examiner1Id;
    }

    /**
     * @param examiner1Id the examiner1Id to set
     */
    public void setExaminer1Id(String examiner1Id) {
        this.examiner1Id = examiner1Id;
    }

    /**
     * @return the examiner2
     */
    public String getExaminer2() {
        return examiner2;
    }

    /**
     * @param examiner2 the examiner2 to set
     */
    public void setExaminer2(String examiner2) {
        this.examiner2 = examiner2;
    }

    /**
     * @return the examiner2Id
     */
    public String getExaminer2Id() {
        return examiner2Id;
    }

    /**
     * @param examiner2Id the examiner2Id to set
     */
    public void setExaminer2Id(String examiner2Id) {
        this.examiner2Id = examiner2Id;
    }

    /**
     * @return the checkType
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * @param checkType the checkType to set
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    /**
     * @return the checkFrequenceMonthly
     */
    public long getCheckFrequenceMonthly() {
        return checkFrequenceMonthly;
    }

    /**
     * @param checkFrequenceMonthly the checkFrequenceMonthly to set
     */
    public void setCheckFrequenceMonthly(long checkFrequenceMonthly) {
        this.checkFrequenceMonthly = checkFrequenceMonthly;
    }

    /**
     * @return the checkId
     */
    public String getCheckId() {
        return checkId;
    }

    /**
     * @param checkId the checkId to set
     */
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    /**
     * @return the deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return the checkContent
     */
    public String getCheckContent() {
        return checkContent;
    }

    /**
     * @param checkContent the checkContent to set
     */
    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    /**
     * @return the checkDate
     */
    public LocalDateTime getCheckDate() {
        return checkDate;
    }

    /**
     * @param checkDate the checkDate to set
     */
    public void setCheckDate(LocalDateTime checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * @return the checkYear
     */
    public String getCheckYear() {
        return checkYear;
    }

    /**
     * @param checkYear the checkYear to set
     */
    public void setCheckYear(String checkYear) {
        this.checkYear = checkYear;
    }

    /**
     * @return the checkMonth
     */
    public String getCheckMonth() {
        return checkMonth;
    }

    /**
     * @param checkMonth the checkMonth to set
     */
    public void setCheckMonth(String checkMonth) {
        this.checkMonth = checkMonth;
    }

    /**
     * @return the checkSymbol
     */
    public String getCheckSymbol() {
        return checkSymbol;
    }

    /**
     * @param checkSymbol the checkSymbol to set
     */
    public void setCheckSymbol(String checkSymbol) {
        this.checkSymbol = checkSymbol;
    }

    /**
     * @return the checkResult
     */
    public String getCheckResult() {
        return checkResult;
    }

    /**
     * @param checkResult the checkResult to set
     */
    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    /**
     * @return the checkTimesMark
     */
    public long getCheckTimesMark() {
        return checkTimesMark;
    }

    /**
     * @param checkTimesMark the checkTimesMark to set
     */
    public void setCheckTimesMark(long checkTimesMark) {
        this.checkTimesMark = checkTimesMark;
    }

    /**
     * @return the ctNote
     */
    public String getCtNote() {
        return ctNote;
    }

    /**
     * @param ctNote the ctNote to set
     */
    public void setCtNote(String ctNote) {
        this.ctNote = ctNote;
    }

    /**
     * @return the isWaitUpdateToSummary
     */
    public boolean isWaitUpdateToSummary() {
        return isWaitUpdateToSummary;
    }

    /**
     * @param isWaitUpdateToSummary the isWaitUpdateToSummary to set
     */
    public void setWaitUpdateToSummary(boolean isWaitUpdateToSummary) {
        this.isWaitUpdateToSummary = isWaitUpdateToSummary;
    }

    /**
     * @return the managerSign
     */
    public String getManagerSign() {
        return managerSign;
    }

    /**
     * @param managerSign the managerSign to set
     */
    public void setManagerSign(String managerSign) {
        this.managerSign = managerSign;
    }

    /**
     * @return the checkStatus
     */
    public String getCheckStatus() {
        return checkStatus;
    }

    /**
     * @param checkStatus the checkStatus to set
     */
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * @return the createDt
     */
    public LocalDateTime getCreateDt() {
        return createDt;
    }

    /**
     * @param createDt the createDt to set
     */
    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the creatorId
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId the creatorId to set
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return the creatorIp
     */
    public String getCreatorIp() {
        return creatorIp;
    }

    /**
     * @param creatorIp the creatorIp to set
     */
    public void setCreatorIp(String creatorIp) {
        this.creatorIp = creatorIp;
    }

    /**
     * @return the updateDt
     */
    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    /**
     * @param updateDt the updateDt to set
     */
    public void setUpdateDt(LocalDateTime updateDt) {
        this.updateDt = updateDt;
    }

    /**
     * @return the updatorName
     */
    public String getUpdatorName() {
        return updatorName;
    }

    /**
     * @param updatorName the updatorName to set
     */
    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    /**
     * @return the updatorId
     */
    public String getUpdatorId() {
        return updatorId;
    }

    /**
     * @param updatorId the updatorId to set
     */
    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    /**
     * @return the updatorIp
     */
    public String getUpdatorIp() {
        return updatorIp;
    }

    /**
     * @param updatorIp the updatorIp to set
     */
    public void setUpdatorIp(String updatorIp) {
        this.updatorIp = updatorIp;
    }

    /**
     * @return the checkDateStr
     */
    public String getCheckDateYYYYMMDD() {
        checkDateStr = getCheckDate() == null ? "" : getCheckDate().toString(FORMATTER_YYYYMMDD);
        
        return checkDateStr;
    }

    /**
     * @param checkDateStr the checkDateStr to set
     */
    public void setCheckDateYYYYMMDD(LocalDateTime checkDateStr) {
        String dateStr = checkDateStr == null ? "" : checkDateStr.toString(FORMATTER_YYYYMMDD);
        this.checkDateStr = dateStr;
    }

    /**
     * @return the createDtStr
     */
    public String getCreateDtYYYYMMDDHHMMSS() {
        createDtStr = getCreateDt() == null ? "" : getCreateDt().toString(FORMATTER_YYYYMMDDHHMMSS);
        return createDtStr;
    }

    /**
     * @param createDtStr the createDtStr to set
     */
    public void setCreateDtYYYYMMDDHHMMSS(LocalDateTime createDtStr) {
        String dateStr = createDtStr == null ? "" : createDtStr.toString(FORMATTER_YYYYMMDDHHMMSS);
        this.createDtStr = dateStr;
    }

    /**
     * @return the updateDtStr
     */
    public String getUpdateDtYYYYMMDDHHMMSS() {
        updateDtStr = getUpdateDt() == null ? "" : getUpdateDt().toString(FORMATTER_YYYYMMDDHHMMSS);
        return updateDtStr;
    }

    /**
     * @param updateDtStr the updateDtStr to set
     */
    public void setUpdateDtYYYYMMDDHHMMSS(LocalDateTime updateDtStr) {
        String dateStr = updateDtStr == null ? "" : updateDtStr.toString(FORMATTER_YYYYMMDDHHMMSS);
        this.updateDtStr = dateStr;
    }

    /**
     * @return the checkDate1Str
     */
    public String getCheckDate1Str() {
        return checkDate1Str;
    }

    /**
     * @param checkDate1Str the checkDate1Str to set
     */
    public void setCheckDate1Str(String checkDate1Str) {
        this.checkDate1Str = checkDate1Str;
    }

    /**
     * @return the checkDate2Str
     */
    public String getCheckDate2Str() {
        return checkDate2Str;
    }

    /**
     * @param checkDate2Str the checkDate2Str to set
     */
    public void setCheckDate2Str(String checkDate2Str) {
        this.checkDate2Str = checkDate2Str;
    }

    /**
     * @return the checkSymbol1
     */
    public String getCheckSymbol1() {
        return checkSymbol1;
    }

    /**
     * @param checkSymbol1 the checkSymbol1 to set
     */
    public void setCheckSymbol1(String checkSymbol1) {
        this.checkSymbol1 = checkSymbol1;
    }

    /**
     * @return the checkSymbol2
     */
    public String getCheckSymbol2() {
        return checkSymbol2;
    }

    /**
     * @param checkSymbol2 the checkSymbol2 to set
     */
    public void setCheckSymbol2(String checkSymbol2) {
        this.checkSymbol2 = checkSymbol2;
    }

    /**
     * @return the checkResult1
     */
    public String getCheckResult1() {
        return checkResult1;
    }

    /**
     * @param checkResult1 the checkResult1 to set
     */
    public void setCheckResult1(String checkResult1) {
        this.checkResult1 = checkResult1;
    }

    /**
     * @return the checkResult2
     */
    public String getCheckResult2() {
        return checkResult2;
    }

    /**
     * @param checkResult2 the checkResult2 to set
     */
    public void setCheckResult2(String checkResult2) {
        this.checkResult2 = checkResult2;
    }
    
    /**
     * @return the checkSymbol3
     */
    public String getCheckSymbol3() {
        return checkSymbol3;
    }
    /**
     * @param checkSymbol3 the checkSymbol3 to set
     */
    public void setCheckSymbol3(String checkSymbol3) {
        this.checkSymbol3 = checkSymbol3;
    }
    /**
     * @return the checkSymbol4
     */
    public String getCheckSymbol4() {
        return checkSymbol4;
    }
    /**
     * @param checkSymbol4 the checkSymbol4 to set
     */
    public void setCheckSymbol4(String checkSymbol4) {
        this.checkSymbol4 = checkSymbol4;
    }
    /**
     * @return the checkSymbol5
     */
    public String getCheckSymbol5() {
        return checkSymbol5;
    }
    /**
     * @param checkSymbol5 the checkSymbol5 to set
     */
    public void setCheckSymbol5(String checkSymbol5) {
        this.checkSymbol5 = checkSymbol5;
    }
    /**
     * @return the checkSymbol6
     */
    public String getCheckSymbol6() {
        return checkSymbol6;
    }
    /**
     * @param checkSymbol6 the checkSymbol6 to set
     */
    public void setCheckSymbol6(String checkSymbol6) {
        this.checkSymbol6 = checkSymbol6;
    }
    /**
     * @return the day
     */
    public String getDay() {
        return day;
    }
    /**
     * @param day the day to set
     */
    public void setDay(String day) {
        this.day = day;
    }
    /**
     * @return the dayOfWeek
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FireCheckRecordDto fcrDto = (FireCheckRecordDto) o;

        return fcrId != null ? fcrId.equals(fcrDto.fcrId) : fcrDto.fcrId == null;
    }

    @Override
    public int hashCode() {
        return fcrId != null ? fcrId.hashCode() : 0;
    }
    
}
