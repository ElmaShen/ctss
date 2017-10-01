package proj.ctworld.core.service;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

import proj.ctworld.core.facade.Code;
import proj.ctworld.core.orm.*;
//com.websystique.springmvc.dao.UserDao;
//import com.websystique.springmvc.model.User;
import proj.ctworld.core.repository.*;

@Service("SystemService")
@PropertySource("classpath:application.properties")
@Transactional(rollbackFor = Exception.class)
public class SystemServiceImpl implements SystemService{

	@Value("${proj.mode}")
	private String ssMode;
	
	private static Logger logger = Logger.getLogger(SystemServiceImpl.class);

	@Autowired
	private SystemMessageRepository smDAO;
	
	@Autowired
	private SystemSettingRepository ssDAO;

	public HashMap<String, Object> getMessage(Integer rtnCode) {
		return getMessage(rtnCode, null);
	}

	public HashMap<String, Object> getMessage(Integer rtnCode, String message) {
		logger.debug("rtnCode="+rtnCode);
		SystemMessage sm = smDAO.findBySmCode(rtnCode);

		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("rtnCode", rtnCode);
		ret.put("rtnMsg", message != null ? message
				: sm != null ? sm.getSmMessage() : String.format("系統錯誤，此錯誤(%d)並沒有定義在訊息表中，請聯絡系統人員。", rtnCode));

		return ret;
	}

	public ArrayList<Integer> findMissingMessage() {
		Field[] fields = Code.class.getDeclaredFields();
		ArrayList<Integer> missingCodes = new ArrayList<Integer>();
		for (Field field : fields) {
			try {
				Integer code = (Integer) field.get(null);
				if (getMessage(code) == null) {
					missingCodes.add(code);
					logger.debug("missing message of " + code);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (missingCodes.size() > 0)
			logger.error("some systemMessage are not declared, " + Arrays.toString(missingCodes.toArray()));
		return missingCodes;
	}

	@Override
	public String getSystemSetting(String ssCode) {
		logger.debug("ssMode="+ssMode);
		List<SystemSetting> list = ssDAO.getSystemSetting(ssCode, ssMode);
		logger.debug("list size="+list.size());
		return list.size() > 0 ?list.get(0).getSsValue(): null;
	}
}
