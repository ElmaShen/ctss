package proj.ctworld.ctss.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import proj.ctworld.ctss.orm.*;

@Repository
public interface AdminInfoRepostiory extends JpaRepository<AdminInfo, Long>{


	@Query("select a from AdminInfo a where a.userId = ?1")
	List<AdminInfo> getUserByUserId(String userId);
	
}
