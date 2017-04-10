package cn.catecat.user.userFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cn.catecat.user.bean.User;	
import cn.catecat.user.service.dao.UserService;
	
public class UserFilter implements Filter {
	@Autowired
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	protected FilterConfig config;

    public UserFilter() {
    	
    }
    public void init(FilterConfig config) throws ServletException {
    	this.config = config;
	}
	

	/**
	 * 重写doFilter方法，过滤所有请求和回复，加入或取出Cookie
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Cookie[] cookies = req.getCookies();
        String[] info = null;
        if(req.getRequestURI().endsWith("login.action") || req.getRequestURI().endsWith("regist.action")){
        	chain.doFilter(req, resp);return;
        }
        if (cookies != null) {
            for (Cookie c : cookies) {
            	if("autologin".equals(c.getName())){
            		info = c.getValue().split("&");
            		if (info.length >= 2) {
                        String username = info[0];
                        String password = info[1];
                        User CookieUser = userService.checkUser(username,password);
                        req.getSession().setAttribute("user", CookieUser);
                    }
            	}
            }
        }
        chain.doFilter(req, resp);
	}

	/**
	 * 销毁过滤器
	 */
	public void destroy() {
		this.config = null;
	}

}
