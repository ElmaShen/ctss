package proj.ctworld.ctss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import proj.ctworld.ctss.orm.*;

@Repository
public interface FireCheckItemRepository extends JpaRepository<FireCheckItem, Long> {

    @Query("Select Distinct fci.checkType From FireCheckItem fci Where fci.isEnabled=1")
	List<FireCheckItem> queryCheckType();
    
    @Query("Select fci From FireCheckItem fci Where fci.checkType=?1 And fci.isEnabled=1 Order by fci.checkId")
    List<FireCheckItem> queryByCheckType(String checkType);
    
}
