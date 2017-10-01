package proj.ctworld.ctss.interceptor;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import proj.ctworld.core.facade.Code;
import proj.ctworld.ctss.facade.AdminFacade;
import proj.ctworld.ctss.service.AdminAuthService;

/**
 * 這是 AdminAudit Interceptor，與 AdminAuditAudit 互相配合，
 * 如果 AdminAudit 有加到 method 頭上，代表這個 method 需要 有 member login，
 * 即需要 token，則會驗證他的 token 是否有效。如果沒有效則回傳尚未登入不能使用。
 * @author matthewhui
 *
 */
public class AdminAuditInterceptor implements HandlerInterceptor  {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	ObjectMapper objectMapper;
	
	@Autowired
	AdminAuthService mmService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HandlerMethod hm ;
		try {
			hm = (HandlerMethod) handler;
		}
		catch (Exception ex) {
			return true;
		}
		
		Method method = hm.getMethod();
		
		if (method.getDeclaringClass().isAnnotationPresent(Controller.class) ||
				method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
//			logger.debug("Controller.class || RestController.class");
		}
		else {
			return true;
		}
		
		logger.debug("preHandle");
		if (!method.isAnnotationPresent(AdminAudit.class) && !method.isAnnotationPresent(AdminApiAudit.class)) {
			return true;
		}
		else if (method.isAnnotationPresent(AdminAudit.class)) {
			logger.info("do LoginAudit");
			
			logger.debug("request="+request +" mmService="+mmService);
			//從 request 中取出Token
			Long token = mmService.getToken(request);
			
			//利用 facade 中的 tokenIsValid function
			boolean alreadyLoggedIn = mmService.isLoginAndUpdateSession(token);
			
			logger.debug("alreadyLoggedIn="+alreadyLoggedIn+" token="+token);
			if (!alreadyLoggedIn) {
				
				mmService.removeToken(request);
				
				logger.debug("redirect to login page");
				
				request.getSession().setAttribute(AdminFacade.MEMBER_LAST_PAGE, request.getRequestURI());
				
				response.sendRedirect(request.getContextPath() + "/admin/login");
				return false;
			}
		}
		else if (method.isAnnotationPresent(AdminApiAudit.class)) {
			logger.info("do LoginAudit");
			
			logger.debug("request="+request +" mmService="+mmService);
			//從 request 中取出Token
			Long token = mmService.getToken(request);
			
			//利用 facade 中的 tokenIsValid function
			boolean alreadyLoggedIn = mmService.isLoginAndUpdateSession(token);
			
			logger.debug("alreadyLoggedIn="+alreadyLoggedIn+" token="+token);
			if (!alreadyLoggedIn) {
				
				mmService.removeToken(request);
				
				logger.debug("return no login message");
				
				HashMap<String,Object> ret = mmService.getMessage(Code.NON_LOGIN);
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				response.getWriter().write(objectMapper.writeValueAsString(ret));
				return false;
			}
		}
		
		logger.debug("return true");
		return true;
	}

	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		
		// we have nothing to handle
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
} 
