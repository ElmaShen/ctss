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
@Table(name = "admin_emp_session")
public class AdminEmpSession implements Serializable {

    private static final long serialVersionUID = -7775544350900863875L;
    
    private Long aesId;
	private String aesUserName;
	private String aesUserType;
	private String aesUserId;
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
	private LocalDateTime aesLoginTime;
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
	private LocalDateTime aesLastAccessTime;
	private String aesIsActive;
	private String aesRemoveReason;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "aesId", unique = true, nullable = false)
	/**
	 * @return the aesId
	 */
	public Long getAesId() {
		return aesId;
	}
	/**
	 * @param aesId the aesId to set
	 */
	public void setAesId(Long aesId) {
		this.aesId = aesId;
	}

	@Column(name = "aesUserName", nullable = false,length=20)
	/**
	 * @return the aesUserName
	 */
	public String getAesUserName() {
		return aesUserName;
	}
	/**
	 * @param aesUserName the aesUserName to set
	 */
	public void setAesUserName(String aesUserName) {
		this.aesUserName = aesUserName;
	}
	
	@Column(name = "aesUserType", nullable = false,length=5)
	/**
	 * @return the aesUserType
	 */
	public String getAesUserType() {
		return aesUserType;
	}
	/**
	 * @param aesUserType the aesUserType to set
	 */
	public void setAesUserType(String aesUserType) {
		this.aesUserType = aesUserType;
	}
	
	@Column(name = "aesUserId", nullable = false,length=20)
	/**
	 * @return the aesUserId
	 */
	public String getAesUserId() {
		return aesUserId;
	}
	/**
	 * @param aesUserId the aesUserId to set
	 */
	public void setAesUserId(String aesUserId) {
		this.aesUserId = aesUserId;
	}
	/**
	 * @return the aesLoginTime
	 */
	public LocalDateTime getAesLoginTime() {
		return aesLoginTime;
	}
	/**
	 * @param aesLoginTime the aesLoginTime to set
	 */
	public void setAesLoginTime(LocalDateTime aesLoginTime) {
		this.aesLoginTime = aesLoginTime;
	}
	/**
	 * @return the aesLastAccessTime
	 */
	public LocalDateTime getAesLastAccessTime() {
		return aesLastAccessTime;
	}
	/**
	 * @param aesLastAccessTime the aesLastAccessTime to set
	 */
	public void setAesLastAccessTime(LocalDateTime aesLastAccessTime) {
		this.aesLastAccessTime = aesLastAccessTime;
	}
	/**
	 * @return the aesIsActive
	 */
	public String getAesIsActive() {
		return aesIsActive;
	}
	/**
	 * @param aesIsActive the aesIsActive to set
	 */
	public void setAesIsActive(String aesIsActive) {
		this.aesIsActive = aesIsActive;
	}
	/**
	 * @return the aesRemoveReason
	 */
	public String getAesRemoveReason() {
		return aesRemoveReason;
	}
	/**
	 * @param aesRemoveReason the aesRemoveReason to set
	 */
	public void setAesRemoveReason(String aesRemoveReason) {
		this.aesRemoveReason = aesRemoveReason;
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminEmpSession aes = (AdminEmpSession) o;

        return aesId != null ? aesId.equals(aes.aesId) : aes.aesId == null;
    }

    @Override
    public int hashCode() {
        return aesId != null ? aesId.hashCode() : 0;
    }
    
    
    
    
    private static final String FORMATTER_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private String aesLoginTimeStr;
    
   
    @Transient
    /**
     * @return the aesLoginTimeStr
     */
    public String getAesLoginTimeYYYYMMDDHHMMSS() {
    	aesLoginTimeStr = getAesLoginTime() == null ? "" : getAesLoginTime().toString(FORMATTER_YYYYMMDDHHMMSS);
        return aesLoginTimeStr;
    }

    /**
     * @param aesLoginTimeStr to set
     */
    public void setAesLoginTimeYYYYMMDDHHMMSS(LocalDateTime aesLoginTimeStr) {
        String dateStr = aesLoginTimeStr == null ? "" : aesLoginTimeStr.toString(FORMATTER_YYYYMMDDHHMMSS);
        this.aesLoginTimeStr = dateStr;
    }
}
