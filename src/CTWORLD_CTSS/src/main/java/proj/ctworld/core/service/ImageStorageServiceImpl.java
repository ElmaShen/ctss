package proj.ctworld.core.service;

import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import proj.ctworld.core.lib.Utils;
import proj.ctworld.core.orm.*;
import proj.ctworld.core.repository.ImageStorageRepository;
import proj.ctworld.core.lib.ModelErrorException;

@Service("ImageStorageService")
@Transactional(rollbackFor=Exception.class)
public class ImageStorageServiceImpl implements ImageStorageService{

	private static Logger logger = Logger.getLogger(ImageStorageServiceImpl.class);

	@Autowired
	private ImageStorageRepository isDAO;

	@Autowired
	private Utils utils;
	
	private List<String> EXT_ACCEPTED = Arrays.asList("JPG");
	
	public ImageStorage insertImage(Long relatedId, String relatedType, byte[] file, String filename, Long auId) throws Exception
	{
		String ext = FilenameUtils.getExtension(filename);
		if (!EXT_ACCEPTED.contains(ext.toUpperCase()))
			throw new ModelErrorException("檔案副檔名不可為"+ext);
		
		//if exist old file, generate new filename
		if (utils.existImg(filename)) {
			int post = 1;
			String name = FilenameUtils.getBaseName(filename);
			while(utils.existImg(name + post + "." + ext))
				post++;
			filename = name + post + "." + ext;
		}
		
		//file system
		String location = utils.saveImg(file, filename);
		
		//db
		DateTime creationTime = new DateTime();
		ImageStorage is = new ImageStorage(relatedId, relatedType, filename, location, location, location, auId, "a", creationTime, auId, "a", creationTime);
		isDAO.save(is);
		logger.debug("file id " + is.getIsId());
		
		logger.debug(filename + " saved in local file system, path: " + location);
		
		return is;
	}

	public ImageStorage updateImage(byte[] file, String filename, Long relatedId, String relatedType, Long auId) throws Exception
	{
		String ext = FilenameUtils.getExtension(filename);
		if (!EXT_ACCEPTED.contains(ext.toUpperCase()))
			throw new ModelErrorException("檔案副檔名不可為"+ext);
		
		return null;
		//這是要幹麻的？
		//get pojo
//		ImageStorage is = getByRelatedId(relatedId, relatedType);
//
//		//if exist old file, generate new filename
//		if (utils.existImg(filename)) {
//			int post = 1;
//			String name = FilenameUtils.getBaseName(filename);
//			while(utils.existImg(name + post + "." + ext))
//				post++;
//			filename = name + post + "." + ext;
//		}
//		
//		//file system
//		String location = utils.saveImg(file, filename);
//		
//		//db
//		is.setIsName(filename);
//		is.setIsLocationLarge(location);
//		is.setIsLocationMedium(location);
//		is.setIsLocationThumbnail(location);
//		is.setIsMdyer(auId);
//		is.setIsMdyerType("a");
//		is.setIsLastUpdateTime(new DateTime());
//		isDAO.save(is);
		
//		logger.debug(filename + " saved in local file system, path: " + location);
		
//		return is;
	}
	
	public void deleteImage(Long relatedId, String relatedType) {
		List<ImageStorage> list = isDAO.getLikeTypeImage(relatedType, relatedId);
		
		for (ImageStorage is : list) {
			isDAO.delete(is);
		}
		
	}
	
	public String getLocationLarge(Long isId) {
		ImageStorage is = isDAO.findOne(isId);
		return is != null ? is.getIsLocationLarge() : null;
	}
	
	public String getLocationMedium(Long isId) {
		ImageStorage is = isDAO.findOne(isId);
		return is != null ? is.getIsLocationMedium() : null;
	}
	
	public String getLocationThumbnail(Long isId) {
		ImageStorage is = isDAO.findOne(isId);
		return is != null ? is.getIsLocationThumbnail() : null;
	}
	
	public List<ImageStorage> getByRelatedId(Long relatedId, String relatedType) {
		return isDAO.getLikeTypeImage(relatedType, relatedId);
	}
}
