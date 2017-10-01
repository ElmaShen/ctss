package proj.ctworld.ctss.service;

import java.util.List;

import proj.ctworld.ctss.orm.AnnouncementSetInfo;

public interface AnnounceMsgService {
	
	 /**
     * getAllAnnounceMsg 說明: 取得所有公告訊息依日期降冪排序.<br/>
     * <br/>   
     * @return List<AnnouncementSetInfo>
     * @author JoyceLai 2017/07/25
     */
    public List<AnnouncementSetInfo> getAllAnnounceMsg();
	
}
