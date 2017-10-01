package proj.ctworld.core.service;

import java.util.*;

public interface SystemService {

	public HashMap<String, Object> getMessage(Integer rtnCode);

	public HashMap<String, Object> getMessage(Integer rtnCode, String message);

	public ArrayList<Integer> findMissingMessage();
	
	/**
	 * 從系統的資料表中取出相對的 ssCode
	 * @param ssCode ssCode的值
	 * @return 若從DB中找到ssCode，則回傳ssValue，否則回傳null
	 */
	public String getSystemSetting(String ssCode);
}
