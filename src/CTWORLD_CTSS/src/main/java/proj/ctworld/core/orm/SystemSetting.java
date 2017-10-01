package proj.ctworld.core.orm;
// Generated by Hibernate Tools 4.3.5.Final

import java.util.*;
import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

/**
 * SystemMessage generated by hbm2java
 */
@Entity
@Table(name = "system_setting")
public class SystemSetting implements java.io.Serializable {
	
	private Long ssId;
	private String ssMode;
	private String ssCode;
	private String ssType;
	private String ssName;
	private String ssValue;
	private String ssTempValue;
	private long ssCreator;
	private String ssCreatorType;
	private DateTime ssCreationTime;
	private long ssMdyer;
	private String ssMdyerType;
	private DateTime ssLastUpdateTime;
	private String ssDescription;

	public SystemSetting() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ss_id", unique = true, nullable = false)
	public Long getSsId() {
		return this.ssId;
	}

	public void setSsId(Long ssId) {
		this.ssId = ssId;
	}

	/**
	 * @return the ssMode
	 */
	@Column(name = "ss_mode", nullable = false,length=100)
	public String getSsMode() {
		return ssMode;
	}

	/**
	 * @param ssMode the ssMode to set
	 */
	public void setSsMode(String ssMode) {
		this.ssMode = ssMode;
	}

	/**
	 * @return the ssCode
	 */
	@Column(name = "ss_code", nullable = false,length=100)
	public String getSsCode() {
		return ssCode;
	}

	/**
	 * @param ssCode the ssCode to set
	 */
	public void setSsCode(String ssCode) {
		this.ssCode = ssCode;
	}

	/**
	 * @return the ssType
	 */
	@Column(name = "ss_type", nullable = false,length=100)
	public String getSsType() {
		return ssType;
	}

	/**
	 * @param ssType the ssType to set
	 */
	public void setSsType(String ssType) {
		this.ssType = ssType;
	}

	/**
	 * @return the ssName
	 */
	@Column(name = "ss_name", nullable = false,length=1000)
	public String getSsName() {
		return ssName;
	}

	/**
	 * @param ssName the ssName to set
	 */
	public void setSsName(String ssName) {
		this.ssName = ssName;
	}

	/**
	 * @return the ssValue
	 */
	@Column(name = "ss_value", nullable = true,length=1000)
	public String getSsValue() {
		return ssValue;
	}

	/**
	 * @param ssValue the ssValue to set
	 */
	public void setSsValue(String ssValue) {
		this.ssValue = ssValue;
	}
	
	/**
	 * @return the ssTempValue
	 */
	@Column(name = "ss_temp_value", nullable = false,length=1000)
	public String getSsTempValue() {
		return ssTempValue;
	}

	/**
	 * @param ssTempValue the ssTempValue to set
	 */
	public void setSsTempValue(String ssTempValue) {
		this.ssTempValue = ssTempValue;
	}

	/**
	 * @return the ssCreator
	 */
	@Column(name = "ss_creator", nullable = false)
	public long getSsCreator() {
		return ssCreator;
	}

	/**
	 * @param ssCreator the ssCreator to set
	 */
	public void setSsCreator(long ssCreator) {
		this.ssCreator = ssCreator;
	}

	/**
	 * @return the ssCreatorType
	 */
	@Column(name = "ss_creation_type", nullable = false,length=40)
	public String getSsCreatorType() {
		return ssCreatorType;
	}

	/**
	 * @param ssCreatorType the ssCreatorType to set
	 */
	public void setSsCreatorType(String ssCreatorType) {
		this.ssCreatorType = ssCreatorType;
	}

	/**
	 * @return the ssCreationTime
	 */
	@Column(name = "ss_creation_time", nullable = false)
	public DateTime getSsCreationTime() {
		return ssCreationTime;
	}

	/**
	 * @param ssCreationTime the ssCreationTime to set
	 */
	public void setSsCreationTime(DateTime ssCreationTime) {
		this.ssCreationTime = ssCreationTime;
	}

	/**
	 * @return the ssMdyer
	 */
	@Column(name = "ss_mdyer", nullable = false,length=40)
	public long getSsMdyer() {
		return ssMdyer;
	}

	/**
	 * @param ssMdyer the ssMdyer to set
	 */
	public void setSsMdyer(long ssMdyer) {
		this.ssMdyer = ssMdyer;
	}

	/**
	 * @return the ssMdyerType
	 */
	@Column(name = "ss_mdyer_type", nullable = false,length=40)
	public String getSsMdyerType() {
		return ssMdyerType;
	}

	/**
	 * @param ssMdyerType the ssMdyerType to set
	 */
	public void setSsMdyerType(String ssMdyerType) {
		this.ssMdyerType = ssMdyerType;
	}

	/**
	 * @return the ssLastUpdateTime
	 */
	@Column(name = "ss_last_update_time", nullable = false)
	public DateTime getSsLastUpdateTime() {
		return ssLastUpdateTime;
	}

	/**
	 * @param ssLastUpdateTime the ssLastUpdateTime to set
	 */
	public void setSsLastUpdateTime(DateTime ssLastUpdateTime) {
		this.ssLastUpdateTime = ssLastUpdateTime;
	}

	/**
	 * @return the ssDescription
	 */
	@Column(name = "ss_description", nullable = false,length=1000)
	public String getSsDescription() {
		return ssDescription;
	}

	/**
	 * @param ssDescription the ssDescription to set
	 */
	public void setSsDescription(String ssDescription) {
		this.ssDescription = ssDescription;
	}



}