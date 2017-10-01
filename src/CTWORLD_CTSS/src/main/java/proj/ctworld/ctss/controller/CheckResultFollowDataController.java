package proj.ctworld.ctss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.service.CheckResultFollowService;

/**
 * 
 * 【程式名稱】: CheckResultFollowDataController <br/>
 * 【功能名稱】: 進度追蹤資料取得<br/>
 * 【功能說明】: 1.依狀態與場所識別碼取得問題項清單。<br/>
 * 【建立日期】: 2017/08/01 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
@RestController
@RequestMapping(value = "/api/admin/checkResultFollow")
public class CheckResultFollowDataController {
	private static Logger logger = Logger.getLogger(CheckResultFollowDataController.class);
    
    @Autowired
    private CheckResultFollowService checkResultFollowService;

    /**
     * 
     * queryCheckStatusList 說明: 依狀態與場所識別碼取得問題項清單.<br/>
     * <br/>   
     * @param checkStatus	狀態
     * @param placeId		場所識別碼
     * @return Map<String, Object>
     * @author Tom Lai 2017/08/04
     */
	@RequestMapping(value = "/queryResultFollowList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryResultFollowList(@RequestParam(value="checkStatus") String checkStatus ) {
		logger.debug("queryResultFollowList() checkStatus = " + checkStatus );
		Map<String, Object> resultMap = new HashMap<>();
		List<FireCheckRecord> checkResultList = new ArrayList<FireCheckRecord>();
		String msg = "" ;
		try{
			if( StringUtils.isEmpty(checkStatus) ){
				checkResultList = checkResultFollowService.queryResultFollowList();
			}else{
				checkResultList = checkResultFollowService.queryResultFollowList(checkStatus);
			}
	        logger.debug("queryResultFollowList() resultList="+ checkResultList);
		}catch( Exception e ){
			logger.error("queryResultFollowList() has error : " + e.getMessage() , e );
			msg = "查詢失敗，錯誤訊息：\n" + e.getMessage();
		}finally{
			resultMap.put("msg", msg);
			resultMap.put("resultList", checkResultList);
		}
        
        return resultMap;
    }
	
}
