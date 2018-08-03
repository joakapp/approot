package com.ms.db.config;

import com.google.common.collect.Maps;
import com.ms.db.common.DataSourceKey;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jws.Oneway;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DBConfig {

    /**
     * 配置写数据源1
     * @return
     */
    @Bean("masterDs")
    public DataSource createMasterDs(){
        return new HikariDataSource(masterHikariConfig());
    }

    /**
     * 创建只写模式的数据源配置
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "hikari.master")
    public HikariConfig masterHikariConfig() {
        return new HikariConfig();
    }

    /**
     * 配置读数据源1
     * @return
     */
    @Bean("slaveDs")
    public DataSource createSlaveDs(){
        return new HikariDataSource(slaveHikariConfig());
    }

    /**
     * 创建只读模式的数据源配置
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "hikari.slave")
    public HikariConfig slaveHikariConfig() {
        return new HikariConfig();
    }

    /**
     * 获得所有的数据源映射表
     * @return
     */
    public Map<Object,Object> getAllDsMap(){
        Map<Object, Object> allDsMap = Maps.newHashMap();
        allDsMap.putAll(getMasterDsMap());
        allDsMap.putAll(getSlaveDsMap());
        return allDsMap;
    }

    /**
     * 设置写数据源映射关系表
     * @return
     */
    public Map<Object,Object> getMasterDsMap(){
        Map<Object, Object> masterDsMap = Maps.newHashMap();
        masterDsMap.put(DataSourceKey.masterDs.name(),createMasterDs());
        return masterDsMap;
    }

    /**
     * 设置读数据源映射关系表
     * @return
     */
    public Map<Object,Object> getSlaveDsMap(){
        Map<Object, Object> slaveDsMap = Maps.newHashMap();
        slaveDsMap.put(DataSourceKey.slaveDs.name(),createSlaveDs());
        return slaveDsMap;
    }

    /**
     * 获得系统设置的默认的数据源
     * @return
     */
    public Object getDefTargetDsKey(){
        return DataSourceKey.masterDs.name();
    }
}
