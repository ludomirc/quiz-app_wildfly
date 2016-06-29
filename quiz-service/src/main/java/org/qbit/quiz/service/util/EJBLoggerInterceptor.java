package org.qbit.quiz.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class EJBLoggerInterceptor {

	Logger logger = LogManager.getLogger(EJBLoggerInterceptor.class);

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {

		logger.debug(ctx.getTarget().getClass().getName() +"."+ ctx.getMethod().getName() + "()- begin");
		Object result = ctx.proceed();
		logger.debug(ctx.getTarget().getClass().getName() +"."+ ctx.getMethod().getName() + "()- end");

		return result;
	}
}