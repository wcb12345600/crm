package com.shsxt.crm.aop;

import com.shsxt.crm.annotation.RequestPermission;
import com.shsxt.crm.utils.AssertUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {

    @Autowired
    private HttpSession session;

    @Pointcut("@annotation(com.shsxt.crm.annotation.RequestPermission)")
    public void cut(){}

    @Around("cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        RequestPermission annotation = method.getAnnotation(RequestPermission.class);
        String aclValue = annotation.aclValue();

        List<String> permissions = (List<String>) session.getAttribute("permissions");
        AssertUtil.isTrue(CollectionUtils.isEmpty(permissions) || !permissions.contains(aclValue),"没有权限");

        result = pjp.proceed();

        return result;
    }
}
