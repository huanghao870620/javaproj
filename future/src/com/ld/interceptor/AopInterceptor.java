package com.ld.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ld.page.SetPageParam;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author huang.hao
 *
 */
@Aspect
@Component
public class AopInterceptor {

	@Pointcut("execution(* com.ld.mapper.*.*ByPaging(..))")
	private void anyMethod(){}
	
	@Before("anyMethod() && args(name)")
	public void doAccessCheck(String name){
//		point.getArgs();
		System.out.println(1);
	}
	
	
	/**
	 * 设置分页参数
	 */
	@Before("anyMethod()")
	public void doBefore(){
		SetPageParam spp = new SetPageParam();
		spp.execute();
	}
	
	
	/**
	 * 分页查询后执行
	 * @param rvt
	 */
	@AfterReturning(returning="rvt",value="anyMethod()")
	public void doAfter(Object rvt){
		com.github.pagehelper.Page<?> resultPage = (com.github.pagehelper.Page<?>) rvt;
		ActionContext ctx = ActionContext.getContext();
		ctx.put("resultPage", resultPage);
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
