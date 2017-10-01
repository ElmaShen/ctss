package proj.ctworld.core.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import proj.ctworld.core.orm.SystemMessage;
import proj.ctworld.core.orm.SystemSetting;

@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {

	@Query("select s from SystemSetting s where ssCode=?1 and ssMode=?2")
	List<SystemSetting> getSystemSetting(String ssCode,String ssMode);
}
