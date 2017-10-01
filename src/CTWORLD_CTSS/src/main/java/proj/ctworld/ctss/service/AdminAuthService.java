package proj.ctworld.ctss.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import proj.ctworld.core.lib.ModelErrorException;
import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.AdminInfo;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;

public interface AdminAuthService {
	
	/**
	 * 根據 id 取得管理者的資訊
	 * @param aiId 使用者 ID
	 * @return 若果成功則回傳 Object，否貼貼回傳 null
	 */
	public AdminInfo getAdminInfo(Long aiId);
	
	/**
	 * 根據 userId 取得管理者的資訊
	 * @param userId 使用者編號
	 * @return 若果成功則回傳 Object，否貼貼回傳 null
	 */
	public AdminInfo getAdminInfo(String userId);

	/**
	 * getUserPlaceMappingInfo 說明: 取得所有場所人員對應表資料.<br/>
	 * <br/>   
	 * @return List<UserPlaceMappingInfo>
	 * @author JoyceLai 2017/07/28
	 */
	public List<UserPlaceMappingInfo> getUserPlaceMappingInfo();
	
	/**
	 * 根據 userId 取得與這個使用者相關的場所
	 * @param userId 使用者編號
	 * @return 回傳陣列
	 */
	public List<UserPlaceMappingInfo> getUserPlaceMappingInfo(String userId);

	/**
	 * 判斷使用者是否能成功登入
	 * @param account 帳號
	 * @param password 密碼
	 * @param userType 使用者類別
	 * @return 0 是成功， < 0 則代表失敗並回傳錯誤代碼
	 * @throws ModelErrorException
	 */
	public int login(String account, String password, String userType) throws ModelErrorException;
	
	/**
	 * 取得系統訊息
	 * @param rtnCode 代碼
	 * @param message 訊息名稱
	 * @return 回傳訊息 map ，用於 ajax
	 */
	public HashMap<String, Object> getMessage(Integer rtnCode, String message);

	/**
	 * 取得系統訊息
	 * @param rtnCode 代碼
	 * @return 回傳訊息 map ，用於 ajax
	 */
	public HashMap<String, Object> getMessage(Integer rtnCode);

	/**
	 * 取得系統參數
	 * @param ssCode 系統參數代碼
	 * @return 若成功讀取，則回傳值，否則則回傳空字串
	 */
	public String getSystemSetting(String ssCode);
	
	/**
	 * 取得目前放於系統連線(Session)的識別碼
	 * @param request http request
	 * @return 若成功則回傳代碼，失敗則回傳 null
	 */
	public Long getToken(HttpServletRequest request);

	/**
	 * 建立系統連線(Session)的識別碼
	 * @param request http request
	 * @param userType 使用者類別
	 * @param userId 使用者 id
	 */
	public void createToken(HttpServletRequest request, String userType, String userId);

	/**
	 * 移除系統連線(Session)的識別碼
	 * @param request
	 */
	public void removeToken(HttpServletRequest request);

	/**
	 * 取得Session 資訊
	 * @param aesId 識別碼
	 * @return Session資訊
	 */
	public AdminEmpSession getAdminEmpSession(Long aesId);
	
	/**
	 * 更新連線資訊，若已登入，則回傳已登入，並更新Session，否則則回傳 false
	 * @param token 識別碼
	 * @return 回傳是否登入
	 */
	public boolean isLoginAndUpdateSession(Long token);
}
