package sg.nus.iss.shoppingCart.interceptor;

import java.io.IOException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class RedirectIfLoggedOutInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		// change:
		// 1.Can not change to boolean directly. need a if to check
		Object sessionAttr = request.getSession().getAttribute("isLoggedIn");
		boolean isLoggedIn = sessionAttr !=null && (Boolean) sessionAttr;
		if (isLoggedIn == false) {
			response.sendRedirect("/login");
			return false;
		} else if (isLoggedIn == false) {
			
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
	
	}
	
}
