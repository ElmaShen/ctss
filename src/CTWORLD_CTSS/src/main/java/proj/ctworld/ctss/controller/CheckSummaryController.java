package proj.ctworld.ctss.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import proj.ctworld.ctss.interceptor.AdminAudit;
import proj.ctworld.ctss.orm.AdminEmpSession;
import proj.ctworld.ctss.orm.UserPlaceMappingInfo;
import proj.ctworld.ctss.service.AdminAuthService;
import proj.ctworld.ctss.service.CheckSummaryService;

/**
 * 
 * 【程式名稱】: CheckSummaryController <br/>
 * 【功能名稱】: 檢查總表<br/>
 * 【功能說明】: <br/>
 * 【建立日期】: 2017/08/04 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author Tom Lai <br/>
 *
 */
@Controller
public class CheckSummaryController {
    
    private static Logger logger = Logger.getLogger(CheckSummaryController.class);
    
    @Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private CheckSummaryService checkSummaryService;
    
    /**
     * 
     * checkSummary 說明: 取得檢查總表初始化資料並跳轉頁面.<br/>
     * <br/>   
     * @param locale
     * @param model
     * @return
     * @throws Exception 
     * @return String
     * @author Tom Lai 2017/08/04
     */
    @RequestMapping(value = "/admin/checkSummary", method = RequestMethod.GET)
    @AdminAudit
    public String checkSummary(Locale locale, Model model, @RequestParam(value="userPlaceId", required=false) String userPlaceId) throws Exception {
        Long token = authService.getToken(request);
        AdminEmpSession aes = authService.getAdminEmpSession(token);
        
        logger.debug("UserId:"+aes.getAesUserId());
        logger.debug("UserName:"+aes.getAesUserName());
        logger.debug("UserType:"+aes.getAesUserType());
        logger.debug("UserIp:"+request.getRemoteAddr());
        
        model.addAttribute("UserId",aes.getAesUserId());
        model.addAttribute("UserName",aes.getAesUserName());
        model.addAttribute("UserType",aes.getAesUserType());
        model.addAttribute("UserIp", request.getRemoteAddr());
        
        List<UserPlaceMappingInfo> upmis;
        if("admin".equals(aes.getAesUserType())) {
            upmis = authService.getUserPlaceMappingInfo();
        } else {
            upmis = authService.getUserPlaceMappingInfo(aes.getAesUserId());
        }
        
        logger.debug("upmis.size="+upmis.size());
        
//        for (UserPlaceMappingInfo userPlaceMappingInfo : upmis) {
//            logger.debug("upmi.placeId="+userPlaceMappingInfo.getPlaceId() );
//        }
        
        model.addAttribute("upmis",upmis);
        model.addAttribute("upId", StringUtils.isEmpty(userPlaceId) ? upmis.get(0) : userPlaceId);
        
        return "admin/checkSummary";
    }

    /**
     * 
     * exportExcel 說明: 進行總表匯出.<br/>
     * <br/>   
     * @param response
     * @param placeType
     * @param year
     * @param month 
     * @return void
     * @author Tom Lai 2017/08/09
     */
    @RequestMapping(value = "/admin/checkSummary/exportExcel", method = RequestMethod.GET)
    @AdminAudit
    public void exportExcel(HttpServletResponse response, @RequestParam(value="placeType") String placeType, @RequestParam(value="year") String year, 
    		@RequestParam(value="month") String month) {
    	logger.debug("### CheckSummary exportExcel Start...");
    	
    	Map<String, Object> dataMap = checkSummaryService.getExportData(placeType, year, month);
    	
    	// 產生 Excel檔案(xls) 並下載
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   
        try {    
            // 建立 Excel 內容資料
            HSSFWorkbook workbook = checkSummaryService.createExcel(dataMap);
            
            String mainTitle = (String) dataMap.get("title") ;
            String fileName = new String(mainTitle.getBytes(), "ISO8859-1");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xls\""); 
            
            workbook.write(baos);
            InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exportExcel Error!", e);
        }
    }
}
