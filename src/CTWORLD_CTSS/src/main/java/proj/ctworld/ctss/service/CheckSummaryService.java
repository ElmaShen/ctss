package proj.ctworld.ctss.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.orm.FireCheckSummary;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;

/**
 * 
 * 【程式名稱】: CheckSummaryService <br/>
 * 【功能名稱】: server for 檢查總表<br/>
 * 【功能說明】: 執行 進度追蹤 相關動作<br/>
 * 【建立日期】: 2017/08/04 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
public interface CheckSummaryService {
	
	/**
	 * 
	 * queryCheckSummaryList 說明: 取得總表清單.<br/>
	 * <br/>   
	 * @param placeType	場所分類
	 * @param year		年
	 * @param month		月
	 * @return List<FireCheckSummary>
	 * @author Tom Lai 2017/08/09
	 */
	public List<FireCheckSummary> queryCheckSummaryList(String placeType , String year , String month);
	
	/**
	 * 
	 * queryCheckRecordList 說明: 取得自我檢查項清單.<br/>
	 * <br/>   
	 * @param placeId	場所辨識碼
	 * @param year		年
	 * @param month		月
	 * @return List<FireCheckRecord>
	 * @author Tom Lai 2017/08/09
	 */
	public List<FireCheckRecord> queryCheckRecordList(String placeId , String year , String month);
	
	/**
	 * 
	 * queryIsEnabledUserPlaceList 說明: 取得啟用的使用者場所對應清單.<br/>
	 * <br/>   
	 * @param placeType	場所分類
	 * @return List<UserPlaceMappingInfo>
	 * @author Tom Lai 2017/08/09
	 */
	public List<UserPlaceMappingInfo> queryIsEnabledUserPlaceList(String placeType);
	
	/**
	 * 
	 * syncSummaryFourClass 說明: 同步總表 4 類內容.<br/>
	 * <br/>   
	 * @param fcsId				總表 ID
	 * @param fireRefuge		防火避難設施
	 * @param fireProtection	消防安全設備
	 * @param electricDevice	電器安全
	 * @param fireSource		日常火源
	 * @param updatorName		更新人員姓名
	 * @param updatorId			更新人員編號
	 * @param updatorIp 		更新人員IP
	 * @return	void
	 * @author Tom Lai 2017/08/09
	 */
	public void syncSummaryFourClass(Long fcsId, String fireRefuge, String fireProtection, String electricDevice,
			String fireSource, String updatorName, String updatorId, String updatorIp);
	
	/**
	 * 
	 * updateFireRecordAfterSync 說明: 更新自我檢查表已同步完成.<br/>
	 * <br/>   
	 * @param placeId		場所辨識碼
	 * @param year			年
	 * @param month			月
	 * @param updatorName	更新人員姓名
	 * @param updatorId		更新人員編號
	 * @param updatorIp		更新人員IP 
	 * @return void
	 * @author Tom Lai 2017/08/09
	 */
	public void updateFireRecordAfterSync(String placeId, String year, String month, String updatorName, String updatorId, String updatorIp);
	
	/**
	 * 
	 * insertCheckSummaryList 說明: 新增總表清單.<br/>
	 * <br/>   
	 * @param checkSummaryList	總表清單 
	 * @return void
	 * @author Tom Lai 2017/08/09
	 */
	public void insertCheckSummaryList(List<FireCheckSummary> checkSummaryList);
	
	/**
	 * 
	 * insertCheckSummary 說明: 新增總表.<br/>
	 * <br/>   
	 * @param checkSummary	總表 
	 * @return void
	 * @author Tom Lai 2017/08/09
	 */
	public void insertCheckSummary(FireCheckSummary checkSummary);

	/**
	 * 
	 * queryCheckYearList 說明: 取得檢查年度清單.<br/>
	 * <br/>   
	 * @param placeType	場所分類
	 * @return List<String>
	 * @author Tom Lai 2017/08/09
	 */
	public List<String> queryCheckYearList(String placeType);
	
	/**
	 * 
	 * queryPlaceTypeList 說明: 取得場所分類清單.<br/>
	 * <br/>   
	 * @return List<String>
	 * @author Tom Lai 2017/08/09
	 */
	public List<String> queryPlaceTypeList();
	
	/**
	 * 
	 * updateCheckSummaryCtNote 說明: 更新總表本山備註.<br/>
	 * <br/>   
	 * @param fcsId			總表ID
	 * @param ctNote		本山備註
	 * @param updatorId		更新人員編號
	 * @param updatorName	更新人員姓名
	 * @param updatorIp		更新人員IP 
	 * @return void
	 * @author Tom Lai 2017/08/09
	 */
	public void updateCheckSummaryCtNote(Long fcsId, String ctNote, String updatorId, String updatorName , String updatorIp);
	
	/**
	 * 
	 * updateCheckRecordCtNote 說明: 更新自我檢查項本山備註.<br/>
	 * <br/>   
	 * @param fcrId			自我檢查項ID
	 * @param ctNote		本山備註
	 * @param updatorId		更新人員ID
	 * @param updatorName	更新人員姓名
	 * @param updatorIp		更新人員IP 
	 * @return void
	 * @author Tom Lai 2017/08/09
	 */
	public void updateCheckRecordCtNote(Long fcrId, String ctNote, String updatorId, String updatorName , String updatorIp);
	
	/**
	 * 
	 * getExportData 說明: 取得匯出資料.<br/>
	 * <br/>   
	 * @param placeType	場所分類
	 * @param year		年
	 * @param month		月
	 * @return Map<String,Object>
	 * @author Tom Lai 2017/08/09
	 */
	public Map<String,Object> getExportData(String placeType , String year , String month);
	
	/**
	 * 
	 * createExcel 說明: 產生 Excel.<br/>
	 * <br/>   
	 * @param dataMap	Excel 資料集合
	 * @return HSSFWorkbook
	 * @author Tom Lai 2017/08/09
	 */
	public HSSFWorkbook createExcel(Map<String, Object> dataMap);
	
	/**
     * queryCheckRecordChangeList 說明: 取得自我檢查項異動清單.<br/>
     * <br/>   
     * @param placeId   場所辨識碼
     * @param year      年
     * @param month     月
     * @return List<FireCheckRecord>
     * @author JoyceLai 2017/08/28
     */
    public List<FireCheckRecord> queryCheckRecordChangeList(String placeId , String year , String month);
    
    /**
     * queryIsNeedDailyCheckFireByPlaceId 說明: 依據場所別取得是否需檢查日常火源項目.<br/>
     * <br/>   
     * @param placeId   場所辨識碼
     * @return long
     * @author JoyceLai 2017/09/12
     */
    public long queryIsNeedDailyCheckFireByPlaceId(String placeId);
}
