package proj.ctworld.ctss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.orm.FireCheckSummary;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.service.AdminAuthService;
import proj.ctworld.ctss.service.CheckSummaryService;

/**
 * 
 * 【程式名稱】: CheckSummaryDataController <br/>
 * 【功能名稱】: 檢查總表資料取得<br/>
 * 【功能說明】: <br/>
 * 【建立日期】: 2017/08/03 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
@RestController
@RequestMapping(value = "/api/admin/checkSummary")
public class CheckSummaryDataController {
	private static Logger logger = Logger.getLogger(CheckSummaryDataController.class);
	
	@Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private CheckSummaryService checkSummaryService;
	
    /**
     * 
     * queryPlaceTypeList 說明: 取得場所分類清單.<br/>
     * <br/>   
     * @return Map<String,Object>
     * @author Tom Lai 2017/08/09
     */
	@RequestMapping(value = "/queryPlaceTypeList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryPlaceTypeList(){
		logger.debug("queryPlaceTypeList()");
		Map<String, Object> resultMap = new HashMap<>();
		List<String> placeTypeList = new ArrayList<String>();
		String msg = "" ;
		try{
			placeTypeList = checkSummaryService.queryPlaceTypeList();
			logger.debug("queryPlaceTypeList() placeTypeList.size() = " + placeTypeList.size());
		}catch(Exception e){
			logger.error("queryPlaceTypeList() has error : " + e.getMessage() , e );
			msg = "查詢失敗，錯誤訊息：\n" + e.getMessage() ;
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("resultList", placeTypeList);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * queryCheckYearList 說明: 取得檢查年度清單.<br/>
	 * <br/>   
	 * @param placeType	場所分類
	 * @return Map<String,Object>
	 * @author Tom Lai 2017/08/09
	 */
	@RequestMapping(value = "/queryCheckYearList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryCheckYearList(@RequestParam(value="placeType") String placeType){
		logger.debug("queryCheckYearList()");
		Map<String, Object> resultMap = new HashMap<>();
		List<String> checkYearList = new ArrayList<String>();
		String msg = "" ;
		try{
			checkYearList = checkSummaryService.queryCheckYearList(placeType);
			logger.debug("queryPlaceTypeList() checkYearList.size() = " + checkYearList.size());
		}catch(Exception e){
			logger.error("queryCheckYearList() has error : " + e.getMessage() , e );
			msg = "查詢失敗，錯誤訊息：\n" + e.getMessage() ;
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("resultList", checkYearList);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	 * queryCheckSummaryList 說明: 取得檢查總表清單.<br/>
	 * <br/>   
	 * @param placeType	場所分類
	 * @param year		檢查年度
	 * @param month		檢查月份
	 * @return Map<String,Object>
	 * @author Tom Lai 2017/08/09
	 */
	@RequestMapping(value = "/queryCheckSummaryList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryCheckSummaryList(@RequestParam(value="placeType") String placeType , @RequestParam(value="year") String year , @RequestParam(value="month") String month) {
		logger.debug("queryCheckSummaryList() placeType = " + placeType + " year = " + year + " month = " + month);
		Map<String, Object> resultMap = new HashMap<>();
		List<FireCheckSummary> checkSummaryList = new ArrayList<FireCheckSummary>();
		List<UserPlaceMappingInfo> userPlaceList = new ArrayList<UserPlaceMappingInfo>();
		String msg = "" ;
		try{
			
			checkSummaryList = checkSummaryService.queryCheckSummaryList(placeType, year, month);

			Long token = authService.getToken(request);
		    AdminEmpSession aes = authService.getAdminEmpSession(token);
		    String userId = aes.getAesUserId();
		    String userName = aes.getAesUserName();
			String userIp = request.getRemoteAddr();
			boolean needAdd = checkSummaryList.size() == 0 ? true : false ;
			
			if( needAdd ){
				logger.info("queryCheckSummaryList() no data change use queryIsEnabledCheckSummaryList() ");
				userPlaceList = checkSummaryService.queryIsEnabledUserPlaceList(placeType);
				
				for( UserPlaceMappingInfo userPlace : userPlaceList ){
					
					FireCheckSummary checkSummary = transferUserPlaceToCheckSummary(userPlace);
					
					checkSummary.setCreatorId(userId);
					checkSummary.setCreatorName(userName);
					checkSummary.setCreatorIp(userIp);
					checkSummary.setCreateDt(LocalDateTime.now());
					checkSummary.setUpdatorId(userId);
					checkSummary.setUpdatorName(userName);
					checkSummary.setUpdatorIp(userIp);
					checkSummary.setUpdateDt(LocalDateTime.now());
					
					checkSummary.setYear(year);
					checkSummary.setMonth(month);
					
					checkSummary.setPlaceType(placeType);
					
					checkSummaryList.add(checkSummary);
					
				}
				
			}
			
			for( FireCheckSummary checkSummary : checkSummaryList ){
				
				String fireRefuge = "";
				String fireProtection = "" ;
				String electricDevice = "" ;
				String fireSource = "" ;
				String placeId = checkSummary.getPlaceId() ;
                boolean flgFireRefuge = false;
                boolean flgFireProtection = false;
                boolean flgElectricDevice = false;
                boolean flgFireSource = false;
				
				List<FireCheckRecord> checkRecordList = checkSummaryService.queryCheckRecordChangeList(placeId, year, month);
				
				if(CollectionUtils.isNotEmpty(checkRecordList)) {
    				for( FireCheckRecord checkRecord : checkRecordList ){
    					String checkType = checkRecord.getCheckType();
    					String checkResult = StringUtils.isEmpty(checkRecord.getCheckResult()) ? "" : checkRecord.getCheckResult();
//    					if( StringUtils.isNotEmpty(checkResult) ){
    						if( checkType.equals("防火避難設施") ){
    						    if( StringUtils.isNotEmpty(checkResult) ){
        							if( StringUtils.isEmpty(fireRefuge) ){
        								fireRefuge = checkResult ;
        							} else {
        								fireRefuge += " / " + checkResult ;
        							}
    						    }
    							flgFireRefuge = true;
    						} else if ( checkType.equals("消防安全設備") ) {
                                if( StringUtils.isNotEmpty(checkResult) ){
        							if( StringUtils.isEmpty(fireProtection) ){
        								fireProtection = checkResult ;
        							} else {
        								fireProtection += " / " + checkResult ;
        							}
                                }
    							flgFireProtection = true;
    						} else if ( checkType.equals("電器安全") ) {
                                if( StringUtils.isNotEmpty(checkResult) ){
        							if( StringUtils.isEmpty(electricDevice) ){
        								electricDevice = checkResult ;
        							} else {
        								electricDevice += " / " + checkResult ;
        							}
                                }
    							flgElectricDevice = true;
    						} else if ( checkType.equals("日常火源") ) {
                                if( StringUtils.isNotEmpty(checkResult) ){
        							if( StringUtils.isEmpty(fireSource) ){
        								fireSource = checkResult ;
        							} else {
        								fireSource += " / " + checkResult ;
        							}
                                }
    							flgFireSource = true;
    						}
//    					}
    				}
				}
				
//				fireRefuge = StringUtils.isEmpty(fireRefuge) ? "O" : fireRefuge ;
//				fireProtection = StringUtils.isEmpty(fireProtection) ? "O" : fireProtection ;
//				electricDevice = StringUtils.isEmpty(electricDevice) ? "O" : electricDevice ;
//				fireSource = StringUtils.isEmpty(fireSource) ? "O" : fireSource ;
				
				if(flgFireRefuge) {
				    fireRefuge = StringUtils.isEmpty(fireRefuge) ? "O" : fireRefuge;
				} else {
				    fireRefuge = StringUtils.isEmpty(checkSummary.getFireRefuge()) ? "" : checkSummary.getFireRefuge();
				}
				if(flgFireProtection) {
				    fireProtection = StringUtils.isEmpty(fireProtection) ? "O" : fireProtection;
                } else {
                    fireProtection = StringUtils.isEmpty(checkSummary.getFireProtection()) ? "" : checkSummary.getFireProtection();
                }
                if(flgElectricDevice) {
                    electricDevice = StringUtils.isEmpty(electricDevice) ? "O" : electricDevice;
                } else {
                    electricDevice = StringUtils.isEmpty(checkSummary.getElectricDevice()) ? "" : checkSummary.getElectricDevice();
                }
                if(flgFireSource) {
                    fireSource = StringUtils.isEmpty(fireSource) ? "O" : fireSource;
                } else {
                    fireSource = StringUtils.isEmpty(checkSummary.getFireSource()) ? "" : checkSummary.getFireSource();
                }
				
				checkSummary.setFireRefuge(fireRefuge.length() > 300 ? ( fireRefuge.substring(0, 297) + "..." ) : fireRefuge );
				checkSummary.setFireProtection(fireProtection.length() > 300 ? ( fireProtection.substring(0, 297) + "..." ) : fireProtection );
				checkSummary.setElectricDevice(electricDevice.length() > 300 ? ( electricDevice.substring(0, 297) + "..." ) : electricDevice );
				checkSummary.setFireSource(fireSource.length() > 300 ? ( fireSource.substring(0, 297) + "..." ) : fireSource );
				
				// 無需檢查日常火源的場所，寫入: 不需檢查
				long isNeedDailyCheckFire = checkSummaryService.queryIsNeedDailyCheckFireByPlaceId(placeId);
				if(isNeedDailyCheckFire == 0) {
				    fireSource = "不需檢查";
				    checkSummary.setFireSource(fireSource);
				}
				
				if( needAdd ){
					checkSummary.setCtNote("");
					checkSummaryService.insertCheckSummary(checkSummary);
				}else{
					checkSummaryService.syncSummaryFourClass(checkSummary.getFcsId(), fireRefuge, fireProtection, electricDevice, fireSource, userName, userId, userIp);
				}
				
				checkSummaryService.updateFireRecordAfterSync(placeId, year, month, userName, userId, userIp);
			}
						
	        logger.debug("queryCheckSummaryList() checkSummaryList.size() = "+ checkSummaryList.size());
		}catch( Exception e ){
			logger.error("queryCheckSummaryList() has error : " + e.getMessage() , e );
			msg = "查詢失敗，錯誤訊息：\n" + e.getMessage();
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("checkSummaryList", checkSummaryList);
		}
        
        return resultMap;
    }
	
	/**
	 * 
	 * transferUserPlaceToCheckSummary 說明: 轉換人員場所對應物件為檢查總表物件.<br/>
	 * <br/>   
	 * @param userPlace			人員場所對應物件
	 * @return FireCheckSummary
	 * @author Tom Lai 2017年/08/09
	 */
	private FireCheckSummary transferUserPlaceToCheckSummary( UserPlaceMappingInfo userPlace){
		FireCheckSummary checkSummary = new FireCheckSummary();
		
		checkSummary.setPlace(userPlace.getPlace());
		checkSummary.setPlaceId(userPlace.getPlaceId());
		checkSummary.setUnitCode(userPlace.getUnitCode() == null ? "" : userPlace.getUnitCode());
		
		checkSummary.setManager(userPlace.getManager());
		checkSummary.setManagerId(userPlace.getManagerId());
		checkSummary.setExaminer1(userPlace.getExaminer1());
		checkSummary.setExaminer1Id(userPlace.getExaminer1Id());
		checkSummary.setExaminer2(userPlace.getExaminer2());
		checkSummary.setExaminer2Id(userPlace.getExaminer2Id());
		
		return checkSummary ;
	}
	
	/**
	 * 
	 * queryCheckRecordList 說明: 取得自我檢查項清單.<br/>
	 * <br/>   
	 * @param placeId	場所辨識碼
	 * @param year		檢查年度
	 * @param month		檢查月份
	 * @return Map<String,Object>
	 * @author Tom Lai 2017/08/09
	 */
	@RequestMapping(value = "/queryCheckRecordList", method = RequestMethod.POST)
    @ResponseBody
	private Map<String,Object> queryCheckRecordList(@RequestParam(value="placeId") String placeId , @RequestParam(value="year") String year , @RequestParam(value="month") String month) {
		logger.debug("queryCheckRecordList() placeId = " + placeId + " year = " + year + " month = " + month);
		Map<String, Object> resultMap = new HashMap<>();
		List<FireCheckRecord> checkRecordList = new ArrayList<FireCheckRecord>();
		String msg = "" ;
		try{
			checkRecordList = checkSummaryService.queryCheckRecordList(placeId, year, month);
			logger.debug("queryCheckRecordList() checkRecordList.size = " + checkRecordList.size());
		}catch(Exception e){
			logger.error("queryCheckRecordList() has error : " + e.getMessage() , e );
			msg = "查詢失敗，錯誤訊息：\n" + e.getMessage();
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("checkRecordList", checkRecordList);
		}
		
		return resultMap ;
	}
	
	/**
	 * 
	 * updateCheckSummaryCtNote 說明: 更新檢查總表本山備註.<br/>
	 * <br/>   
	 * @param fcsId		總表ID
	 * @param ctNote	本山備註
	 * @return Map<String,Object>
	 * @author Tom Lai 2017/08/09
	 */
	@RequestMapping(value = "/updateCheckSummaryCtNote", method = RequestMethod.POST)
    @ResponseBody
	private Map<String,Object> updateCheckSummaryCtNote(@RequestParam(value="fcsId") Long fcsId , @RequestParam(value="ctNote") String ctNote ) {
		logger.debug("updateCheckSummaryCtNote() fcsId = " + fcsId + " ctNote = " + ctNote );
		Map<String, Object> resultMap = new HashMap<>();
		String msg = "" ;
		boolean isSuccess = false ;
		Long token = authService.getToken(request);
	    AdminEmpSession aes = authService.getAdminEmpSession(token);
	    String updatorId = aes.getAesUserId();
	    String updatorName = aes.getAesUserName();
		String updatorIp = request.getRemoteAddr();
		try{
			checkSummaryService.updateCheckSummaryCtNote(fcsId, ctNote, updatorId, updatorName, updatorIp);
			msg = "更新成功。" ;
			isSuccess = true ;
		}catch(Exception e){
			logger.error("updateCheckSummaryCtNote() has error : " + e.getMessage() , e );
			msg = "更新失敗，錯誤訊息：\n" + e.getMessage();
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("success", isSuccess);
		}
		
		return resultMap ;
	}
	
	/**
	 * 
	 * updateCheckRecordCtNote 說明: 更新自我檢查項本山備註.<br/>
	 * <br/>   
	 * @param fcrId		自我檢查項 ID
	 * @param ctNote	本山備註
	 * @return Map<String,Object>
	 * @author Tom Lai 2017/08/09
	 */
	@RequestMapping(value = "/updateCheckRecordCtNote", method = RequestMethod.POST)
    @ResponseBody
	private Map<String,Object> updateCheckRecordCtNote(@RequestParam(value="fcrId") Long fcrId , @RequestParam(value="ctNote") String ctNote ) {
		logger.debug("updateCheckRecordCtNote() fcrId = " + fcrId + " ctNote = " + ctNote );
		Map<String, Object> resultMap = new HashMap<>();
		String msg = "" ;
		boolean isSuccess = false ;
		Long token = authService.getToken(request);
	    AdminEmpSession aes = authService.getAdminEmpSession(token);
	    String updatorId = aes.getAesUserId();
	    String updatorName = aes.getAesUserName();
		String updatorIp = request.getRemoteAddr();
		try{
			checkSummaryService.updateCheckRecordCtNote(fcrId, ctNote, updatorId, updatorName, updatorIp);
			msg = "更新成功。" ;
			isSuccess = true ;
		}catch(Exception e){
			logger.error("updateCheckRecordCtNote() has error : " + e.getMessage() , e );
			msg = "更新失敗，錯誤訊息：\n" + e.getMessage();
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("success", isSuccess);
		}
		
		return resultMap ;
	}
	
	/**
	 * 
	 * queryExportSummaryList 說明: 取得匯出的檢查總表清單.<br/>
	 * <br/>   
	 * @param placeType	場所分類
	 * @param year		檢查年度
	 * @param month		檢查月份
	 * @return Map<String,Object>
	 * @author Tom Lai 2017/08/09
	 */
	@RequestMapping(value = "/queryExportSummaryList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryExportSummaryList(@RequestParam(value="placeType") String placeType , @RequestParam(value="year") String year , @RequestParam(value="month") String month) {
		logger.debug("queryExportSummaryList() placeType = " + placeType + " year = " + year + " month = " + month);
		Map<String, Object> resultMap = new HashMap<>();
		List<FireCheckSummary> exportSummaryList = new ArrayList<FireCheckSummary>();
		String msg = "" ;
		try{
			exportSummaryList = checkSummaryService.queryCheckSummaryList(placeType, year, month);
			logger.debug("queryExportSummaryList() exportSummaryList.size = " + exportSummaryList.size());
		}catch(Exception e){
			logger.error("queryExportSummaryList() has error : " + e.getMessage() , e );
			msg = "查詢失敗，錯誤訊息：\n" + e.getMessage();
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("exportSummaryList", exportSummaryList);
		}
		
		return resultMap ;
	}
}
