package proj.ctworld.ctss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.service.AdminAuthService;
import proj.ctworld.ctss.service.CheckStatusMarkService;

/**
 * 
 * 【程式名稱】: CheckStatusMarkDataController <br/>
 * 【功能名稱】: 問題項狀態處理資料取得<br/>
 * 【功能說明】: 	1.依狀態與場所識別碼取得問題項清單。<br/>
 * 				2.儲存問題項變更。<br/>
 * 【建立日期】: 2017/08/01 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
@RestController
@RequestMapping(value = "/api/admin/checkStatusMark")
public class CheckStatusMarkDataController {
	private static Logger logger = Logger.getLogger(CheckStatusMarkDataController.class);
	
	@Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private CheckStatusMarkService checkStatusMarkService;

    /**
     * 
     * queryCheckStatusList 說明: 依狀態與場所識別碼取得問題項清單.<br/>
     * <br/>   
     * @param checkStatus	狀態
     * @param placeId		場所識別碼
     * @return Map<String, Object>
     * @author Tom Lai 2017/08/01
     */
	@RequestMapping(value = "/queryCheckStatusList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryCheckStatusList(@RequestParam(value="checkStatus") String checkStatus, 
    		@RequestParam(value="placeId") String placeId) {
		logger.debug("queryCheckStatusList() checkStatus = " + checkStatus + " placeId = " + placeId);
		Map<String, Object> resultMap = new HashMap<>();
		List<FireCheckRecord> checkStatusList = new ArrayList<FireCheckRecord>();
		String msg = "" ;
		try{
			if( StringUtils.isEmpty(checkStatus) ){
				checkStatusList = checkStatusMarkService.queryCheckStatusList(placeId);
			}else{
				checkStatusList = checkStatusMarkService.queryCheckStatusList(checkStatus,placeId);
			}
	        logger.debug("getCheckStatusList() resultList="+ checkStatusList);
		}catch( Exception e ){
			logger.error("getCheckStatusList() has error : " + e.getMessage() , e );
			msg = "查詢失敗，錯誤訊息：\n" + e.getMessage();
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("resultList", checkStatusList);
		}
        
        return resultMap;
    }
	
	
    /**
     * 
     * saveFireCheckRecord 說明: 儲存問題項變更.<br/>
     * <br/>   
     * @param placeId		場所辨識碼
     * @param checkId		檢查碼
     * @param checkStatus	問題項狀態
     * @param checkSymbol	問題項符號
     * @param checkResult	問題項處置情形
     * @return Map<String,Object>
     * @author Tom Lai 2017/08/03
     */
	@RequestMapping(value = "/saveFireCheckRecord", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveFireCheckRecord(@RequestParam(value= "placeId") String placeId , String checkId , String checkStatus 
    		, String checkSymbol , String checkResult , Long fcrId , boolean isWaitUpdateToSummary ){
		Map<String,Object> resultMap = new HashMap<>();
		Long token = authService.getToken(request);
        AdminEmpSession aes = authService.getAdminEmpSession(token);
		logger.debug("saveFireCheckRecord() placeId : " + placeId + " ,checkId : " + checkId + " ,checkStatus : " + checkStatus + " ,checkSymbol : " + checkSymbol + " ,checkResult : " + checkResult ); 
		String msg = "編輯完成。" ;
		boolean isSuccess = false ;
		//boolean isWaitUpdateToSummary = checkStatus.equals("O") ? false : true ;
		try{
			//checkStatusMarkService.saveFireCheckRecord(placeId, checkId, checkStatus, checkSymbol, checkResult , aes.getAesUserId() , aes.getAesUserName() , request.getRemoteAddr() , isWaitUpdateToSummary);
			checkStatusMarkService.saveFireCheckRecord(fcrId, checkStatus, checkSymbol, checkResult , aes.getAesUserId() , aes.getAesUserName() , request.getRemoteAddr() , isWaitUpdateToSummary);
			isSuccess = true ;
		}catch(Exception e){
			logger.error("saveFireCheckRecord has error : " + e.getMessage() , e );
			msg = "編輯失敗，錯誤訊息: \n" + e.getMessage() ;
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("success", isSuccess);
		}
		
		return resultMap ;
	}
	
}
