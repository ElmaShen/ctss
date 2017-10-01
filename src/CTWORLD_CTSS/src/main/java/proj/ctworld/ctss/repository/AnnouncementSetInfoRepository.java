package proj.ctworld.ctss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import proj.ctworld.ctss.orm.*;

@Repository
public interface AnnouncementSetInfoRepository extends JpaRepository<AnnouncementSetInfo, Long>,AnnouncementSetInfoRepositoryCustom{

	@Query("SELECT asi from AnnouncementSetInfo asi where asi.messageId=?1")
	List<AnnouncementSetInfo> getAccouncementByMessageId(String messageId);
	
	@Query("SELECT asi from AnnouncementSetInfo asi where asi.isEnabled=1 order by asi.announcementDt desc")
	List<AnnouncementSetInfo> getAllAnnounceMsg();
	
	List<AnnouncementSetInfo> findByOrderByAnnouncementDtDesc();
	
	@Modifying
    @Transactional
    @Query("Update AnnouncementSetInfo asi Set asi.announcementDt = sysdate(), asi.announcementSubject = :announcementSubject, "
            + "asi.announcementContent = :announcementContent, asi.isEnabled = :isEnabled, asi.updatorId=:userId, "
            + "asi.updatorName=:userName, asi.updatorIp=:userIp, asi.updateDt=sysdate() "
            + "Where asi.asiId = :asiId")
    int updateData(@Param("asiId") Long asiId, @Param("announcementSubject") String announcementSubject, @Param("announcementContent") String announcementContent, 
            @Param("isEnabled") int isEnabled, @Param("userId") String userId, @Param("userName") String userName, @Param("userIp") String userIp);
	
	AnnouncementSetInfo findFirstByOrderByMessageIdDesc();
}
