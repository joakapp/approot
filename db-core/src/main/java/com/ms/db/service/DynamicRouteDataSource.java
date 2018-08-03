package com.ms.db.service;

import com.ms.db.common.DynamicDSContextHolder;
import com.ms.db.config.DBConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 创建创建动态数据源
 *
 * @Author lushaonan
 *
 */
@Slf4j
@Primary
@Configuration
public class DynamicRouteDataSource extends AbstractRoutingDataSource {

    @Autowired
    public DBConfig dbConfig;

    @PostConstruct
    public void init(){
        /**
         * 设置全体数据源映射关系表
         */
        Map<Object,Object> allDsMap = dbConfig.getAllDsMap();
        log.debug("全部数据源:{}",allDsMap);
        setTargetDataSources(allDsMap);
        /**
         * 设置默认使用数据源
         */
        Map<Object,Object> masterDsMap = dbConfig.getMasterDsMap();
        log.debug("写数据源:{}",masterDsMap);
        setDefaultTargetDataSource(masterDsMap.get(dbConfig.getDefTargetDsKey()));
        DynamicDSContextHolder.writeDsSet.addAll(masterDsMap.keySet());
        /**
         * 读使用数据源
         */
        Map<Object,Object> slaveDsMap = dbConfig.getSlaveDsMap();
        log.debug("读数据源:{}",slaveDsMap);
        DynamicDSContextHolder.readDsSet.addAll(slaveDsMap.keySet());
    }

    protected Object determineCurrentLookupKey() {
        log.debug("当前数据源为：",DynamicDSContextHolder.getDsKey());
        return DynamicDSContextHolder.getDsKey();
    }
}
