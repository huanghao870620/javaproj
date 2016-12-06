package com.xa.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 
 * @author burgess
 *
 */
@Aspect
@Component
public class AopInterceptor {

	@Pointcut("execution(* com.xa.entity.File.*(..))")
	private void anyMethod(){}
	
	@Before("anyMethod() && args(name)")
	public void doAccessCheck(String name){
		System.out.println(1);
	}
	
	
	/**
	 */
	@Before("anyMethod()")
	public void doBefore(){
		System.out.println("999999999999999");
	}
	
	
	/**
	 * @param rvt
	 */
	@AfterReturning(returning="rvt",value="anyMethod()")
	public void doAfter(Object rvt){
//		com.github.pagehelper.Page<?> resultPage = (com.github.pagehelper.Page<?>) rvt;
		System.out.println("222");
	}
	
	@After("anyMethod()")
	public void after(){
		System.out.println(11);
	}
	
	@AfterThrowing("anyMethod()")
	public void doAfterThrow(){
		
	}
	
	
//	@Around("anyMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
//		Object obj = pjp.proceed();
		return pjp;
	}
	
}
