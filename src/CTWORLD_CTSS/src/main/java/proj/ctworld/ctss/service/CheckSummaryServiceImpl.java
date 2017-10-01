package proj.ctworld.ctss.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import proj.ctworld.ctss.constant.ExportColumnfield;
import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.orm.FireCheckSummary;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.repository.FireCheckRecordRepository;
import proj.ctworld.ctss.repository.FireCheckSummaryRepository;
import proj.ctworld.ctss.repository.UserPlaceMappingInfoRepository;

/**
 * 
 * 【程式名稱】: CheckSummaryServiceImpl <br/>
 * 【功能名稱】: 實作 CheckSummaryService<br/>
 * 【功能說明】: 執行 檢查總表 相關動作<br/>
 * 【建立日期】: 2017/08/04 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
@Service("CheckSummaryService")
@Component
public class CheckSummaryServiceImpl implements CheckSummaryService{
	
	@Autowired
	private FireCheckSummaryRepository fireCheckSummaryRepository;
	
	@Autowired
	private FireCheckRecordRepository fireCheckRecordRepository;
	
	@Autowired
	private UserPlaceMappingInfoRepository userPlaceMappingInfoRepository;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd a HH:mm") ;

	@Override
	public List<FireCheckSummary> queryCheckSummaryList(String placeType , String year , String month) {
		List<FireCheckSummary> checkSummaryList = fireCheckSummaryRepository.queryCheckSummaryList(placeType, year, month);
		return checkSummaryList;
	}
	
	@Override
	public List<FireCheckRecord> queryCheckRecordList(String placeId , String year , String month ) {
		return fireCheckRecordRepository.queryChecKRecordList(placeId, year, month);
	}

	@Override
	public List<UserPlaceMappingInfo> queryIsEnabledUserPlaceList(String placeType) {
		List<UserPlaceMappingInfo> checkSummaryList = userPlaceMappingInfoRepository.queryIsEnabledList(placeType);
		return checkSummaryList;
	}
	
	@Override
	public void insertCheckSummaryList(List<FireCheckSummary> checkSummaryList){
		for( FireCheckSummary checkSummary : checkSummaryList ){
			fireCheckSummaryRepository.save(checkSummary);
		}
	}
	
	@Override
	public void insertCheckSummary( FireCheckSummary checkSummary ){
		fireCheckSummaryRepository.save(checkSummary);
	}
	
	@Override
	public void syncSummaryFourClass(Long fcsId, String fireRefuge, String fireProtection, String electricDevice,
			String fireSource, String updatorName, String updatorId, String updatorIp) {
		fireCheckSummaryRepository.updateCheckSummarDeviceContent(fcsId, fireRefuge, fireProtection, electricDevice, fireSource, updatorName, updatorId, updatorIp);		
	}
	
	@Override
	public void updateFireRecordAfterSync(String placeId, String year, String month, String updatorName, String updatorId, String updatorIp) {
		fireCheckRecordRepository.updateCheckRecordAfterSync(placeId ,year, month, updatorId, updatorName, updatorIp);		
	}
	
	@Override
	public List<String> queryCheckYearList(String placeType){
		
		List<String> checkYearList = fireCheckRecordRepository.queryCheckYear(placeType);
		
		if(CollectionUtils.isEmpty(checkYearList)) {
			checkYearList = new ArrayList<String>();
        }
        // 除了檢查紀錄外，再加入本年度與下一年度
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        
        if(!checkYearList.contains(String.valueOf(currentYear))) {
        	checkYearList.add(0, String.valueOf(currentYear));  
        }
        
        if(!checkYearList.contains(String.valueOf(currentYear + 1))) {
        	checkYearList.add(0, String.valueOf(currentYear + 1));  
        }
        
		return checkYearList ;
	}
	
	@Override
	public List<String> queryPlaceTypeList(){
		List<String> placeTypeList = userPlaceMappingInfoRepository.queryPlaceTypeList();
		return placeTypeList ;
	}
	
	@Override
	public void updateCheckSummaryCtNote(Long fcsId , String ctNote, String updatorId, String updatorName , String updatorIp){
		fireCheckSummaryRepository.updateCheckSummarCtNote(fcsId, ctNote, updatorId, updatorName, updatorIp);
	}
	
	@Override
	public void updateCheckRecordCtNote(Long fcrId , String ctNote, String updatorId, String updatorName , String updatorIp){
		fireCheckRecordRepository.updateCheckRecordCtNote(fcrId, ctNote, updatorId, updatorName, updatorIp);
	}

	@Override
	public Map<String, Object> getExportData(String placeType, String year, String month) {
		
		Map<String , Object> dataMap = new HashMap<>();
		
		dataMap.put("sheetName", placeType);
		
		String title = "中台禪寺" + ( new Integer(year).intValue() - 1911 ) + "年" + month + "月 "  ;
		if( placeType.equals("精舍單位") ){
			title += "精舍消防自行檢查總表" ;
		} else {
			title += "消防自行檢查總表-" + placeType ;
		}
		
		dataMap.put("title", title);
		
		String dateTitle = "列印日期：" + dateFormat.format(Calendar.getInstance().getTime()) ;
		
		dataMap.put("dateTitle", dateTitle);
		
		List<ExportColumnfield> headerList = getHeaderList(placeType);
		
		dataMap.put("headerList", headerList);
		
		List<FireCheckSummary> summaryList = queryCheckSummaryList(placeType, year, month);
		
		List<List<String>> rowDataList = getRowDataList(summaryList, headerList); 
		
		dataMap.put("rowDataList", rowDataList);
		
		return dataMap;
	}
	
	private List<ExportColumnfield> getHeaderList(String placeType){
		List<ExportColumnfield> headerList = new ArrayList<>();
		
		ExportColumnfield[] dataArray = { ExportColumnfield.PLACE , ExportColumnfield.FIRE_REFUGE , 
				ExportColumnfield.FIRE_PROTECTION , ExportColumnfield.ELETRIC_DEVICE , 
				ExportColumnfield.FIRE_SOURCE , ExportColumnfield.CT_NOTE };
		
		ExportColumnfield[] userArray = { ExportColumnfield.MANAGER , ExportColumnfield.EXAMINER1 , 
				ExportColumnfield.EXAMINER2 } ;
		
		if( placeType.equals("精舍單位") ){
			headerList.add(ExportColumnfield.UNIT_CODE);
			headerList.addAll(Arrays.asList(dataArray));
			headerList.addAll(Arrays.asList(userArray));
		} else {
			headerList.addAll(Arrays.asList(userArray));
			headerList.addAll(Arrays.asList(dataArray));
		}
		
		return headerList ;
	}
	
	private List<List<String>> getRowDataList(List<FireCheckSummary> summaryList , List<ExportColumnfield> headerList){
		List<List<String>> rowDataList = new ArrayList<>();
		for( FireCheckSummary checkSummary : summaryList ){
			List<String> rowData = new ArrayList<>();
			for( ExportColumnfield column : headerList ){
				switch( column ){
					case UNIT_CODE :
						rowData.add(checkSummary.getUnitCode());
						break;
					case PLACE :
						rowData.add(checkSummary.getPlace());
						break;
					case FIRE_REFUGE :
						rowData.add(checkSummary.getFireRefuge());
						break;
					case FIRE_PROTECTION :
						rowData.add(checkSummary.getFireProtection());
						break;
					case ELETRIC_DEVICE :
						rowData.add(checkSummary.getElectricDevice());
						break;
					case FIRE_SOURCE :
						rowData.add(checkSummary.getFireSource());
						break;
					case CT_NOTE :
						rowData.add(checkSummary.getCtNote());
						break;
					case MANAGER :
						rowData.add(checkSummary.getManager());
						break;
					case EXAMINER1 :
						rowData.add(checkSummary.getExaminer1());
						break;
					case EXAMINER2 :
						rowData.add(checkSummary.getExaminer2());
						break;
				}
			}
			rowDataList.add(rowData);
		}
		return rowDataList ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HSSFWorkbook createExcel(Map<String, Object> dataMap) {
		
		String sheetName = (String) dataMap.get("sheetName");
        String title = (String) dataMap.get("title");
        String dateTitle = (String) dataMap.get("dateTitle");
        List<ExportColumnfield> headerList = (List<ExportColumnfield>) dataMap.get("headerList");
        List<List<String>> rowDataList = (List<List<String>>) dataMap.get("rowDataList");
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth((short) 12);
        
        int currentRow = 0;
        short currentColumn = 0;
        
        currentRow = setExcelTitle(title, currentRow, currentColumn, sheet, workbook);
        currentRow = setExcelTitle(dateTitle, currentRow, currentColumn, sheet, workbook);
        
        currentRow = setExcelHeader(headerList, currentRow, currentColumn, sheet, workbook);
        
        setExcelDataCell(rowDataList, currentRow, currentColumn, sheet, workbook);
        
        setExcelColumnMerged(sheet);
		
		return workbook;
	}
	
	private int setExcelTitle(String title , int currentRow , short currentColumn , Sheet sheet , HSSFWorkbook workbook ){
		Row titleRow = null;
        
        HSSFRichTextString text = new HSSFRichTextString(title);
		
		titleRow = sheet.createRow(currentRow);
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        
        Cell cell = titleRow.createCell(currentColumn); 
        cell.setCellStyle(titleStyle);
        cell.setCellValue(text);
        
        if( currentRow == 0 ){
            titleRow.setHeightInPoints(25);
            titleFont.setFontHeightInPoints((short)14);
        	titleStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION); 
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        } else {
        	titleStyle.setAlignment(HorizontalAlignment.RIGHT);
        }
        
        return ++currentRow ;
	}
	
	private int setExcelHeader(List<ExportColumnfield> headerList , int currentRow , short currentColumn , Sheet sheet , HSSFWorkbook workbook ){
		
		CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont); 
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        
        Row headerRow = sheet.createRow(currentRow);
        
        for(ExportColumnfield header:headerList){
        	
            HSSFRichTextString text = new HSSFRichTextString(header.getText());
            Cell cell = headerRow.createCell(currentColumn); 
            cell.setCellStyle(headerStyle);
            cell.setCellValue(text);      
            currentColumn++;
            
        }
        
        return ++currentRow ;
	}
	
	private void setExcelDataCell(List<List<String>> rowDataList , int currentRow , short currentColumn , Sheet sheet , HSSFWorkbook workbook ){
		CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        
        if(!CollectionUtils.isEmpty(rowDataList)) {
            for(List<String> rowData: rowDataList){
                currentColumn = 0;
                Row row = sheet.createRow(currentRow);
                
                for(String value : rowData) { 
                    Cell cell = row.createCell(currentColumn);
                    HSSFRichTextString text = new HSSFRichTextString(value);  
                    cell.setCellStyle(cellStyle);  
                    cell.setCellValue(text);   
                        
                    currentColumn++;
                }
                currentRow++;
            }
        }
	}
	
	private void setExcelColumnMerged( Sheet sheet ){
		int totalColumn = sheet.getRow(2).getPhysicalNumberOfCells();

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, totalColumn-1));
        
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, totalColumn-1));
        
        /*
        if("日常火源".equals(sheetName)) {
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4)); 
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, totalColumn-1));
            
        } else {
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1)); 
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, totalColumn-1));
        }
        */

        for(int i=0; i<totalColumn; i++) {  
        	//sheet.autoSizeColumn((short)i);
        	if(i==3){
        		sheet.setColumnWidth(i, (short)256*40);
        	}else{
        		sheet.setColumnWidth(i, (short)256*15);
        	}
        }
	}

	/*
	 * (non-Javadoc)
	 * @see proj.ctworld.ctss.service.CheckSummaryService#queryCheckRecordChangeList(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
    public List<FireCheckRecord> queryCheckRecordChangeList(String placeId, String year, String month) {
        return fireCheckRecordRepository.findByPlaceIdAndCheckYearAndCheckMonthAndIsWaitUpdateToSummaryOrderByCheckId(placeId, year, month, true);
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.CheckSummaryService#queryIsNeedDailyCheckFireByPlaceId(java.lang.String)
     */
    @Override
    public long queryIsNeedDailyCheckFireByPlaceId(String placeId) {
        return userPlaceMappingInfoRepository.getIsNeedDailyCheckFireByPlaceId(placeId);
    }
}
