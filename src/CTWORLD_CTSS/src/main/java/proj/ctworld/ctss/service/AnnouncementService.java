package proj.ctworld.ctss.service;

import java.util.*;

import proj.ctworld.ctss.orm.AnnouncementSetInfo;

public interface AnnouncementService {
	
	/**
	 * 取得使用者尚未讀取的公告
	 * @param userId 使用者Id
	 * @return 回傳尚未讀取的公告
	 */
	public List<AnnouncementSetInfo> getUnread(String userId);
	
	/**
	 * 更新公告為已讀取
	 * @param userId 使用者Id
	 * @param messageIds 訊息
	 * @param creatorIp 使用者IP
	 * @param userName 使用者名稱
	 */
	public void updateRead(String userId, ArrayList<String> messageIds,String creatorIp,String userName) ;
	
	/**
	 * 根據公告的 Id 取得公告
	 * @param messageId 公告Id
	 * @return 回傳公告
	 */
	public AnnouncementSetInfo getAnnouncement(String messageId);
}
