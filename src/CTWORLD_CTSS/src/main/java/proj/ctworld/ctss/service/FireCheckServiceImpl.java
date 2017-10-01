package proj.ctworld.ctss.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import org.springframework.util.StringUtils;

import proj.ctworld.ctss.orm.FireCheckItem;
import proj.ctworld.ctss.orm.FireCheckRecord;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.orm.dto.FireCheckRecordDto;
import proj.ctworld.ctss.repository.FireCheckItemRepository;
import proj.ctworld.ctss.repository.FireCheckRecordRepository;
import proj.ctworld.ctss.repository.UserPlaceMappingInfoRepository;

/**
 * 【程式名稱】: FireCheckServiceImpl <br/>
 * 【功能名稱】: 實作 FileCheckService <br/>
 * 【功能說明】: 執行 安檢登錄/調閱 相關動作 <br/>
 * 【建立日期】: 2017/07/25 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
@Service("FireCheckService")
@Component
public class FireCheckServiceImpl implements FireCheckService {
    private static Logger logger = Logger.getLogger(FireCheckServiceImpl.class);
    
    @Autowired
    FireCheckItemRepository fireCheckItemRepository;
    
    @Autowired
    UserPlaceMappingInfoRepository userPlaceMappingInfoRepository;
    
    @Autowired
    FireCheckRecordRepository fireCheckRecordRepository;
    
    private static final SimpleDateFormat DATE_FORMAT_DAY = new SimpleDateFormat("u");
    private static final SimpleDateFormat DATE_FORMAT_10 = new SimpleDateFormat( "yyyy-MM-dd" );
    private static final String[] DAYS = {"一", "二", "三", "四", "五", "六", "日"}; 

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FileCheckService#getCheckType(java.lang.String)
     */
    @Override
    public List<FireCheckItem> getCheckType(String placeId) {
        // 取得所有記錄別
        List<FireCheckItem> resultList = fireCheckItemRepository.queryCheckType();
        
        // 日常火源的顯示需依據場所人員對應表的日常火源控制項決定，is_need_daily_check_fire=1時才需要顯示，=0則不顯示
        long isNeedDailyCheckFire = 0;
        if(!StringUtils.isEmpty(placeId)) {
           isNeedDailyCheckFire = userPlaceMappingInfoRepository.getIsNeedDailyCheckFireByPlaceId(placeId);
        }
        
        if(!CollectionUtils.isEmpty(resultList) && isNeedDailyCheckFire == 0) {
            resultList.remove("日常火源");
        }
        return resultList;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FileCheckService#getCheckFrequenceMonthlyByCheckType(java.lang.String)
     */
    @Override
    public long getCheckFrequenceMonthlyByCheckType(String checkType) {
        long result = 0;
        List<FireCheckItem> resultList = fireCheckItemRepository.queryByCheckType(checkType);
        if(!CollectionUtils.isEmpty(resultList)) {
            result = resultList.get(0).getCheckFrequenceMonthly();
        }
        
        return result;
    }

    /* (non-Javadoc)
     * @see proj.ctworld.ctss.service.FileCheckService#getCheckYear()
     */
    @Override
    public List<String> getCheckYear(String userType, String placeId, String checkType) {
        List<String> resultList = new ArrayList<String>();
        
        if(!"admin".equals(userType)) {
            resultList = fireCheckRecordRepository.queryCheckYear();
            
            if(CollectionUtils.isEmpty(resultList)) {
                resultList = new ArrayList<String>();
            }
            // 除了檢查紀錄外，再加入本年度與下一年度
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            
            if(!resultList.contains(String.valueOf(currentYear))) {
               resultList.add(0, String.valueOf(currentYear));  
            }
            if(!resultList.contains(String.valueOf(currentYear + 1))) {
                resultList.add(0, String.valueOf(currentYear + 1));  
            }
        } else {
            resultList = fireCheckRecordRepository.queryCheckYearData(placeId, checkType);
        }
        
            return resultList;
    }
    
    
    /* (non-Javadoc)
     * @see proj.ctworld.ctss.service.FileCheckService#queryCheckMonthData()
     */
    @Override
    public List<String> getCheckMonth(String userType, String placeId, String checkType, String checkYear) {
        List<String> resultList = new ArrayList<String>();
        
        if("admin".equals(userType)) {
            resultList = fireCheckRecordRepository.queryCheckMonthData(placeId, checkType, checkYear);
        } else {
            for(int i=1; i<=12; i++) {
                String month = String.valueOf(i);
                if(month.length() < 2) {
                    month = "0" + month;
                }
                resultList.add(month);
            }
        }
        
        return resultList;
    }

   /*
    * (non-Javadoc)
    * @see proj.ctworld.ctss.service.FileCheckService#getCheckDate(java.lang.String, java.lang.String, java.lang.String, java.lang.String, long)
    */
    @Override
    public String getCheckDate(String checkYear, String checkMonth, String placeId, String checkType, long checkTimesMark) {
//        return fireCheckRecordRepository.queryCheckDate(checkYear, checkMonth, placeId, checkType, checkTimesMark);
        FireCheckRecord fcr = fireCheckRecordRepository.findFirstByCheckYearAndCheckMonthAndPlaceIdAndCheckTypeAndCheckTimesMarkOrderByCheckDate(checkYear, checkMonth, placeId, checkType, checkTimesMark);
        String checkDateStr = fcr == null ? null : fcr.getCheckDateYYYYMMDD();
        
        return checkDateStr;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FileCheckService#getCheckRecordData(java.lang.String, java.lang.String, java.lang.String, java.lang.String, long, java.lang.String)
     */
    @Override
    public List<FireCheckRecord> getCheckRecordData(String checkYear, String checkMonth, String placeId,
            String checkType, long checkTimesMark, String checkDate) {
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDateTime checkDateLocalDateTime = LocalDateTime.parse(checkDate, dateTimeFormatter);
        
        return fireCheckRecordRepository.queryCheckRecord(checkYear, checkMonth, placeId, checkType, checkTimesMark, checkDateLocalDateTime);
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FileCheckService#queryCheckItemAndCreateRecord()
     */
    @Override
    public List<FireCheckRecord> getCheckItemAndCreateRecord(String checkType, FireCheckRecord recordData) {
        List<FireCheckRecord> resultList = new ArrayList<FireCheckRecord>();
        if(!StringUtils.isEmpty(checkType)) {
            List<FireCheckItem> fireCheckItemList = fireCheckItemRepository.queryByCheckType(checkType);
            if(!CollectionUtils.isEmpty(fireCheckItemList)) {
                FireCheckRecord fireCheckRecord = null;
                for(FireCheckItem fci : fireCheckItemList) {
                    fireCheckRecord = new FireCheckRecord();
                    BeanUtils.copyProperties(recordData, fireCheckRecord);
                    fireCheckRecord.setCheckId(fci.getCheckId());
                    fireCheckRecord.setCheckType(fci.getCheckType());
                    fireCheckRecord.setDeviceType(fci.getDeviceType());
                    fireCheckRecord.setCheckContent(fci.getCheckContent());
                    fireCheckRecord.setCheckFrequenceMonthly(fci.getCheckFrequenceMonthly());
                    
                    resultList.add(fireCheckRecord);
                }
                // DB 欄位均 Nullable 均為No. 無法先新增記錄再修改資料
//                resultList = fireCheckRecordRepository.save(resultList);
            }
        }
        
        return resultList;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FileCheckService#getUserPlaceMappingInfoByUserIdPlaceId(java.lang.String, java.lang.String)
     */
    @Override
    public UserPlaceMappingInfo getUserPlaceMappingInfoByUserIdPlaceId(String userId, String placeId) {
        UserPlaceMappingInfo result = new UserPlaceMappingInfo();
        List<UserPlaceMappingInfo> fcrList = userPlaceMappingInfoRepository.queryByUserIdPlaceId(userId, placeId);
        if(!CollectionUtils.isEmpty(fcrList)) {
            result = fcrList.get(0);
        }
        
        return result;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#getCheckRecordDatas(proj.ctworld.ctss.orm.AdminEmpSession, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, long, java.lang.String)
     */
    @Override
    public List<FireCheckRecord> getCheckRecordDatas(String userId, String userName, String userIp, String checkYear,
            String checkMonth, String placeId, String checkType, long checkTimesMark, String checkDate) {
        
        List<FireCheckRecord> resultList = new ArrayList<FireCheckRecord>();
        FireCheckRecord record = new FireCheckRecord();
        UserPlaceMappingInfo userPlaceInfo = getUserPlaceMappingInfoByUserIdPlaceId(userId, placeId);
        record.setPlaceType(userPlaceInfo.getPlaceType());
        record.setManagerId(userPlaceInfo.getManagerId());
        record.setManager(userPlaceInfo.getManager());
        record.setExaminer1Id(userPlaceInfo.getExaminer1Id());
        record.setExaminer1(userPlaceInfo.getExaminer1());
        record.setExaminer2Id(userPlaceInfo.getExaminer2Id());
        record.setExaminer2(userPlaceInfo.getExaminer2());
        
        record.setCheckDate(LocalDateTime.parse(checkDate));
        record.setCheckYear(checkYear);
        record.setCheckMonth(checkMonth.length()<2 ? "0" + checkMonth : checkMonth);
        record.setPlaceId(placeId);
        record.setPlace(userPlaceInfo.getPlace());
        record.setCheckTimesMark(checkTimesMark);
        
        LocalDateTime currentTime = LocalDateTime.now();
        record.setCreateDt(currentTime);
        record.setCreatorId(userId);
        record.setCreatorName(userName);
        record.setCreatorIp(userIp);
        record.setUpdateDt(currentTime);
        record.setUpdatorId(userId);
        record.setUpdatorName(userName);
        record.setUpdatorIp(userIp);
        resultList = getCheckItemAndCreateRecord(checkType, record);
        
        return resultList;
    }

   
    @Override
    public List<FireCheckRecord> insertCheckRecordData(List<FireCheckRecord> allCheckDatas, List<Map<String, Object>> updateDatas, String managerSign) {
        List<FireCheckRecord> resultList = new ArrayList<FireCheckRecord>();
        
        for(FireCheckRecord fcRecord : allCheckDatas) {
            String checkId = fcRecord.getCheckId();
            
            for(int i=0; i < updateDatas.size(); i++) {
                Map<String, Object> map = updateDatas.get(i);
                if(checkId.equals(String.valueOf(map.get("checkId")))) {
                    String checkSymbol = map.get("checkSymbol") == null ? "" : String.valueOf(map.get("checkSymbol"));
                    String checkResult = map.get("checkResult") == null ? "" : String.valueOf(map.get("checkResult"));
//                    if(!StringUtils.isEmpty(checkResult)) {
//                        checkResult = checkSymbol.concat(":").concat(String.valueOf(map.get("checkResult")));
//                        fcRecord.setIsWaitUpdateToSummary(true);
//                    }                            
                    fcRecord.setCheckSymbol(checkSymbol);
                    fcRecord.setCheckResult(checkResult);
                    fcRecord.setCheckStatus(map.get("checkStatus") == null ? "" : String.valueOf(map.get("checkStatus")));      
                }
            }

            if(!StringUtils.isEmpty(managerSign)) {
                fcRecord.setManagerSign(managerSign);
            }
            fcRecord.setIsWaitUpdateToSummary(true);
        }
        resultList = fireCheckRecordRepository.save(allCheckDatas);
        
        return resultList;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#getFirePreventionFacilities(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<FireCheckRecordDto> getFirePreventionFacilities(String placeId, String checkType, String checkYear,
            String checkMonth) {
        List<FireCheckRecordDto> resultList = new ArrayList<FireCheckRecordDto>();
        List<FireCheckRecordDto> fcrDtoList = fireCheckRecordRepository.queryFirePreventionFacilities(placeId, checkType, checkYear, checkMonth);
        
        long checkTimesMark = fcrDtoList.get(0).getCheckTimesMark();
        
        if(checkTimesMark == 1 && checkTimesMark == fcrDtoList.get(1).getCheckTimesMark()) {
            for(int i=0; i<fcrDtoList.size(); i++) {
                FireCheckRecordDto fcrDto = fcrDtoList.get(i);
                FireCheckRecordDto record = new FireCheckRecordDto(fcrDto.getManager(), fcrDto.getManagerSign(), fcrDto.getCheckContent(), fcrDto.getCheckTimesMark(), fcrDto.getUpdatorName(), fcrDto.getCheckSymbol1(), fcrDto.getCheckResult1(), fcrDto.getCheckDate1Str(), "", "", "");  
                resultList.add(record);
            }
        } else if(checkTimesMark == 2 && checkTimesMark == fcrDtoList.get(1).getCheckTimesMark()) {
            for(int i=0; i<fcrDtoList.size(); i++) {
                FireCheckRecordDto fcrDto = fcrDtoList.get(i);
                FireCheckRecordDto record = new FireCheckRecordDto(fcrDto.getManager(), fcrDto.getManagerSign(), fcrDto.getCheckContent(), fcrDto.getCheckTimesMark(), fcrDto.getUpdatorName(), "", "", "", fcrDto.getCheckSymbol2(), fcrDto.getCheckResult2(), fcrDto.getCheckDate2Str());  
                resultList.add(record);
            }
        } else {
            for(int i=0; i<fcrDtoList.size(); i++) {
                if(i % 2 == 0) {
                    continue;
                }
                FireCheckRecordDto fcrDto = fcrDtoList.get(i);
                FireCheckRecordDto record = new FireCheckRecordDto(fcrDto.getManager(), fcrDto.getManagerSign(), fcrDto.getCheckContent(), fcrDto.getCheckTimesMark(), fcrDto.getUpdatorName(), fcrDtoList.get(i-1).getCheckSymbol1(), fcrDtoList.get(i-1).getCheckResult1(), fcrDtoList.get(i-1).getCheckDate1Str(), fcrDto.getCheckSymbol2(), fcrDto.getCheckResult2(), fcrDto.getCheckDate2Str());  
                resultList.add(record);
            }
        }
                
        return resultList;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#getFireSafetyEquipment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<FireCheckRecordDto> getFireSafetyEquipment(String placeId, String checkType, String checkYear,
            String checkMonth) {
        List<FireCheckRecordDto> fcrDtoList = fireCheckRecordRepository.queryFireSafetyEquipment(placeId, checkType, checkYear, checkMonth);

        return fcrDtoList;
    }
    
    /**
     * getDayOfWeek 說明: 取得星期幾中文.<br/>
     * <br/>   
     * @param year      年
     * @param month     月
     * @param day       日
     * @return String
     * @author JoyceLai 2017/08/23
     */
    private String getDayOfWeek(String year, String month, int day) {
        String result = "";
        String sDay = String.valueOf(day);
        if(day<10) {
            sDay = "0" + sDay;
        }
        String dateStr = year.concat("-").concat(month).concat("-").concat(sDay);
        try {
            Date date = DATE_FORMAT_10.parse(dateStr);
            int dayOfWeek = Integer.parseInt(DATE_FORMAT_DAY.format(date));
            result = DAYS[dayOfWeek-1];
            
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("### FireCheckServiceImpl getDayOfWeek Error!", e);
        }
        
        return result;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#getDailyFireSource(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<FireCheckRecordDto> getDailyFireSource(String placeId, String checkType, String checkYear,
            String checkMonth) {

        List<FireCheckRecordDto> resultList = new ArrayList<FireCheckRecordDto>();
        List<FireCheckRecord> fcrList = fireCheckRecordRepository.queryDailyFireSource(placeId, checkType, checkYear, checkMonth);
        
        try {
            Calendar cal = Calendar.getInstance();  
            cal.set(Calendar.YEAR, Integer.parseInt(checkYear));  
            cal.set(Calendar.MONTH, Integer.parseInt(checkMonth)-1);  
            int lastDay = cal.getActualMaximum(Calendar.DATE);  
//            SimpleDateFormat dateFormat = new SimpleDateFormat("u");
//            SimpleDateFormat dateStringFormat = new SimpleDateFormat( "yyyy-MM-dd" );
            
            for(int i=1; i <= lastDay; i++) {
                String sI = String.valueOf(i);
//                if(i<10) {
//                    sI = "0" + sI;
//                }
//                String dateStr = checkYear.concat("-").concat(checkMonth).concat("-").concat(sI);
//                Date date = dateStringFormat.parse(dateStr);
//                String dayOfWeek = dateFormat.format(date);
//                dayOfWeek = StringUtils.isEmpty(dayOfWeek) ? "" : dayOfWeek.substring(2, 3);
                String dayOfWeek = getDayOfWeek(checkYear, checkMonth, i);
                String manager = "";
                String managerSign = "";
                String updatorName = ""; 
                String symbol1 = "";
                String symbol2 = "";
                String symbol3 = "";
                String symbol4 = "";
                String symbol5 = "";
                String symbol6 = "";
                

                for(int j=0; j<fcrList.size(); j++) {
                    FireCheckRecord fcr = fcrList.get(j);
                    if(i == fcr.getCheckTimesMark()) {
                        manager = fcr.getManager();
                        managerSign = StringUtils.isEmpty(fcr.getManagerSign()) ? "" : fcr.getManagerSign();
                        updatorName = fcr.getUpdatorName();
                        String checkStr = StringUtils.isEmpty(fcr.getCheckResult()) ? fcr.getCheckSymbol() : fcr.getCheckResult();
                        if("D01010".equals(fcr.getCheckId())) {
                            symbol1 = checkStr;
                        } else if("D01020".equals(fcr.getCheckId())) {
                            symbol2 = checkStr;
                        } else if("D01030".equals(fcr.getCheckId())) {
                            symbol3 = checkStr;
                        } else if("D01040".equals(fcr.getCheckId())) {
                            symbol4 = checkStr;
                        } else if("D01050".equals(fcr.getCheckId())) {
                            symbol5 = checkStr;
                        } else if("D01060".equals(fcr.getCheckId())) {
                            symbol6 = checkStr;
                        }                        
                    } 
                }
                FireCheckRecordDto fcrDto = new FireCheckRecordDto(manager, managerSign, sI, dayOfWeek, symbol1, symbol2, symbol3, symbol4, symbol5, symbol6, updatorName);
                resultList.add(fcrDto);
                
            }
            
            
        } catch (Exception e) {
            logger.error("FireCheckService.getDailyFireSource Error!!", e);
        }
        return resultList;
    }
    
    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#updateCheckRecordData(java.util.List, java.util.List, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void updateCheckRecordData(List<FireCheckRecord> oldCheckDatas, List<Map<String, Object>> updateDatas, String managerSign, String userId, String userName, String userIp) {

        LocalDateTime currentTime = LocalDateTime.now();
        for(FireCheckRecord oldRecord : oldCheckDatas) {
            String checkId = oldRecord.getCheckId();
            for(int i=0; i < updateDatas.size(); i++) {
                Map<String, Object> map = updateDatas.get(i);
                if(checkId.equals(String.valueOf(map.get("checkId")))) {
                    String checkSymbol = map.get("checkSymbol") == null ? "" : String.valueOf(map.get("checkSymbol"));
                    String checkResult = map.get("checkResult") == null ? "" : String.valueOf(map.get("checkResult"));
//                    if(":".equals(checkResult.indexOf(1))) {
//                        checkResult = checkResult.substring(2);
//                    }
//                    if(!StringUtils.isEmpty(checkResult)) {
//                        checkResult = checkSymbol.concat(":").concat(checkResult);
//                        oldRecord.setIsWaitUpdateToSummary(true);
//                    }
                    oldRecord.setCheckSymbol(checkSymbol);
                    oldRecord.setCheckResult(checkResult);
                    oldRecord.setCheckStatus(map.get("checkStatus") == null ? "" : String.valueOf(map.get("checkStatus")));      
                    break;
                }
            }
            
            int updateKey = fireCheckRecordRepository.updateRecord(oldRecord.getFcrId(), oldRecord.getCheckSymbol(), oldRecord.getCheckResult(), oldRecord.getCheckStatus(), managerSign, currentTime, userName, userId, userIp, true);
        }
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#createExcelContent(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public HSSFWorkbook createExcelContent(Map<String, Object> dataMap) {
        logger.debug("### createExcelContent Start.....");
        
        //VARIABLES REQUIRED IN MODEL
        String sheetName = (String) dataMap.get("sheetName");
        List<String> titles = (List<String>) dataMap.get("titles");
        List<String> headers = (List<String>) dataMap.get("headers");
        List<List<String>> contents = (List<List<String>>) dataMap.get("contents");
//        HorizontalAlignment[] contentAligns = (HorizontalAlignment[]) dataMap.get("contentAligns"); 
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth((short) 12);
        
        int currentRow = 0;
        short currentColumn = 0;
        
        // 處理 Title 
        Row titleRow = null;
        for(int i=0; i<titles.size(); i++) {
            String title = titles.get(i);
            
            HSSFRichTextString text = new HSSFRichTextString(title);

            if(i == 0) {
                titleRow = sheet.createRow(currentRow);
                titleRow.setHeightInPoints(25);
                CellStyle titleStyle = workbook.createCellStyle();
                titleStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION); 
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                Font titleFont = workbook.createFont();
                titleFont.setFontHeightInPoints((short)14);
                titleFont.setBold(true);
                titleStyle.setFont(titleFont);
                Cell cell = titleRow.createCell(currentColumn); 
                cell.setCellStyle(titleStyle);
                cell.setCellValue(text); 
                
            } else if(i == 1) {
                titleRow = sheet.createRow(currentRow);
                CellStyle titleStyle = workbook.createCellStyle();
                titleStyle.setAlignment(HorizontalAlignment.LEFT);
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleStyle.setFont(titleFont); 
                Cell cell = titleRow.createCell(currentColumn); 
                cell.setCellStyle(titleStyle);
                cell.setCellValue(text);     
                
            } else {
                CellStyle titleStyle = workbook.createCellStyle();
                titleStyle.setAlignment(HorizontalAlignment.RIGHT);
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleStyle.setFont(titleFont); 

                Cell cell = titleRow.createCell(currentColumn); 
                cell.setCellStyle(titleStyle);
                cell.setCellValue(text);   

            }
            
            if(i == 0) {
                currentRow++;
            } else {
                currentColumn += 2;
            }
        }
        
        currentRow ++;
        currentColumn = 0;
        
        // 處理 Table Header
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont); 
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        
        Row headerRow = sheet.createRow(currentRow);
        for(String header:headers){
            HSSFRichTextString text = new HSSFRichTextString(header);
            Cell cell = headerRow.createCell(currentColumn); 
            cell.setCellStyle(headerStyle);
            cell.setCellValue(text);      
            currentColumn++;
            
        }

        currentRow++;
        
        // 處理 Table 欄位內容
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        
        if(!CollectionUtils.isEmpty(contents)) {
            for(List<String> result: contents){
                currentColumn = 0;
                Row row = sheet.createRow(currentRow);
                
                for(String value : result) { 
                    Cell cell = row.createCell(currentColumn);
                    HSSFRichTextString text = new HSSFRichTextString(value);  
                    cell.setCellStyle(cellStyle);  
                    cell.setCellValue(text);    
                        
                    currentColumn++;
                }
                currentRow++;
            }
        }
        
        // 調整欄位寬度
//        int totalRow = sheet.getLastRowNum() + 1;
        int totalColumn = sheet.getRow(2).getPhysicalNumberOfCells();

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, totalColumn-1));     
        if("日常火源".equals(sheetName)) {
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4)); 
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, totalColumn-1));
            
        } else {
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1)); 
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, totalColumn-1));
        }

        for(int i=0; i<totalColumn; i++) {  
            //sheet.autoSizeColumn(i);
        	sheet.setColumnWidth(i, (short)256*25);
        }
        
        logger.debug("### createExcelContent End.....");

        return workbook;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#getExcelData1(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, long, java.lang.String)
     */
    @Override
    public Map<String, Object> getExcelData1(String checkYear, String checkMonth, String placeId, String place,
            String checkType, long checkTimesMark, String managerSign) {
        
        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        String checkDate1 = "";
        String checkDate2 = "";
        String manager = "";
        
        dataMap.put("sheetName", checkType);
        
        // 取得欲匯出的資料
        List<FireCheckRecordDto> recordList = getFirePreventionFacilities(placeId, checkType, checkYear, checkMonth);  
        if(!CollectionUtils.isEmpty(recordList)) {
            FireCheckRecordDto record = recordList.get(0);
            manager = StringUtils.isEmpty(record.getManager()) ? "" : record.getManager();
            checkDate1 = StringUtils.isEmpty(record.getCheckDate1Str()) ? "" : record.getCheckDate1Str();
            checkDate2 = StringUtils.isEmpty(record.getCheckDate2Str()) ? "" : record.getCheckDate2Str();
            managerSign = StringUtils.isEmpty(record.getManagerSign()) ? "" : record.getManagerSign();
            logger.debug("*** record ***=" + record.toString());
        }
                
        // 設定 Title
        List<String> titles = new ArrayList<String>();
        String mainTitle = place.concat(checkYear).concat("年").concat(checkMonth).concat("月").concat(checkType).concat("自行檢查記錄表");
        titles.add(mainTitle);
        titles.add("單位主管簽章：" + managerSign);
        titles.add("負責人：".concat(manager).concat(" 檢查日期：").concat(checkDate1).concat("/").concat(checkDate2));
        dataMap.put("titles", titles);

        // 設定 Table Header
        List<String> headers = new ArrayList<String>();
        headers.add("No.");
        headers.add("檢查重點實施內容");
        headers.add("符號");
        headers.add("檢查狀況處置情形");
        headers.add("符號");
        headers.add("檢查狀況處置情形");
        headers.add("更新人員");
        dataMap.put("headers", headers);

        // 設定 Table 內容
        List<List<String>> contents = new ArrayList<List<String>>();
        List<String> contentList = null;
        for(int i=0; i<recordList.size(); i++) {
            contentList = new ArrayList<String>();
            FireCheckRecordDto record = recordList.get(i);
            contentList.add(String.valueOf(i+1));
            contentList.add(record.getCheckContent());
            contentList.add(record.getCheckSymbol1());
            contentList.add(record.getCheckResult1());
            contentList.add(record.getCheckSymbol2());
            contentList.add(record.getCheckResult2());
            contentList.add(record.getUpdatorName());
            contents.add(contentList);
        }        
        dataMap.put("contents", contents);
        
        // 設定 Table 內容水平對齊方式
//        HorizontalAlignment[] contentAligns = new HorizontalAlignment[]{HorizontalAlignment.CENTER, HorizontalAlignment.LEFT, HorizontalAlignment.CENTER, HorizontalAlignment.LEFT, HorizontalAlignment.CENTER, HorizontalAlignment.LEFT, HorizontalAlignment.CENTER};
//        dataMap.put("contentAligns", contentAligns);
        
        return dataMap;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#getExcelData2(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, long, java.lang.String)
     */
    @Override
    public Map<String, Object> getExcelData2(String checkYear, String checkMonth, String placeId, String place,
            String checkType, long checkTimesMark, String managerSign) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        String checkDateStr = "";
        String manager = "";
        
        dataMap.put("sheetName", checkType);
        
        // 取得欲匯出的資料
        List<FireCheckRecordDto> recordList = getFireSafetyEquipment(placeId, checkType, checkYear, checkMonth);  
        if(!CollectionUtils.isEmpty(recordList)) {
            FireCheckRecordDto record = recordList.get(0);
            manager = StringUtils.isEmpty(record.getManager()) ? "" : record.getManager();
            checkDateStr = StringUtils.isEmpty(record.getCheckDateYYYYMMDD()) ? "" : record.getCheckDateYYYYMMDD();
            managerSign = StringUtils.isEmpty(record.getManagerSign()) ? "" : record.getManagerSign();
//            logger.debug("*** record ***=" + record.toString());
        }
                
        // 設定 Title
        List<String> titles = new ArrayList<String>();
        String mainTitle = place.concat(checkYear).concat("年").concat(checkMonth).concat("月").concat(checkType).concat("自行檢查記錄表");
        titles.add(mainTitle);
        titles.add("單位主管簽章：" + managerSign);
        titles.add("負責人：".concat(manager).concat(" 檢查日期：").concat(checkDateStr));
        dataMap.put("titles", titles);

        // 設定 Table Header
        List<String> headers = new ArrayList<String>();
        headers.add("No.");
        headers.add("類別設備內容");
        headers.add("檢查重點實施內容");
        headers.add("符號");
        headers.add("檢查狀況處置情形");
        headers.add("更新人員");
        dataMap.put("headers", headers);

        // 設定 Table 內容
        List<List<String>> contents = new ArrayList<List<String>>();
        List<String> contentList = null;
        for(int i=0; i<recordList.size(); i++) {
            contentList = new ArrayList<String>();
            FireCheckRecordDto record = recordList.get(i);
            contentList.add(String.valueOf(i+1));
            contentList.add(record.getDeviceType());
            contentList.add(record.getCheckContent());
            contentList.add(record.getCheckSymbol());
            contentList.add(record.getCheckResult());
            contentList.add(record.getUpdatorName());
            contents.add(contentList);
        }        
        dataMap.put("contents", contents);
        
        // 設定 Table 內容水平對齊方式
//        HorizontalAlignment[] contentAligns = new HorizontalAlignment[]{HorizontalAlignment.CENTER, HorizontalAlignment.LEFT, HorizontalAlignment.LEFT, HorizontalAlignment.CENTER, HorizontalAlignment.LEFT, HorizontalAlignment.CENTER};
//        dataMap.put("contentAligns", contentAligns);
        
        return dataMap;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#getExcelData4(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> getExcelData4(String checkYear, String checkMonth, String placeId, String place,
            String checkType, String managerSign) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        String manager = "";
        
        dataMap.put("sheetName", checkType);
        
        // 取得欲匯出的資料
        List<FireCheckRecordDto> recordList = getDailyFireSource(placeId, checkType, checkYear, checkMonth);  
        if(!CollectionUtils.isEmpty(recordList)) {
            FireCheckRecordDto record = recordList.get(0);
            manager = StringUtils.isEmpty(record.getManager()) ? "" : record.getManager();
            managerSign = StringUtils.isEmpty(record.getManagerSign()) ? "" : record.getManagerSign();
//            logger.debug("*** record ***=" + record.toString());
        }
                
        // 設定 Title
        List<String> titles = new ArrayList<String>();
        String mainTitle = place.concat(checkYear).concat("年").concat(checkMonth).concat("月").concat(checkType).concat("自行檢查記錄表");
        titles.add(mainTitle);
        titles.add("單位主管簽章：" + managerSign);
        titles.add("負責人：" + manager);
        dataMap.put("titles", titles);

        // 設定 Table Header
        List<String> headers = new ArrayList<String>();
        headers.add("日期");
        headers.add("週");
        headers.add("用火設備使用情形");
        headers.add("電器設備配線");
        headers.add("煙蒂處理");
        headers.add("下班時火源管理");
        headers.add("其它(可燃物管理等)");
        headers.add("附記");
        headers.add("更新人員");
        dataMap.put("headers", headers);

        // 設定 Table 內容
        List<List<String>> contents = new ArrayList<List<String>>();
        List<String> contentList = null;
        for(int i=0; i<recordList.size(); i++) {
            contentList = new ArrayList<String>();
            FireCheckRecordDto record = recordList.get(i);
            contentList.add(record.getDay());
            contentList.add(record.getDayOfWeek());
            contentList.add(record.getCheckSymbol1());
            contentList.add(record.getCheckSymbol2());
            contentList.add(record.getCheckSymbol3());
            contentList.add(record.getCheckSymbol4());
            contentList.add(record.getCheckSymbol5());
            contentList.add(record.getCheckSymbol6());
            contentList.add(record.getUpdatorName());
            contents.add(contentList);
        }        
        dataMap.put("contents", contents);
        
        // 設定 Table 內容水平對齊方式
//        HorizontalAlignment[] contentAligns = new HorizontalAlignment[]{HorizontalAlignment.CENTER, HorizontalAlignment.CENTER, HorizontalAlignment.CENTER, HorizontalAlignment.CENTER, HorizontalAlignment.CENTER, HorizontalAlignment.CENTER, HorizontalAlignment.CENTER, HorizontalAlignment.CENTER, HorizontalAlignment.CENTER};
//        dataMap.put("contentAligns", contentAligns);
        
        return dataMap;
    }

    /*
     * (non-Javadoc)
     * @see proj.ctworld.ctss.service.FireCheckService#getCheckTimesMarks(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<FireCheckRecord> getCheckTimesMarks(String placeId, String checkType, String checkYear,
            String checkMonth) {
        
        return fireCheckRecordRepository.queryCheckTimesMarkList(placeId, checkType, checkYear, checkMonth);
    }
    
}
