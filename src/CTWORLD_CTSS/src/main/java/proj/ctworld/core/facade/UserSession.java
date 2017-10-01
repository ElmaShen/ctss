package proj.ctworld.core.facade;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class UserSession {

	private static Logger logger = Logger.getLogger(UserSession.class);
	private Long userId;
	protected DateTime loginTime;
	protected String loginIp;
	
	protected DateTime accessTime;
	
	public UserSession(Long userId, DateTime loginTime, String loginIp) {
		this.setUserId(userId);
		this.loginTime = loginTime;
		this.loginIp = loginIp;
	}
	
	public void updateContext(DateTime accessTime)
	{
		this.accessTime = accessTime;
	}
	
	public int isAvailable(DateTime accessTime)
	{
		return Code.SUCCESS;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
