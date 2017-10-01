package proj.ctworld.ctss.orm;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import proj.ctworld.core.lib.MyLocalDateTimeDeserializer;
import proj.ctworld.core.lib.MyLocalDateTimeSerializer;


@Entity
@Table(name = "fire_check_record")
public class FireCheckRecord implements Serializable {

    private static final long serialVersionUID = 3039779488352551071L;
    
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
	
	public FireCheckRecord(){};
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "fcrId", unique = true, nullable = false)
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
	
	
	@Column(name = "placeId",nullable = false, length = 6)
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
	
	
	@Column(name = "placeType",nullable = false, length = 10)
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
	
	
	@Column(name = "place",nullable = false, length = 100)
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
	
	@Column(name = "manager",nullable = false, length = 20)
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
	
	@Column(name = "managerId",nullable = false, length = 20)
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
	
	@Column(name = "examiner1",nullable = false, length = 20)
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
	
	@Column(name = "examiner1Id",nullable = false, length = 20)
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
	
	@Column(name = "examiner2",nullable = false, length = 20)
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
	
	@Column(name = "examiner2Id",nullable = false, length = 20)
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
	
	@Column(name = "checkType",nullable = false, length = 10)
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
	
	@Column(name = "checkFrequenceMonthly",nullable = false)
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
	
	@Column(name = "checkId",nullable = false, length = 6)
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
	
	@Column(name = "deviceType",nullable = false, length = 30)
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
	
	@Column(name = "checkContent",nullable = false, length = 200)
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
	
	@Column(name = "checkDate",nullable = false)
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
	
	@Column(name = "checkYear",nullable = false, length = 4)
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
	
	@Column(name = "checkMonth",nullable = false, length = 2)
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
	
	@Column(name = "checkSymbol",nullable = true, length = 1)
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
	
	@Column(name = "checkResult",nullable = true, length = 200)
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
	
	@Column(name = "checkTimesMark",nullable = false)
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
	
	@Column(name = "ctNote",nullable = true, length = 200)
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
	
	@Column(name = "isWaitUpdateToSummary",nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	/**
	 * @return the isWaitUpdateToSummary
	 */
	public boolean getIsWaitUpdateToSummary() {
		return isWaitUpdateToSummary;
	}
	/**
	 * @param isWaitUpdateToSummary the isWaitUpdateToSummary to set
	 */
	public void setIsWaitUpdateToSummary(boolean isWaitUpdateToSummary) {
		this.isWaitUpdateToSummary = isWaitUpdateToSummary;
	}
	
	@Column(name = "managerSign",nullable = true, length = 10)
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
	
	@Column(name = "checkStatus",nullable = true, length = 10)
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
	
	@Column(name = "createDt",nullable = false)
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
	
	@Column(name = "creatorName",nullable = false, length = 60)
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
	
	@Column(name = "creatorId",nullable = false, length = 20)
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
	
	@Column(name = "creatorIp",nullable = false, length = 16)
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
	
	@Column(name = "updateDt",nullable = false)
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
	
	@Column(name = "updatorName",nullable = false, length = 60)
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
	
	@Column(name = "updatorId",nullable = false, length = 20)
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
	
	@Column(name = "updatorIp",nullable = false, length = 16)
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

	private String checkDateStr;
    private String createDtStr;
    private String updateDtStr;

    private static final String FORMATTER_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMATTER_YYYYMMDD = "yyyy-MM-dd";
    
    @Transient
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

    @Transient
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

    @Transient
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FireCheckRecord fcr = (FireCheckRecord) o;

        return fcrId != null ? fcrId.equals(fcr.fcrId) : fcr.fcrId == null;
    }

    @Override
    public int hashCode() {
        return fcrId != null ? fcrId.hashCode() : 0;
    }
    
}
