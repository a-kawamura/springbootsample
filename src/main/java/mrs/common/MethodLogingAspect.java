package mrs.common;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLogingAspect {

	private final Logger log = LoggerFactory
			.getLogger(MethodLogingAspect.class);

	@Around("execution(* mrs..*Controller.*(..)) || execution(* mrs..*Service.*(..)) || execution(* mrs..*Repository.*(..)) || execution(* mrs..*Dao.*(..))")
	public Object log(ProceedingJoinPoint jp) throws Throwable {
		log.info("[START]" + jp.getSignature() + "-"
				+ Arrays.asList(jp.getArgs()));
		Object result = jp.proceed();
		log.info("[END]" + jp.getSignature() + "-" + result);
		return result;

	}

}