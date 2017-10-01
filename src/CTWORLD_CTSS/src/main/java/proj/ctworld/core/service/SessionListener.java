package proj.ctworld.core.service;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionListener implements HttpSessionListener {
 
    @Autowired
    private SessionRegistry sessionRegistry;
 
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    	String ipAddr = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
    	           .getRequest().getRemoteAddr();
    	se.getSession().setAttribute("ip", ipAddr);
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionRegistry.removeSession(se.getSession());
    }
}
