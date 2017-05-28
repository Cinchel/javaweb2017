package com;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(MyException.class)
	public String getMyException(
			HttpServletRequest request, 
			RedirectAttributes redirectAttributes, 
			MyException e) {
		redirectAttributes.addFlashAttribute("exception", e.getMessage());
		return "redirect:" + request.getHeader("referer");
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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	
	public void get(){
		System.out.println("aa");
	} 

}
