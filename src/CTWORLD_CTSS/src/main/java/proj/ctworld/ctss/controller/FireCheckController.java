package proj.ctworld.ctss.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
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
import proj.ctworld.ctss.service.FireCheckService;

/**
 * 【程式名稱】: FireCheckController <br/>
 * 【功能名稱】: 安檢登錄與調閱 Controller <br/>
 * 【功能說明】: For 安檢登錄/調閱 使用<br/>
 * 【建立日期】: 2017/07/24 <br/>
 * 【異動紀錄】: <br/>
 * <br/>
 *
 * @author JoyceLai <br/>
 * 
 */
@Controller
public class FireCheckController {
    
    private static Logger logger = Logger.getLogger(FireCheckController.class);
    
    @Autowired
    private AdminAuthService authService;

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private FireCheckService fireCheckService;
    
    /**
     * checkRecord 說明: 安檢登錄/調閱.<br/>
     * <br/>   
     * @param locale
     * @param model
     * @return String
     * @throws Exception 
     * @author JoyceLai 2017/07/24
     */
    @RequestMapping(value = "/admin/fireCheck", method = RequestMethod.GET)
    @AdminAudit
    public String fireCheck(Locale locale, Model model, @RequestParam(value="userPlaceId", required=false) String userPlaceId) throws Exception {
        logger.debug("### FireCheckController.FireCheck Start...");
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
        
//        logger.debug("upmis.size="+upmis.size());
        
//        for (UserPlaceMappingInfo userPlaceMappingInfo : upmis) {
//            logger.debug("upmi.placeId="+userPlaceMappingInfo.getPlaceId() );
//        }
        
        model.addAttribute("upmis",upmis);
        model.addAttribute("upId", StringUtils.isEmpty(userPlaceId) ? upmis.get(0) : userPlaceId);
        
        logger.debug("### FireCheckController.FireCheck End...");
        
        return "admin/fireCheck";
    }

    /**
     * exportExcel1 說明: 匯出Excel(防火避難設施).<br/>
     * <br/>   
     * @param session
     * @param response
     * @param checkYear
     * @param checkMonth
     * @param placeId
     * @param place
     * @param checkType
     * @param checkTimesMark
     * @param managerSign void
     * @author JoyceLai 2017/08/05
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/admin/fireCheck/exportExcel", method = RequestMethod.GET)
    @AdminAudit
    public void exportExcel(HttpServletResponse response, @RequestParam(value="checkYear") String checkYear, @RequestParam(value="checkMonth") String checkMonth, @RequestParam(value="placeId") String placeId, @RequestParam(value="place") String place, @RequestParam(value="checkType") String checkType, @RequestParam(value="checkTimesMark") long checkTimesMark, @RequestParam(value="managerSign") String managerSign) {
        logger.debug("### FireCheckController.exportExcel Start...");

        Map<String, Object> dataMap = new HashMap<String, Object>();
        if("防火避難設施".equals(checkType)) {
            dataMap = fireCheckService.getExcelData1(checkYear, checkMonth, placeId, place, checkType, checkTimesMark, managerSign);
        } else if("日常火源".equals(checkType)) {
            dataMap = fireCheckService.getExcelData4(checkYear, checkMonth, placeId, place, checkType, managerSign);
        } else {
            dataMap = fireCheckService.getExcelData2(checkYear, checkMonth, placeId, place, checkType, checkTimesMark, managerSign);
        }

        // 產生 Excel檔案(xls) 並下載
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   
        try {    
            // 建立 Excel 內容資料
            HSSFWorkbook workbook = fireCheckService.createExcelContent(dataMap);
            
            String mainTitle = ((List<String>)dataMap.get("titles")).get(0);
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
        logger.debug("### FireCheckController.exportExcel End...");
    }
    
}
