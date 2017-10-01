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
public interface UserPlaceMappingInfoRepository extends JpaRepository<UserPlaceMappingInfo, Long>{

    @Query("select u from UserPlaceMappingInfo u where u.isEnabled=1 order by u.placeId")
    List<UserPlaceMappingInfo> getAllUserPlace();
    
	@Query("select u from UserPlaceMappingInfo u where u.isEnabled=1 and (u.managerId = ?1 or u.examiner1Id = ?1 or u.examiner2Id = ?1) order by u.placeId")
	List<UserPlaceMappingInfo> getUserPlaceByUserId(String userId);
	
	@Query("Select u.isNeedDailyCheckFire From UserPlaceMappingInfo u Where u.placeId = ?1 And u.isEnabled=1")
	long getIsNeedDailyCheckFireByPlaceId(String place);
	
	@Query("Select u From UserPlaceMappingInfo u Where u.isEnabled=1 And (u.managerId = ?1 Or u.examiner1Id = ?1 Or u.examiner2Id = ?1) And u.placeId = ?2")
	List<UserPlaceMappingInfo> queryByUserIdPlaceId(String userId, String placeId);
	
	@Query("Select Distinct placeType From UserPlaceMappingInfo ")
	List<String> queryPlaceTypeList();
	
	@Query(" Select upmi From UserPlaceMappingInfo upmi Where upmi.isEnabled = 1 And placeType = :placeType Order By placeId ")
	List<UserPlaceMappingInfo> queryIsEnabledList(@Param("placeType") String placeType);

	@Modifying
    @Transactional
    @Query("Update UserPlaceMappingInfo u Set u.manager = :manager, u.managerId = :managerId, u.examiner1 = :examiner1, u.examiner1Id = :examiner1Id, "
            + "u.examiner2 = :examiner2, u.examiner2Id = :examiner2Id, u.isNeedDailyCheckFire = :isNeedDailyCheckFire, u.isEnabled = :isEnabled, "
            + "u.unitCode = :unitCode, u.updatorId = :userId, u.updatorName = :userName, u.updatorIp = :userIp, u.updateDt = sysdate() "
            + "Where u.upmiId = :upmiId")
	int updateData(@Param("upmiId") Long upmiId, @Param("manager") String manager, @Param("managerId") String managerId, @Param("examiner1") String examiner1, 
	        @Param("examiner1Id") String examiner1Id, @Param("examiner2") String examiner2, @Param("examiner2Id") String examiner2Id, 
	        @Param("isNeedDailyCheckFire") long isNeedDailyCheckFire, @Param("isEnabled") long isEnabled, @Param("unitCode") String unitCode,
	        @Param("userId") String userId, @Param("userName") String userName, @Param("userIp") String userIp);

	@Query("Select Distinct u.placeType From UserPlaceMappingInfo u Where u.isEnabled=1")
    List<String> queryPlaceTypes();
	
	UserPlaceMappingInfo findFirstByPlaceTypeOrderByPlaceIdDesc(String placeType); 
}
