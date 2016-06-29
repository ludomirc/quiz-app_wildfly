package org.qbit.quiz.web.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Log @Interceptor
public class LogInterceptor implements Serializable {

    Logger logger = LogManager.getLogger(LogInterceptor.class);

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {

        logger.debug(ctx.getTarget().getClass().getSimpleName() +"."+ ctx.getMethod().getName() + "()- begin");
        Object result = ctx.proceed();
        logger.debug(ctx.getTarget().getClass().getSimpleName() +"."+ ctx.getMethod().getName() + "()- end");

        return result;
    }
}