package com.exception;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.util.JsonUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandler_ {
	/*@ExceptionHandler(RuntimeException.class)
	public String getMyException(
			HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			RuntimeException e) {
		redirectAttributes.addFlashAttribute("exception", e.getMessage());
		return "redirect:" + request.getHeader("referer");
	}*/

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public String HandleException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) {
		if(request.getMethod().equals("POST")) {
            if(null != request.getParameter("pk")) { //判断请求是否存在pk参数，如果存在，则说明是table修改
                response.setStatus(500);
                return e.getMessage();
            }
			else
			    return JsonUtils.writeStatus(0,"服务器出现异常："+e.getMessage());
		}
		else { //get请求出现异常
            response.setStatus(500);
			return "服务器出现异常："+e.getMessage();
		}
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public String getConstraintViolationException(
			ConstraintViolationException e, 
			HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		 Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	      StringBuilder strBuilder = new StringBuilder();
	      for (ConstraintViolation<?> violation : violations ) {
	    	  strBuilder.append(violation.getMessage() + "; ");
	      }
	      redirectAttributes.addFlashAttribute("exception", strBuilder.toString());
	      return "redirect:" + request.getHeader("referer");
	}

	@ResponseBody
	@RequestMapping(value="/usersListPost",produces = "application/json; charset=utf-8")
	@ExceptionHandler(PostException.class)
	public String HandlePostException(HttpServletRequest request,HttpServletResponse response,PostException e) {
	    if(null != request.getParameter("pk")) { //判断请求是否存在pk参数，如果存在，则说明是table修改
            response.setStatus(500);
            return e.getMessage();
        }
		else return JsonUtils.writeStatus(0,e.getMessage());
	}

}
