package proj.ctworld.core.facade;

import java.util.*;

import proj.ctworld.core.orm.ImageStorage;


public interface CoreFacade {

	public Map updateImage(byte[] file, String filename, Long relatedId, String relatedType, Long auId) throws Exception;

	public Map insertImage(Long relatedId, String relatedType, byte[] file, String filename, Long auId) throws Exception;

	public List<Map> getLikeTypeImage(String relatedType, long relatedId);

	public HashMap<String, Object> getMessage(Integer rtnCode, String message);

	public HashMap<String, Object> getMessage(Integer rtnCode);

	public String getSystemSetting(String ssCode);
}
