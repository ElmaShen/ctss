package proj.ctworld.core.service;

import java.util.List;

import proj.ctworld.core.orm.ImageStorage;

public interface ImageStorageService {

	public ImageStorage insertImage(Long relatedId, String relatedType, byte[] file, String filename, Long auId) throws Exception;
	
	public ImageStorage updateImage(byte[] file, String filename, Long relatedId, String relatedType, Long auId) throws Exception;
	
	public void deleteImage(Long relatedId, String relatedType);
	
	public String getLocationLarge(Long isId);
	
	public String getLocationMedium(Long isId);
	
	public String getLocationThumbnail(Long isId);
	
	public List<ImageStorage> getByRelatedId(Long relatedId, String relatedType);
}
