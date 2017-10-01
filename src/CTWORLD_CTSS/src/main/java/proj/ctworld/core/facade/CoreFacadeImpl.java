package proj.ctworld.core.facade;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import proj.ctworld.core.orm.*;
import proj.ctworld.core.repository.*;
import proj.ctworld.core.service.*;

@Service("CoreFacade")
public class CoreFacadeImpl implements CoreFacade{
	
	@Autowired
	ImageStorageService isService;
	
	@Autowired
	SystemService smService;
	
	@Autowired
	ObjectMapper oMapper ;
	
	/**
	 * 根據ssCode取得相對的參數設定
	 * @param ssCode 參數代碼
	 * @return  若從DB中找到ssCode，則回傳ssValue，否則回傳null
	 */
	@Override
	public String getSystemSetting(String ssCode) {
		return smService.getSystemSetting(ssCode);
	}
	
	@Override
	public HashMap<String, Object> getMessage(Integer rtnCode) {
		return smService.getMessage(rtnCode);
	}
	
	@Override
	public HashMap<String, Object> getMessage(Integer rtnCode, String message) {
		return smService.getMessage(rtnCode, message);
	}
	
	@Override
	public List<Map> getLikeTypeImage(String relatedType,long relatedId) {
		
		List<ImageStorage> list = isService.getByRelatedId(relatedId, relatedType);
 
		ArrayList<Map> ret = new ArrayList<Map>();
		for ( ImageStorage is : list ) {
			HashMap<String,Object> map =oMapper.convertValue(is, HashMap.class);
			map.put(is.getIsRelatedType(), is.getIsId());
			
			ret.add(map);
        }
		return ret;
	}

	@Override
	public Map insertImage(Long relatedId, String relatedType, byte[] file, String filename, Long auId) throws Exception {
		HashMap<String,Object> ret = new HashMap<String,Object>();
		ImageStorage obj = isService.insertImage(relatedId, relatedType, file, filename, auId);
		HashMap<String,Object> map =oMapper.convertValue(obj, HashMap.class);
		return map;
	}
	
	@Override
	public Map updateImage(byte[] file, String filename, Long relatedId, String relatedType, Long auId) throws Exception {
		return null;
	}
}
