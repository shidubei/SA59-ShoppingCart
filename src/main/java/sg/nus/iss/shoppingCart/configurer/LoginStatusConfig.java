package sg.nus.iss.shoppingCart.configurer;

import sg.nus.iss.shoppingCart.interceptor.RedirectIfLoggedInInterceptor;
import sg.nus.iss.shoppingCart.interceptor.RedirectIfLoggedOutInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class LoginStatusConfig implements WebMvcConfigurer {
	
	@Autowired
	RedirectIfLoggedInInterceptor redirectIfLoggedInInterceptor;
	
	@Autowired
	RedirectIfLoggedOutInterceptor redirectIfLoggedOutInterceptor;
	
	  @Override
	  public void addInterceptors(InterceptorRegistry registry) {
		  registry.addInterceptor(redirectIfLoggedInInterceptor).addPathPatterns("/login","/signup");
		  registry.addInterceptor(redirectIfLoggedOutInterceptor).addPathPatterns("/updatedetails");
	  }
}
