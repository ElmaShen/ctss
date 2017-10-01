package proj.ctworld.core.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import proj.ctworld.core.orm.SystemMessage;

@Repository
public interface SystemMessageRepository extends JpaRepository<SystemMessage, Long>,SystemMessageRepositoryExtend {

}
