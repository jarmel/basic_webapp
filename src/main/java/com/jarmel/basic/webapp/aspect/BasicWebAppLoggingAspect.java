package com.jarmel.basic.webapp.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class BasicWebAppLoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declarations
	@Pointcut("execution(* com.jarmel.basic.webapp.controller..*.*(..))")
	private void forControllerPackage() {}
	
	// do the same for service and dao
	@Pointcut("execution(* com.jarmel.basic.webapp.service..*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.jarmel.basic.webapp.dao..*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}

	// create pointcut for getter methods
	@Pointcut("execution(* com.jarmel.basic.webapp..*.get*(..))")
	public void getter() {}

	// create pointcut for setter methods
	@Pointcut("execution(* com.jarmel.basic.webapp..*.set*(..))")
	public void setter() {}
	// add @Before advice
	// create pointcut: include package ... exclude getter/setter
	@Pointcut("forAppFlow() && !(getter() || setter())")
	public void forPackagesNoGetterSetter() {}

	@Before("forPackagesNoGetterSetter()")
	public void before(JoinPoint theJoinPoint) {
		
		// display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>> in @Before: calling method: " + theMethod);
		
		// display the arguments to the method
		
		// get the arguments
		Object[] args = theJoinPoint.getArgs();
		
		// loop thru and display args
		for (Object tempArg : args) {
			myLogger.info("=====>> argument: " + tempArg);
		}
	}

	// add @AfterReturning advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
	
		// display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>> in @AfterReturning: from method: " + theMethod);

		myLogger.info("=====>> in @AfterReturning: JoinPoint: " + theJoinPoint);

		// display data returned
		myLogger.info("=====>> result: " + theResult);
	}
}
