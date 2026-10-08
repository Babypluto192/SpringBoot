package kz.iitu.springlab.aspect;

import jakarta.servlet.http.HttpServletRequest;
import kz.iitu.springlab.audit.RequiresRole;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AccessControlAspect {


    @Before("@annotation(requiresRole)")
    public void checkAccess(JoinPoint joinPoint, RequiresRole requiresRole) {


        String expectedRole = requiresRole.value();


        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("HTTP запрос не найден");
        }
        HttpServletRequest request = attributes.getRequest();


        String actualRole = request.getHeader("X-User-Role");


        if (actualRole == null || !actualRole.equals(expectedRole)) {
            throw new SecurityException("Доступ запрещен. Ожидалась роль: " + expectedRole + ", но получена: " + actualRole);
        }
    }
}