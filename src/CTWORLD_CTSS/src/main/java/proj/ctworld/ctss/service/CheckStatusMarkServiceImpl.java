package proj.ctworld.ctss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.repository.FireCheckRecordRepository;

/**
 * 
 * 【程式名稱】: CheckStatusMarkServiceImpl <br/>
 * 【功能名稱】: 實作 CheckStatusMarkService<br/>
 * 【功能說明】: 執行 問題項狀態處理 相關動作<br/>
 * 【建立日期】: 2017/08/01 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
@Service("CheckStatusMarkService")
@Component
public class CheckStatusMarkServiceImpl implements CheckStatusMarkService{
	
	@Autowired
	private FireCheckRecordRepository fireCheckRecordRepository;

	@Override
	public List<FireCheckRecord> queryCheckStatusList(String checkStatus,String placeId) {
		List<FireCheckRecord> checkStatusList = fireCheckRecordRepository.queryCheckStatusList(checkStatus, placeId);
		return checkStatusList;
	}

	@Override
	public List<FireCheckRecord> queryCheckStatusList(String placeId){
		List<FireCheckRecord> checkStatusList = fireCheckRecordRepository.queryCheckStatusList(placeId);
		return checkStatusList;
	}

	@Override
	public void saveFireCheckRecord(String placeId, String checkId, String checkStatus, String checkSymbol,
			String checkResult , String userId , String userName , String userIp , boolean isWaitUpdateToSummary) {
		fireCheckRecordRepository.updateCheckStatusMark(placeId, checkId, checkStatus, checkSymbol, checkResult , userId , userName , userIp , isWaitUpdateToSummary);
	}

	@Override
	public void saveFireCheckRecord(Long fcrId, String checkStatus, String checkSymbol, String checkResult,
			String userId, String userName, String userIp, boolean isWaitUpdateToSummary) {
		fireCheckRecordRepository.updateCheckStatusMark(fcrId, checkStatus, checkSymbol, checkResult, userId, userName, userIp , isWaitUpdateToSummary);
		
	}
}
