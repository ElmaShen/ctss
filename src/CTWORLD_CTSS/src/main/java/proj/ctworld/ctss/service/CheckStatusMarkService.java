package proj.ctworld.ctss.service;

import java.util.List;

import proj.ctworld.ctss.orm.FireCheckRecord;

/**
 * 
 * 【程式名稱】: CheckStatusMarkService <br/>
 * 【功能名稱】: server for 問項狀態處理<br/>
 * 【功能說明】: 執行 問題項狀態處理 相關動作<br/>
 * 【建立日期】: 2017/08/01 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
public interface CheckStatusMarkService {
		
	/**
	 * 
	 * queryCheckStatusList 說明: 依狀態及場所辨識碼取得所有問題項.<br/>
	 * <br/>   
	 * @param checkStatus	狀態
	 * @param placeId		場所辨識碼
	 * @return List<FireCheckRecord>
	 * @author Tom Lai 2017/08/01
	 */
	public List<FireCheckRecord> queryCheckStatusList(String checkStatus ,String placeId);

	/**
	 * 
	 * queryCheckStatusList 說明: 依場所辨識碼取得所有問題項.<br/>
	 * <br/>
	 * @param placeId		場所辨識碼
	 * @return List<FireCheckRecord>
	 * @author Tom Lai 2017/08/01
	 */
	public List<FireCheckRecord> queryCheckStatusList(String placeId);
	
	/**
	 * 
	 * saveFireCheckRecord 說明: 儲存變更問題項.<br/>
	 * <br/>   
	 * @param placeId		場所辨識碼
     * @param checkId		檢查碼
     * @param checkStatus	問題項狀態
     * @param checkSymbol	問題項符號
     * @param checkResult	問題項處置情形
	 * @param userId		使用者ID
	 * @param userName		使用者姓名
	 * @param userIp 		使用者IP
	 * @return void
	 * @author Tom Lai 2017/08/03
	 */
	public void saveFireCheckRecord(String placeId , String checkId , String checkStatus , String checkSymbol , String checkResult , String userId , String userName , String userIp , boolean isWaitUpdateToSummary);
	
	/**
	 * 
	 * saveFireCheckRecord 說明: 儲存變更問題項.<br/>
	 * <br/>   
	 * @param fcrId			問題項ID
	 * @param checkStatus	場所辨識碼
     * @param checkId		檢查碼
     * @param checkStatus	問題項狀態
     * @param checkSymbol	問題項符號
     * @param checkResult	問題項處置情形
	 * @param userId		使用者ID
	 * @param userName		使用者姓名
	 * @param userIp 		使用者IP
	 * @return void
	 * @author Tom Lai 2017/08/07
	 */
	public void saveFireCheckRecord(Long fcrId , String checkStatus , String checkSymbol , String checkResult , String userId , String userName , String userIp , boolean isWaitUpdateToSummary);
}
