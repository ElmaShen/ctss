package proj.ctworld.ctss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import proj.ctworld.ctss.orm.FireCheckSummary;

@Repository
public interface FireCheckSummaryRepository extends JpaRepository<FireCheckSummary, Long>{

	@Query(" Select fcs From FireCheckSummary fcs Where fcs.placeType = :placeType And fcs.year = :year And fcs.month = :month Order By placeId ")
	List<FireCheckSummary> queryCheckSummaryList(@Param("placeType") String placeType , @Param("year") String year , @Param("month") String month );
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(" Update FireCheckSummary Set ctNote = :ctNote , updateDt = sysdate() , updatorId = :updatorId , updatorName = :updatorName , updatorIp = :updatorIp Where fcsId = :fcsId ")
	int updateCheckSummarCtNote(@Param("fcsId") Long fcsId , @Param("ctNote") String ctNote , @Param("updatorId") String updatorId , @Param("updatorName") String updatorName , @Param("updatorIp") String updatorIp);
		
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(" Update FireCheckSummary Set fireRefuge = :fireRefuge , fireProtection = :fireProtection , electricDevice = :electricDevice , fireSource = :fireSource , updateDt = sysDate() , updatorName = :updatorName , updatorId = :updatorId , updatorIp = :updatorIp Where fcsId = :fcsId ")
	int updateCheckSummarDeviceContent(@Param("fcsId") Long fcsId , @Param("fireRefuge") String fireRefuge , @Param("fireProtection") String fireProtection , 
			@Param("electricDevice") String electricDevice , @Param("fireSource") String fireSource , @Param("updatorName") String updatorName , 
			@Param("updatorId") String updatorId , @Param("updatorIp") String updatorIp );
	
}
