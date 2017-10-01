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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "announce_set_info")
public class AnnouncementSetInfo implements Serializable {

    private static final long serialVersionUID = -1022549622353437772L;

    private Long asiId;
	
	private String messageId;
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime announcementDt;
	private String announcementSubject;
	private String announcementContent;
	private int isEnabled;

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
	@Column(name = "asiId", unique = true, nullable = false)
	public Long getAsiId() {
		return this.asiId;
	}

	public void setAsiId(Long asiId) {
		this.asiId = asiId;
	}
	
	@Column(name = "messageId", nullable = false,length=9)
	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Column(name = "announceDt", nullable = false)
	/**
	 * @return the announcementDt
	 */
	public LocalDateTime getAnnouncementDt() {
		return announcementDt;
	}

	/**
	 * @param announcementDt the announcementDt to set
	 */
	public void setAnnouncementDt(LocalDateTime announcementDt) {
		this.announcementDt = announcementDt;
	}

	@Column(name = "announceSubject", nullable = false,length=50)
	/**
	 * @return the announcementSubject
	 */
	public String getAnnouncementSubject() {
		return announcementSubject;
	}

	/**
	 * @param announcementSubject the announcementSubject to set
	 */
	public void setAnnouncementSubject(String announcementSubject) {
		this.announcementSubject = announcementSubject;
	}

	@Column(name = "announceContent", nullable = false, length=300)
	/**
	 * @return the announcementContent
	 */
	public String getAnnouncementContent() {
		return announcementContent;
	}

	/**
	 * @param announcementContent the announcementContent to set
	 */
	public void setAnnouncementContent(String announcementContent) {
		this.announcementContent = announcementContent;
	}

	@Column(name = "isEnabled", nullable = false, columnDefinition = "TINYINT", length = 1)
	/**
	 * @return the isEnabled
	 */
	public int getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setIsEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
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
	
	@Column(name = "creatorIp",nullable = false, length = 20)
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
	
	@Column(name = "updatorIp",nullable = false, length = 20)
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
	

	private static final String FORMATTER_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private boolean enabled;
    private String announcementDtStr;
    
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
   
    @Transient
    /**
     * @return the announcementDtStr
     */
    public String getAnnouncementDtYYYYMMDDHHMMSS() {
        announcementDtStr = getAnnouncementDt() == null ? "" : getAnnouncementDt().toString(FORMATTER_YYYYMMDDHHMMSS);
        return announcementDtStr;
    }

    /**
     * @param announcementDtStr the announcementDtStr to set
     */
    public void setAnnouncementDtYYYYMMDDHHMMSS(LocalDateTime announcementDtStr) {
        String dateStr = announcementDtStr == null ? "" : announcementDtStr.toString(FORMATTER_YYYYMMDDHHMMSS);
        this.announcementDtStr = dateStr;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnnouncementSetInfo asi = (AnnouncementSetInfo) o;

        return asiId != null ? asiId.equals(asi.asiId) : asi.asiId == null;
    }

    @Override
    public int hashCode() {
        return asiId != null ? asiId.hashCode() : 0;
    }
}
