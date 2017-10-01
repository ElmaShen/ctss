package proj.ctworld.ctss.repository;

import java.util.List;

import proj.ctworld.ctss.orm.AnnouncementSetInfo;

public interface AnnouncementSetInfoRepositoryCustom {
	public List<AnnouncementSetInfo> getUnread(String userId);
}
