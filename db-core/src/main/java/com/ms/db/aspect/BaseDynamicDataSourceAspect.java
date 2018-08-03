package com.ms.db.aspect;

import com.ms.db.common.DynamicDSContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;

/**
 * 基础动态数据源拦截处理
 *
 * @Author lushaonan
 */
@Slf4j
public abstract class BaseDynamicDataSourceAspect {

    /**
     * 设置走只读数据源的方法
     */
    @Value("${dbServer.slaveMethods:find,select,query,get}")
    private String[] slaveMethods;

    @Pointcut("execution( * com.jweb.db.mapper.*.*(..))")
    public abstract void doCommonAspect();

    @Pointcut("execution(* com.baomidou.mybatisplus.core.mapper.*.*(..))")
    private void doTkAspect() {
    }

    @Pointcut("doCommonAspect() || doTkAspect()")
    private void doAspect() {
    }

    @Before("doAspect()")
    protected void useSlaveDataSource(JoinPoint point) {
        log.debug("处理方法:{}", point.getSignature());
        if (isSlaveMethod(point.getSignature().getName())) {
            DynamicDSContextHolder.useReadDataSource();
            log.debug("选择的数据源:{},处理方法:{}", DynamicDSContextHolder.getDsKey(), point.getSignature());
        }
    }

    @After("doAspect()")
    protected void restoreDataSource(JoinPoint point) {
        DynamicDSContextHolder.clearDsKey();
        log.debug("数据源恢复:{},处理方法:{}", DynamicDSContextHolder.getDsKey(), point.getSignature());
    }

    /**
     * 判断方法是不是使用从库数据源
     *
     * @param methodName 方法名
     * @return
     */
    protected Boolean isSlaveMethod(String methodName) {
        for (String mn : slaveMethods) {
            if (methodName.startsWith(mn)) return true;
        }
        return false;
    }

}
