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
 * ImageStorage generated by hbm2java
 */
@Entity
@Table(name = "image_storage")
public class ImageStorage implements java.io.Serializable {

	private Long isId;
	private long isRelatedId;
	private String isRelatedType;
	private String isName;
	private String isLocationLarge;
	private String isLocationMedium;
	private String isLocationThumbnail;
	private long isCreator;
	private String isCreatorType;
	private DateTime isCreationTime;
	private long isMdyer;
	private String isMdyerType;
	private DateTime isLastUpdateTime;

	public ImageStorage() {
	}

	public ImageStorage(long isRelatedId, String isRelatedType, String isName, String isLocationLarge,
			String isLocationMedium, String isLocationThumbnail, long isCreator, String isCreatorType,
			DateTime isCreationTime, long isMdyer, String isMdyerType, DateTime isLastUpdateTime) {
		this.isRelatedId = isRelatedId;
		this.isRelatedType = isRelatedType;
		this.isName = isName;
		this.isLocationLarge = isLocationLarge;
		this.isLocationMedium = isLocationMedium;
		this.isLocationThumbnail = isLocationThumbnail;
		this.isCreator = isCreator;
		this.isCreatorType = isCreatorType;
		this.isCreationTime = isCreationTime;
		this.isMdyer = isMdyer;
		this.isMdyerType = isMdyerType;
		this.isLastUpdateTime = isLastUpdateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "is_id", unique = true, nullable = false)
	public Long getIsId() {
		return this.isId;
	}

	public void setIsId(Long isId) {
		this.isId = isId;
	}

	@Column(name = "is_related_id", nullable = false)
	public long getIsRelatedId() {
		return this.isRelatedId;
	}

	public void setIsRelatedId(long isRelatedId) {
		this.isRelatedId = isRelatedId;
	}

	@Column(name = "is_related_type", nullable = false, length = 50)
	public String getIsRelatedType() {
		return this.isRelatedType;
	}

	public void setIsRelatedType(String isRelatedType) {
		this.isRelatedType = isRelatedType;
	}

	@Column(name = "is_name", nullable = false, length = 200)
	public String getIsName() {
		return this.isName;
	}

	public void setIsName(String isName) {
		this.isName = isName;
	}

	@Column(name = "is_location_large", nullable = false, length = 200)
	public String getIsLocationLarge() {
		return this.isLocationLarge;
	}

	public void setIsLocationLarge(String isLocationLarge) {
		this.isLocationLarge = isLocationLarge;
	}

	@Column(name = "is_location_medium", nullable = false, length = 200)
	public String getIsLocationMedium() {
		return this.isLocationMedium;
	}

	public void setIsLocationMedium(String isLocationMedium) {
		this.isLocationMedium = isLocationMedium;
	}

	@Column(name = "is_location_Thumbnail", nullable = false, length = 200)
	public String getIsLocationThumbnail() {
		return this.isLocationThumbnail;
	}

	public void setIsLocationThumbnail(String isLocationThumbnail) {
		this.isLocationThumbnail = isLocationThumbnail;
	}

	@Column(name = "is_creator", nullable = false)
	public long getIsCreator() {
		return this.isCreator;
	}

	public void setIsCreator(long isCreator) {
		this.isCreator = isCreator;
	}

	@Column(name = "is_creator_type", nullable = false, length = 1)
	public String getIsCreatorType() {
		return this.isCreatorType;
	}

	public void setIsCreatorType(String isCreatorType) {
		this.isCreatorType = isCreatorType;
	}

	@Column(name = "is_creation_time", nullable = false, length = 19)
	public DateTime getIsCreationTime() {
		return this.isCreationTime;
	}

	public void setIsCreationTime(DateTime isCreationTime) {
		this.isCreationTime = isCreationTime;
	}

	@Column(name = "is_mdyer", nullable = false)
	public long getIsMdyer() {
		return this.isMdyer;
	}

	public void setIsMdyer(long isMdyer) {
		this.isMdyer = isMdyer;
	}

	@Column(name = "is_mdyer_type", nullable = false, length = 1)
	public String getIsMdyerType() {
		return this.isMdyerType;
	}

	public void setIsMdyerType(String isMdyerType) {
		this.isMdyerType = isMdyerType;
	}

	@Column(name = "is_last_update_time", nullable = false, length = 19)
	public DateTime getIsLastUpdateTime() {
		return this.isLastUpdateTime;
	}

	public void setIsLastUpdateTime(DateTime isLastUpdateTime) {
		this.isLastUpdateTime = isLastUpdateTime;
	}

}
