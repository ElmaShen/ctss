
package proj.ctworld.ctss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.repository.FireCheckRecordRepository;

/**
 * 
 * 【程式名稱】: CheckResultFollowServiceImpl <br/>
 * 【功能名稱】: 實作 CheckResultFollowService<br/>
 * 【功能說明】: 執行 進對追蹤 相關動作<br/>
 * 【建立日期】: 2017/08/04 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
@Service("CheckResultFollowService")
@Component
public class CheckResultFollowServiceImpl implements CheckResultFollowService{
	
	@Autowired
	private FireCheckRecordRepository fireCheckRecordRepository;

	@Override
	public List<FireCheckRecord> queryResultFollowList(String checkStatus) {
		List<FireCheckRecord> resultFollowList = fireCheckRecordRepository.queryResultFollowList(checkStatus);
		return resultFollowList;
	}

	@Override
	public List<FireCheckRecord> queryResultFollowList(){
		List<FireCheckRecord> resultFollowList = fireCheckRecordRepository.queryResultFollowList();
		return resultFollowList;
	}

}
