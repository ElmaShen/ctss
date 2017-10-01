package proj.ctworld.ctss.service;

import java.util.List;

import proj.ctworld.ctss.orm.FireCheckRecord;

/**
 * 
 * 【程式名稱】: CheckResultFollowService <br/>
 * 【功能名稱】: server for 進度追蹤<br/>
 * 【功能說明】: 執行 進度追蹤 相關動作<br/>
 * 【建立日期】: 2017/08/04 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
public interface CheckResultFollowService {
		
	/**
	 * 
	 * queryResultFollowList 說明: 依狀態取得所有進度.<br/>
	 * <br/>   
	 * @param checkStatus	狀態
	 * @return List<FireCheckRecord>
	 * @author Tom Lai 2017/08/04
	 */
	public List<FireCheckRecord> queryResultFollowList(String checkStatus);

	/**
	 * 
	 * queryResultFollowList 說明: 取得所有進度.<br/>
	 * <br/>
	 * @return List<FireCheckRecord>
	 * @author Tom Lai 2017/08/04
	 */
	public List<FireCheckRecord> queryResultFollowList();
	
}
