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
import org.joda.time.LocalDateTime;

import proj.ctworld.core.lib.MyLocalDateTimeDeserializer;
import proj.ctworld.core.lib.MyLocalDateTimeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "user_place_mapping_info")
public class UserPlaceMappingInfo implements Serializable {

    private static final long serialVersionUID = -6603076125411287036L;
    
    private Long upmiId;
	private String manager;
	private String managerId;
	private String examiner1;
	private String examiner1Id;
	private String examiner2;
	private String examiner2Id;
	private String placeType;
	private String placeId;
	private String place;
	private long isNeedDailyCheckFire;
	private long isEnabled;
	private String unitCode;
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
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "upmiId", unique = true, nullable = false)
	/**
	 * @return the upmiId
	 */
	public Long getUpmiId() {
		return upmiId;
	}
	/**
	 * @param upmiId the upmiId to set
	 */
	public void setUpmiId(Long upmiId) {
		this.upmiId = upmiId;
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
	
	@Column(name = "isNeedDailyCheckFire",nullable = false)
	/**
	 * @return the isNeedDailyCheckFire
	 */
	public long getIsNeedDailyCheckFire() {
		return isNeedDailyCheckFire;
	}
	/**
	 * @param isNeedDailyCheckFire the isNeedDailyCheckFire to set
	 */
	public void setIsNeedDailyCheckFire(long isNeedDailyCheckFire) {
		this.isNeedDailyCheckFire = isNeedDailyCheckFire;
	}
	
	@Column(name = "isEnabled",nullable = false)
	/**
	 * @return the isEnabled
	 */
	public long getIsEnabled() {
		return isEnabled;
	}
	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setIsEnabled(long isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Column(name = "unitCode",nullable = false,length=3)
	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}
	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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
	
	private boolean needDailyCheckFire;
    private boolean enabled;

    @Transient
    /**
     * @return the needDailyCheckFire
     */
    public boolean isNeedDailyCheckFire() {
        needDailyCheckFire = getIsNeedDailyCheckFire() == 1 ? true : false;
        return needDailyCheckFire;
    }
    /**
     * @param needDailyCheckFire the needDailyCheckFire to set
     */
    public void setNeedDailyCheckFire(boolean needDailyCheckFire) {
        this.needDailyCheckFire = needDailyCheckFire;
    }
    @Transient
    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        enabled = getIsEnabled() == 1 ? true : false;
        return enabled;
    }
    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
   
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPlaceMappingInfo upmi = (UserPlaceMappingInfo) o;

        return upmiId != null ? upmiId.equals(upmi.upmiId) : upmi.upmiId == null;
    }

    @Override
    public int hashCode() {
        return upmiId != null ? upmiId.hashCode() : 0;
    }
    
}
