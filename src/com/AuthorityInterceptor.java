package com;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
		    String URI = request.getRequestURI();
            if(URI.matches("/root/.*")) {
                if(user.getClass().toString().equals("class com.entity.Root")) return true;
                System.out.println(user.getClass().toString());
            }
            else if(URI.matches("/admin/.*")){
                if(user.getClass().toString().equals("class com.entity.Admin")) return true;
            }
            else if(URI.matches("/teacher/.*")){
                if(user.getClass().toString().equals("class com.entity.Teacher")) return true;
            }
		}
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path;
		response.sendRedirect(basePath + "/index");
		return false;
	}

}
