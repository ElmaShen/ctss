package proj.ctworld.ctss.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import proj.ctworld.ctss.orm.*;

@Repository
public interface AnnouncementReadRecordRepository extends JpaRepository<AnnouncementReadRecord, Long>{

	
}
