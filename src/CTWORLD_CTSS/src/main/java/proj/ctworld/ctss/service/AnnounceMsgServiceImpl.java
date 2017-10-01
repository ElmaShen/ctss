package proj.ctworld.ctss.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import proj.ctworld.ctss.orm.AnnouncementSetInfo;
import proj.ctworld.ctss.repository.AnnouncementSetInfoRepository;

/**
 * 【程式名稱】: AnnounceMsgServiceImpl <br/>
 * 【功能名稱】: 實作 AnnounceMsgService <br/>
 * 【功能說明】: 公告訊息 <br/>
 * 【建立日期】: 2017/07/25 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Ennis Hong <br/>
 * 
 */
@Service("AnnounceMsgService")
@Component
public class AnnounceMsgServiceImpl implements AnnounceMsgService {
    
    
    @Autowired
    AnnouncementSetInfoRepository announcementSetInfoRepository;


   /*
    * (non-Javadoc)
    * @see proj.ctworld.ctss.service.AnnounceMsgService#getAllAnnounceMsg()
    */
    @Override
    public List<AnnouncementSetInfo> getAllAnnounceMsg() {
        return announcementSetInfoRepository.getAllAnnounceMsg();
//        return announcementSetInfoRepository.findByOrderByAnnouncementDtDesc();
        
    }


    
}
