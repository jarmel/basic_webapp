package com.jarmel.basic.webapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@Order(5)
public class BasicWebAppLoginMonitorAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());

	// create pointcut for getter methods
	@Pointcut("execution(* com.jarmel.basic.webapp..*.get*(..))")
	public void getter() {
	}

	// create pointcut for setter methods
	@Pointcut("execution(* com.jarmel.basic.webapp..*.set*(..))")
	public void setter() {
	}

	// setup pointcut declarations
	@Pointcut("execution(* com.jarmel.basic.webapp.controller..*.*(..))")
	private void forControllerPackage() {
	}


	// do the same for service and dao
	@Pointcut("execution(* com.jarmel.basic.webapp.service..*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* com.jarmel.basic.webapp.dao..*.*(..))")
	private void forDaoPackage() {
	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {
	}

	// add @Before advice
	// create pointcut: include package ... exclude getter/setter
	@Pointcut("forAppFlow() && !(getter() || setter())")
	public void forPackagesNoGetterSetter() {
	}

	// create pointcut for logging Login Activity to the database analytics tables
	@Pointcut("execution(* com.jarmel.basic.webapp.config.CustomAuthenticationSuccessHandler.onAuthenticationSuccess(..))")
	private void forLoginSuccessDatabaseLogging() {
	}

	@Pointcut("forLoginSuccessDatabaseLogging() && !(getter() || setter())")
	public void forLoginSuccessDatabaseLoggingNoGetterSetter() {
	}

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
			pointcut = "forPackagesNoGetterSetter()",
			returning = "theResult"
	)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

		// display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>> in @AfterReturning: from method: " + theMethod);

		myLogger.info("=====>> in @AfterReturning: JoinPoint: " + theJoinPoint);

		// display data returned
		myLogger.info("=====>> result: " + theResult);
	}


	@Around("execution(* forLoginSuccessDatabaseLoggingNoGetterSetter())")
	public Object aroundGetFortune(
			ProceedingJoinPoint theProceedingJoinPoint) throws Throwable {

		// print out method we are advising on
		String method = theProceedingJoinPoint.getSignature().toShortString();
		System.out.println("\n=====>>> Executing @Around on method: " + method);

		// get begin timestamp
		long begin = System.currentTimeMillis();

		// now, let's execute the method
		Object result = theProceedingJoinPoint.proceed();

		// get end timestamp
		long end = System.currentTimeMillis();

		// compute duration and display it
		long duration = end - begin;
		System.out.println("\n=====> Duration: " + duration / 1000.0 + " seconds");

		return result;
	}
}
