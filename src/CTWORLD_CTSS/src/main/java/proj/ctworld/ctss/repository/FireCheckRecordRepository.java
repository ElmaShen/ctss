package proj.ctworld.ctss.repository;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import proj.ctworld.ctss.orm.*;
import proj.ctworld.ctss.orm.dto.FireCheckRecordDto;

@Repository
public interface FireCheckRecordRepository extends JpaRepository<FireCheckRecord, Long > {

    @Query("Select Distinct fcr.checkYear From FireCheckRecord fcr Order by fcr.checkYear Desc")
    List<String> queryCheckYear();
    
    @Query("Select Distinct fcr.checkYear From FireCheckRecord fcr Where fcr.placeId = ?1 And fcr.checkType = ?2 Order by fcr.checkYear Desc")
    List<String> queryCheckYearData(String placeId, String checkType);
    
    @Query("Select Distinct fcr.checkMonth From FireCheckRecord fcr Where fcr.placeId = ?1 And fcr.checkType = ?2 And fcr.checkYear = ?3 Order by fcr.checkMonth Desc")
    List<String> queryCheckMonthData(String placeId, String checkType, String checkYear);
    
    @Query("Select DATE_FORMAT(Min(fcr.checkDate),'%Y-%m-%d') As checkDate From FireCheckRecord fcr Where fcr.checkYear = ?1 And fcr.checkMonth = ?2 And fcr.placeId = ?3 And fcr.checkType = ?4 And fcr.checkTimesMark = ?5")
    String queryCheckDate(String checkYear, String checkMonth, String placeId, String checkType, long checkTimesMark);
    
    FireCheckRecord findFirstByCheckYearAndCheckMonthAndPlaceIdAndCheckTypeAndCheckTimesMarkOrderByCheckDate(String checkYear, String checkMonth, String placeId, String checkType, long checkTimesMark);
    
    @Query("Select fcr From FireCheckRecord fcr Where fcr.checkYear = ?1 And fcr.checkMonth = ?2 And fcr.placeId = ?3 And fcr.checkType = ?4 And fcr.checkTimesMark = ?5 And fcr.checkDate = ?6 Order By fcr.checkId") 
    List<FireCheckRecord> queryCheckRecord(String checkYear, String checkMonth, String placeId, String checkType, long checkTimesMark, LocalDateTime checkDate);
	
    @Query("Select new proj.ctworld.ctss.orm.dto.FireCheckRecordDto(fcr.manager, fcr.managerSign, fcr.deviceType, fcr.checkContent, fcr.updatorName, fcr.checkSymbol, fcr.checkResult, fcr.checkDate) \n"
            + "From FireCheckRecord fcr Where fcr.checkTimesMark=1 And fcr.placeId = ?1 And fcr.checkType = ?2 And fcr.checkYear = ?3 And fcr.checkMonth = ?4 Order by fcr.checkId")
    List<FireCheckRecordDto> queryFireSafetyEquipment(String placeId, String checkType, String checkYear, String checkMonth);
    
    @Modifying
    @Transactional
    @Query("Update FireCheckRecord fcr set fcr.checkSymbol = ?2, fcr.checkResult = ?3, fcr.checkStatus = ?4, fcr.managerSign = ?5, fcr.updateDt = ?6, fcr.updatorName = ?7, fcr.updatorId = ?8, fcr.updatorIp = ?9, fcr.isWaitUpdateToSummary = ?10 Where fcr.fcrId = ?1")
    int updateRecord(Long fcrId, String checkSymbol, String checkResult, String checkStatus, String managerSign, LocalDateTime updateDt, String updatorName, String updatorId, String updatorIp, boolean isWaitUpdateToSummary);

    @Query("Select new proj.ctworld.ctss.orm.dto.FireCheckRecordDto(fcr.manager, fcr.managerSign, fcr.checkContent, fcr.checkTimesMark, fcr.updatorName, "
            + "fcr1.checkSymbol, fcr1.checkResult, DATE_FORMAT(fcr1.checkDate,'%Y-%m-%d'), fcr2.checkSymbol, fcr2.checkResult, DATE_FORMAT(fcr2.checkDate,'%Y-%m-%d')) \n"
            + "From FireCheckRecord fcr \n"
            + "Left Join FireCheckRecord fcr1 on fcr1.fcrId=fcr.fcrId And fcr1.checkTimesMark=1 \n"
            + "Left Join FireCheckRecord fcr2 on fcr2.fcrId=fcr.fcrId And fcr2.checkTimesMark=2 \n"
            + "Where fcr.placeId = ?1 And fcr.checkType = ?2 And fcr.checkYear = ?3 And fcr.checkMonth = ?4 \n"
            + "Order by fcr.checkId, fcr.checkTimesMark ")
    List<FireCheckRecordDto> queryFirePreventionFacilities(String placeId, String checkType, String checkYear, String checkMonth);
    
    @Query("Select fcr From FireCheckRecord fcr Where fcr.placeId = ?1 And fcr.checkType = ?2 And fcr.checkYear = ?3 And fcr.checkMonth = ?4 Order By fcr.checkTimesMark, fcr.checkContent")
    List<FireCheckRecord> queryDailyFireSource(String placeId, String checkType, String checkYear, String checkMonth);
    
    @Query("Select fcr From FireCheckRecord fcr Where fcr.checkStatus = ?1 And fcr.placeId =?2 And fcr.checkSymbol != 'O'")
    List<FireCheckRecord> queryCheckStatusList(String checkStatus , String placeId);
    
    @Query("Select fcr From FireCheckRecord fcr Where fcr.placeId = ?1 And fcr.checkSymbol != 'O'")
    List<FireCheckRecord> queryCheckStatusList(String placeId);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update FireCheckRecord Set checkStatus = :checkStatus , checkSymbol = :checkSymbol , checkResult = :checkResult , updatorId = :userId , updatorName = :userName , updatorIp = :userIp , updateDt = sysdate() , is_Wait_Update_To_Summary = :isWaitUpdateToSummary Where placeId = :placeId And checkId = :checkId ")
    int updateCheckStatusMark(@Param("placeId") String placeId , @Param("checkId") String checkId , @Param("checkStatus") String checkStatus , @Param("checkSymbol") String checkSymbol , 
    		@Param("checkResult") String checkResult , @Param("userId") String userId, @Param("userName") String userName, @Param("userIp") String userIp , @Param("isWaitUpdateToSummary") boolean isWaitUpdateToSummary );
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update FireCheckRecord Set checkStatus = :checkStatus , checkSymbol = :checkSymbol , checkResult = :checkResult , updatorId = :userId , updatorName = :userName , updatorIp = :userIp , updateDt = sysdate() , is_Wait_Update_To_Summary = :isWaitUpdateToSummary Where fcrId = :fcrId ")
    int updateCheckStatusMark(@Param("fcrId") Long fcrId , @Param("checkStatus") String checkStatus , @Param("checkSymbol") String checkSymbol , 
    		@Param("checkResult") String checkResult , @Param("userId") String userId, @Param("userName") String userName, @Param("userIp") String userIp , @Param("isWaitUpdateToSummary") boolean isWaitUpdateToSummary );
    
    @Query("Select fcr From FireCheckRecord fcr Where fcr.checkSymbol != '' And fcr.checkSymbol != 'O' And fcr.checkStatus = ?1 Order By fcr.placeType, fcr.place, fcr.checkId, fcr.checkDate")
    List<FireCheckRecord> queryResultFollowList(String checkStatus);
    
    @Query("Select fcr From FireCheckRecord fcr Where fcr.checkSymbol != '' And fcr.checkSymbol != 'O' Order By fcr.placeType, fcr.place, fcr.checkId, fcr.checkDate")
    List<FireCheckRecord> queryResultFollowList();
    
    @Query("Select Distinct fcr.checkYear From FireCheckRecord fcr Where fcr.placeType = :placeType Order By fcr.checkYear Desc")
    List<String> queryCheckYear(@Param("placeType") String placeType);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update FireCheckRecord Set ctNote = :ctNote , updateDt = sysdate() , updatorId = :updatorId , updatorName = :updatorName , updatorIp = :updatorIp Where fcrId = :fcrId")
    int updateCheckRecordCtNote(@Param("fcrId") Long fcrId , @Param("ctNote") String ctNote , @Param("updatorId") String updatorId , @Param("updatorName") String updatorName , @Param("updatorIp") String updatorIp);
    
    @Query("Select fcr From FireCheckRecord fcr Where placeId = :placeId And checkYear = :year And checkMonth = :month And checkResult Is Not Null And checkResult != '' Order By checkId ")
    List<FireCheckRecord> queryChecKRecordList(@Param("placeId") String placeId , @Param("year") String year , @Param("month") String month);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update FireCheckRecord Set is_Wait_Update_To_Summary = false , updateDt = sysdate() , updatorId = :updatorId , updatorName = :updatorName , updatorIp = :updatorIp Where placeId = :placeId And checkYear = :year And checkMonth = :month ")
    int updateCheckRecordAfterSync(@Param("placeId") String placeId, @Param("year") String year, @Param("month") String month, @Param("updatorId") String updatorId , @Param("updatorName") String updatorName , @Param("updatorIp") String updatorIp);

    List<FireCheckRecord> findByPlaceIdAndCheckYearAndCheckMonthAndIsWaitUpdateToSummaryOrderByCheckId(String placeId, String checkYear, String checkMonth, boolean isWaitUpdateToSummary);
    
    @Query("Select Distinct fcr.checkTimesMark From FireCheckRecord fcr Where placeId = :placeId And checkType = :checkType And checkYear = :checkYear And checkMonth = :checkMonth Order By checkTimesMark")
    List<FireCheckRecord> queryCheckTimesMarkList(@Param("placeId") String placeId, @Param("checkType") String checkType, @Param("checkYear") String checkYear, @Param("checkMonth") String checkMonth);
    
}    
