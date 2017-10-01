package proj.ctworld.ctss.orm;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.LocalDateTime;

import proj.ctworld.core.lib.MyLocalDateTimeDeserializer;
import proj.ctworld.core.lib.MyLocalDateTimeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "announce_read_record")
public class AnnouncementReadRecord implements Serializable {

    private static final long serialVersionUID = -6507473878416204786L;
    
    private Long arrId;
	private String messageId;
	private String userId;
	private String userName;

    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
	private LocalDateTime createDt;
	private String creatorIp;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "arrId", unique = true, nullable = false)
	public Long getArrId() {
		return this.arrId;
	}

	public void setArrId(Long arrId) {
		this.arrId = arrId;
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

	@Column(name = "userId", nullable = false,length=20)
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "userName", nullable = false,length=20)
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnnouncementReadRecord arr = (AnnouncementReadRecord) o;

        return arrId != null ? arrId.equals(arr.arrId) : arr.arrId == null;
    }

    @Override
    public int hashCode() {
        return arrId != null ? arrId.hashCode() : 0;
    }
}
