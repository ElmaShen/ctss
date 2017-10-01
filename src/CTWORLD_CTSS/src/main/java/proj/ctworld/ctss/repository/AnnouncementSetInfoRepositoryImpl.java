package proj.ctworld.ctss.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import proj.ctworld.ctss.orm.AnnouncementSetInfo;

public class AnnouncementSetInfoRepositoryImpl implements AnnouncementSetInfoRepositoryCustom {

	@Autowired
	protected EntityManager entityManager;
	
	@Override
	public List<AnnouncementSetInfo> getUnread(String userId) {
		
		String hql = ""; 
		hql += "SELECT asi FROM AnnouncementSetInfo asi ";
		hql += "\n";
		hql += "left join AnnouncementReadRecord arr ";
		hql += "\n";
		hql += "on asi.messageId = arr.messageId and arr.userId = :userId ";
		hql += "\n";
		hql += "WHERE arr.userId is null And asi.isEnabled = 1";
		
		
		Query query = entityManager.createQuery(hql);

		query.setParameter("userId", userId);
//		query.setParameter(1, userId);
		
		List<AnnouncementSetInfo> list = (List<AnnouncementSetInfo>)query.getResultList();
		
		return list;
	}

}
