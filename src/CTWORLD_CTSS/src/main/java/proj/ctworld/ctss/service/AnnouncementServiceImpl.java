package proj.ctworld.ctss.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proj.ctworld.ctss.orm.AnnouncementReadRecord;
import proj.ctworld.ctss.orm.AnnouncementSetInfo;
import proj.ctworld.ctss.repository.AnnouncementReadRecordRepository;
import proj.ctworld.ctss.repository.AnnouncementSetInfoRepository;

@Service("AnnouncementService")
public class AnnouncementServiceImpl implements AnnouncementService {

	@Autowired
	AnnouncementSetInfoRepository asiRepo;
	
	@Autowired
	AnnouncementReadRecordRepository arrRepo;
	
	@Override
	public List<AnnouncementSetInfo> getUnread(String userId) {
		return asiRepo.getUnread(userId);
	}

	@Override
	public void updateRead(String userId, ArrayList<String> messageIds,String creatorIp,String userName) {
		LocalDateTime createDt = new LocalDateTime();
		
		ArrayList<AnnouncementReadRecord> list = new ArrayList<AnnouncementReadRecord>();
		
		for (int i = 0; i < messageIds.size(); i++) {
			AnnouncementReadRecord arr = new AnnouncementReadRecord();
			arr.setCreateDt(createDt);
			arr.setCreatorIp(creatorIp);
			arr.setUserId(userId);
			arr.setUserName(userName);
			arr.setMessageId(messageIds.get(i));
			list.add(arr);
		}
		
		arrRepo.save(list);
	}

	@Override
	public AnnouncementSetInfo getAnnouncement(String messageId) {
		List<AnnouncementSetInfo> list = asiRepo.getAccouncementByMessageId(messageId);
		return list.size() >0?list.get(0):null;
	}

}
