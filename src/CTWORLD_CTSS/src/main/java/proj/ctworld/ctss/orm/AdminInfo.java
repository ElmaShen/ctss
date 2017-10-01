package proj.ctworld.ctss.orm;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "admin_info")
public class AdminInfo implements Serializable {

    private static final long serialVersionUID = -5328404719101570670L;
    
    private Long aiId;
	private String userId;
	private String userName;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "aiId", unique = true, nullable = false)
	/**
	 * @return the aiId
	 */
	public Long getAiId() {
		return aiId;
	}
	/**
	 * @param aiId the aiId to set
	 */
	public void setAiId(Long aiId) {
		this.aiId = aiId;
	}
	
	@Column(name = "userId", unique = true, nullable = false, length=20)
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
	
	@Column(name = "userName", unique = true, nullable = false, length=20)
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
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminInfo ai = (AdminInfo) o;

        return aiId != null ? aiId.equals(ai.aiId) : ai.aiId == null;
    }

    @Override
    public int hashCode() {
        return aiId != null ? aiId.hashCode() : 0;
    }
}
