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
@Table(name = "fire_check_item")
public class FireCheckItem implements Serializable {

    private static final long serialVersionUID = -2718320028550569728L;
    
    private Long fciId;
	private String checkId;
	private String checkType;
	private String deviceType;
	private String checkContent;
	private int isEnabled;
	private long checkFrequenceMonthly;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "fciId", unique = true, nullable = false)
	/**
	 * @return the fciId
	 */
	public Long getFciId() {
		return fciId;
	}
	/**
	 * @param fciId the fciId to set
	 */
	public void setFciId(Long fciId) {
		this.fciId = fciId;
	}
	
	@Column(name = "checkId", nullable = false, length = 6)
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
	
	@Column(name = "checkType", nullable = false, length = 10)
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
	
	@Column(name = "deviceType", nullable = false, length = 30)
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
	
	@Column(name = "checkContent", nullable = false, length = 200)
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
	
	@Column(name = "checkFrequenceMonthly", nullable = false)
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
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FireCheckItem fci = (FireCheckItem) o;

        return fciId != null ? fciId.equals(fci.fciId) : fci.fciId == null;
    }

    @Override
    public int hashCode() {
        return fciId != null ? fciId.hashCode() : 0;
    }
	
}
